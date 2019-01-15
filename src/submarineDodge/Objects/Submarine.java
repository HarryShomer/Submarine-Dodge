package submarineDodge.Objects;

/**
 * Submarine type of picture object - this is the player
 */
public class Submarine extends PicObject
{
	public Submarine(int maxX, int maxY) {
		super(maxX, maxY);
		setintial_xC();
		setintial_yC();
		loadSubImage();
	}
	
	/**
	 * Call the superclass method to load out picture
	 */
	private void loadSubImage() {
        super.loadImage("images/submarine.png");
    }
	
	@Override 
	public void setintial_xC() {
		this.xC = maxX/3;
    }
    
	@Override
    public void setintial_yC() {
    	this.yC = maxY/2;
    }
    
    /**
     * Adjust the yC based on speed. Make sure don't exceed upper/lower bounds
     */
	@Override
	public void move() {
		if(yC + speed >= 0 & yC + speed <= (maxY-img.getHeight())){
			yC += speed;
		}
    }
	
	/**
	 * Edit change in yC to up 2
	 */
	public void moveUp() {
		speed = -2;
	}
	
	/**
	 * Edit change in yC to down 2
	 */
	public void moveDown() {
		speed = 2;
	}
	
	/**
	 * Edit change in yC to 0
	 */
	public void stopMoving() {
		speed = 0;
	}


}
