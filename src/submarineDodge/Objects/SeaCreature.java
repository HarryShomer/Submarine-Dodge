package submarineDodge.Objects;

import java.util.Random;

/**
 * SeaCreature object type. Three types: Whale, shark, octopus.
 */
public class SeaCreature extends PicObject
{
	public SeaCreature(int maxX, int maxY) {
		super(maxX, maxY);
		loadSeaImage();
		setintial_xC();
		setintial_yC();
		speed = -2;  // 2 to the left
	}
	
	/**
	 * Call the superclass method to load out picture
	 * We randomly choose from three images
	 * 1 = Whale, 2 = Shark, 3 = Octopus
	 */
	private void loadSeaImage() {
		Random rand = new Random();
		int randNum = rand.nextInt(3) + 1;
		
		if(randNum == 1)
			super.loadImage("images/whale.png");
		if(randNum == 2)
			super.loadImage("images/shark.png");
		if(randNum == 3)
			super.loadImage("images/octopus.png");
    }
	
	@Override 
	public void setintial_xC() {
		this.xC = this.maxX;
    }
    
	@Override
    public void setintial_yC() {
    	this.yC = randomInt(this.maxY);
    }
	
	/**
	 * Move to the left
	 */
	@Override
	public void move() {
		xC += speed;
    }
	
	/**
	 * Get a random number between 0 and max
	 * @param max Max possible value
	 * @return random #
	 */
	public int randomInt(int max) {
		// This ensures the whole creature will be on the screen
		int lowerBound = img.getHeight();
		int upperBound = max - img.getHeight();
		
		Random rand = new Random();
		return rand.nextInt(upperBound) + lowerBound;
	}
	
}
