package submarineDodge.Objects;


/**
 * Create the standard missile class
 */
public abstract class Missile extends PicObject {
	
	public Missile(int maxX, int maxY) {
		super(maxX, maxY);
		loadMissileImage();
		speed = 3;
	}
	
	public void loadMissileImage() {
		super.loadImage("images/missile.png");
	}
	
	@Override 
	public void move() {
		xC += speed;
	}
}

