package submarineDodge.Objects;

import java.util.Random;

/**
 * Enemy missile is a type of missile that starts at a random Y Coordinate
 */
public class EnemyMissile extends Missile
{
	public EnemyMissile(int maxX, int maxY) {
		super(maxX, maxY);
		super.setY(randomInt(maxY));  // Set the random height manually 
		setintial_xC();
		setintial_yC();
	}
	
	@Override 
	public void setintial_xC() {
		this.xC = 0;
    }
    
	@Override
    public void setintial_yC() {
    	this.yC = randomInt(this.maxY);
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
