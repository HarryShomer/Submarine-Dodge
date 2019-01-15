package submarineDodge;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * The Game class contains the panel with the game and starts it
 *
 */
public class Game extends JFrame {
	Ocean oceanPanel;
	    
    public Game() {
    	int width = 800;
    	int height = 600;
    	
    	// This contains the game logic
    	oceanPanel = new Ocean(height, width);
        add(oceanPanel, BorderLayout.CENTER);
        
        // Add the menu
        setJMenuBar(createMenuBar());
        
        setSize(height, width);
        setResizable(false);
        pack();

        setTitle("Submarine Chase");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFocusable(true);
        requestFocusInWindow();
    }   
    
    /**
     * Create the menu bar - includes the start button and the tool tip
     * @return menuBar - menu bar for the game
     */
    public JMenuBar createMenuBar() {
    	JMenuBar menuBar = new JMenuBar();  
    	
    	// Add the start button
        JButton startButton = new JButton("Start New Game");
        startButton.addActionListener(new StartButtonListener());
        menuBar.add(startButton);
        
        // Add the instructions
        JButton instructionButton = new JButton("Instructions");
        instructionButton.addActionListener(new InstructionButtonListener());
        menuBar.add(instructionButton);
        
        return menuBar;
    }
    
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Game ex = new Game();
                ex.setVisible(true);
            }
         });
    }
    
    
    /**
	 * Button Listener - When clicked we start a new game
	 */
	public class StartButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			oceanPanel.startGame();
		}
	}
	
	/**
	 * Button Listener - Display message with instructions
	 */
	public class InstructionButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String instructions = "The instructions are simple. "
								  + "Don't let any missiles or sea creatures touch the sub.\n"
								  + "To ensure so, use the up and down arrows to dodge them.\n\n"
								  + "See how long you can last!";
			JOptionPane.showMessageDialog(null, instructions, "Game Instructions", 
					                      JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
