package submarineDodge;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import submarineDodge.Objects.EnemyMissile;
import submarineDodge.Objects.SeaCreature;
import submarineDodge.Objects.Submarine;

/**
 * Contains the logic for the game and the animation
 *
 */
public class Ocean extends JPanel implements Runnable 
{	
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private final int HEIGHT, WIDTH;
	boolean gameOngoing;
	private Thread animator;
	private int LIVES = 5;
	private int TIME_ELAPSED = 0;
	private Color oceanColor = new Color(0, 119, 190);
	
	// Create the objects
	private ArrayList<EnemyMissile> enemyMissiles;
	private ArrayList<SeaCreature> creatures;
	private Submarine sub;
	
	// Control amount of lives
	private int lives;
	private JLabel livesLabel;
	
	// Control the time elapsed during the game
	// In milliseconds
	private int timeElapsed;
	private JLabel timeElapsedLabel;
	
	// Various delay triggers
	private int missileDelay = 0;        // Start at 0 so we create a missile from the start 
	private int borderDelay = 0;         // Flashed red for 100 milliseconds
	private int creatureDelay = 3000;    // Every 1.5 seconds - we wait 3 until starting
    private final int DELAY = 10;
    
    
    public Ocean(int height, int width) {
    	HEIGHT = height;
    	WIDTH = width;
    	
    	// Nothing happening yet
    	gameOngoing = false;
    	
    	// Create the standard objects
        enemyMissiles = new ArrayList<EnemyMissile>();
        creatures = new ArrayList<SeaCreature>();
        sub = new Submarine(WIDTH, HEIGHT);
    
    	setBackground(oceanColor);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));        
        setLayout(new GridLayout(25, 25));
        
        // Create the border
        // 0 thick and same color
        setBorder(BorderFactory.createLineBorder(oceanColor, 0));
        
        lives = LIVES;
        livesLabel = new JLabel("Lives: " + lives);
        
        timeElapsed = TIME_ELAPSED;
        timeElapsedLabel = new JLabel("Time: " + timeElapsed);

        setFocusable(true);
        requestFocusInWindow();
        specifyKeyBindings();
    }
    
    /**
     * Specify the key bindings for moving up and down
     */
    public void specifyKeyBindings() {
    	getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "upPressed");
        getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "upReleased");
        getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "downPressed");
        getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "downReleased");
        getActionMap().put("upPressed", new MoveUp());
        getActionMap().put("upReleased", new StopMoving());
        getActionMap().put("downPressed", new MoveDown());
        getActionMap().put("downReleased", new StopMoving());
    }
    
    /**
     * Cause the sub to move up when press the up key
     */
    public class MoveUp extends AbstractAction {
    	@Override
        public void actionPerformed(ActionEvent e) {
            sub.moveUp();
        }
    }
    
    /**
     * Cause the sub to move down when press the down key
     */
    public class MoveDown extends AbstractAction {
    	@Override
        public void actionPerformed(ActionEvent e) {
    		sub.moveDown();
        }
    }
    
    /**
     * Cause the sub to stop moving when release
     */
    public class StopMoving extends AbstractAction {
    	@Override
        public void actionPerformed(ActionEvent e) {
            sub.stopMoving();
        }
    }
    
    
    /**
     * Start the Game when the user says so
     */
    public void startGame() {    	
    	// Start it up and reset everything
    	gameOngoing = true;    
        enemyMissiles.clear();
        creatures.clear();
        lives = LIVES;
        timeElapsed = TIME_ELAPSED;
        missileDelay = 0;      
    	borderDelay = 0;      
    	creatureDelay = 3000;
        
        // Add label on top
        // Also set font size
        livesLabel.setFont(new Font(livesLabel.getName(), Font.BOLD, 16));
        livesLabel.setForeground(Color.GREEN);
        add(livesLabel);
        
        timeElapsedLabel.setFont(new Font(livesLabel.getName(), Font.BOLD, 16));
        timeElapsedLabel.setForeground(Color.GREEN);
        add(timeElapsedLabel);
        
        // Make sure it updates
        revalidate();
        repaint();
    }
    
    /**
     * Check if any collisions with missiles or creatures
     */
    public void ifCollisions() {
    	// Draw a box around the sub
    	// This is how we see if it intersects with anything
    	Rectangle subBounds =  new Rectangle(sub.getX(), sub.getY(), 
    										 sub.getWidth(), sub.getHeight());
    	
    	// Check if any missiles intersect with the sub 
    	for(int i=0; i < enemyMissiles.size(); i++) {
    		EnemyMissile missile = enemyMissiles.get(i);
    		
    		// Draw a box around this missile to represent it
    		Rectangle missileBounds = new Rectangle(missile.getX(), missile.getY(),
    												missile.getWidth(), missile.getHeight());
    		
    		// If intersect: You lose a life, destroy the missile and flash red
    		if(missileBounds.intersects(subBounds)) {
    			enemyMissiles.remove(i);
    			// Don't bother when the game is already over
    			if(lives > 0)
    				lives -= 1;
	    			// Start counting down for border
	    			borderDelay = 100;
	    	        setBorder(BorderFactory.createLineBorder(Color.RED, 4));
    		}
    	}
    	
    	// Check if any creatures intersect with the sub 
    	for(int i=0; i < creatures.size(); i++) {
    		SeaCreature creature = creatures.get(i);
    		
    		// Draw a box around the creature to represent it
    		Rectangle creatureBounds = new Rectangle(creature.getX(), creature.getY(),
    												 creature.getWidth(), creature.getHeight());
    		
    		// If intersect: You lose a life, destroy the creatures and flash red
    		if(creatureBounds.intersects(subBounds)) {
    			creatures.remove(i);
    			// Don't bother when the game is already over
    			if(lives > 0)
    				lives -= 1;
	    			// Start counting down for border
	    			borderDelay = 100;
	    	        setBorder(BorderFactory.createLineBorder(Color.RED, 4));
    		}
    	}
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //Draw objects
        drawSub(g);
        drawMissiles(g);
        drawCreatures(g);
        
        // Draw info in top right
        drawLives(g);
        drawTime(g);
        
        // Either draw player info or Game Over
        if(lives <= 0)
        	drawGameOver(g);
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    
    /**
     * Change the time for the current game
     * @param g Graphics object for the panel
     */
    public void drawTime(Graphics g) {
    	timeElapsedLabel.setText("Time: " + (int) timeElapsed/1000);
    }
    
    /**
     * Draw how many lives in top right corner
     * @param g Graphics object for the panel
     */
    private void drawLives(Graphics g) {
    	livesLabel.setText("Lives: " + lives);
    }
    
    /**
     * Note the game is over
     * @param g Graphics object for the panel
     */
    private void drawGameOver(Graphics g) {  
    	gameOngoing = false;
    	
    	// Print the Game Over message
    	g.setColor(Color.RED);        
        g.setFont(new Font("Helvetica", Font.BOLD, 36));
        g.drawString("GAME OVER", WIDTH / 2 - 100, HEIGHT / 2);
        
        // Empty all the missiles and creatures
        enemyMissiles.clear();
        creatures.clear();
    }

    /**
     * Draw the sub in its new spot
     * @param g Graphics object for the panel
     */
    private void drawSub(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(sub.getImage(), sub.getX(), sub.getY(), this);
    }
    
    /**
     * Draw the missiles currently on the board
     * @param g Graphics object for the panel
     */
    public void drawMissiles(Graphics g) {
    	for(EnemyMissile missile : enemyMissiles) {
    		g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
    	}
    }
    
    /**
     * Draw the creatures currently on the board
     * @param g Graphics object for the panel
     */
    public void drawCreatures(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
    	for(SeaCreature creature : creatures) {
    		g2d.drawImage(creature.getImage(), creature.getX(), creature.getY(), this);
    	}
    }
    
    /**
     * Logic for what happens every update
     */
    private void cycle() {
    	// Note the current time
    	timeElapsed += DELAY;
    	
    	// First convert the border back to empty if needed
    	if(borderDelay == 0)
    		setBorder(BorderFactory.createLineBorder(oceanColor, 0));
    	else
    		borderDelay -= DELAY;
    	
    	// First check if collided with anything
    	ifCollisions();
    	
    	// Adjust the sub Y coordinates
    	sub.move();
        
        // Create a new missile
        // Only if missile delay is 0 
        // We create 3.33 per second
        if( missileDelay == 0) {
        	enemyMissiles.add(new EnemyMissile(WIDTH, HEIGHT));
        	missileDelay = 300;
        }
        else
        	missileDelay -= DELAY;
        
        // Move Missiles
        // Get rid of missiles off panel to right
        for(int i=0; i < enemyMissiles.size(); i++) {
        	enemyMissiles.get(i).move();
        	if(enemyMissiles.get(i).getX() > WIDTH) 
        		enemyMissiles.remove(i);        			
        }  
        
        
        // Create a new creature if the delay is at 0
        // Create one every 2 seconds
        if(creatureDelay == 0) {
        	creatures.add(new SeaCreature(WIDTH, HEIGHT));
        	creatureDelay = 2000;
        }
        else 
        	creatureDelay -= DELAY;
        
        // Move creatures
        // Get rid of creatures off panel to left
        for(int i=0; i < creatures.size(); i++) {
        	creatures.get(i).move();
        	if(creatures.get(i).getX() < 0) 
        		creatures.remove(i);        			
        } 
           
    }

    
    @Override
    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        while (true) {
        	// Only run if the game is currently happening
        	if(gameOngoing) {
        		cycle();
        		repaint();
        	}

        	timeDiff = System.currentTimeMillis() - beforeTime;
        	sleep = DELAY - timeDiff;

        	if (sleep < 0) 
        		sleep = 2;
            
        	try {
        		Thread.sleep(sleep);
        	} catch (InterruptedException e) {
        		String msg = String.format("Thread interrupted: %s", e.getMessage());
        		JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
        	}

        	beforeTime = System.currentTimeMillis();
        }
    }
}


