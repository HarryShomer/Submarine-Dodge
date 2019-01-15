package submarineDodge.Objects;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Abstract class for any moving object that is a picture
 */
public abstract class PicObject 
{
	public BufferedImage img;
	public int xC, yC, speed, maxX, maxY;
	
	public PicObject(int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	/**
	 * Abstract method for setting the initial X Coordinate
	 */
	public abstract void setintial_xC();
	
	/**
	 * Abstract method for setting the initial Y Coordinate
	 */
    public abstract void setintial_yC();
    
    /**
     * Move the object in some way
     */
    public abstract void move();
	
	/**
	 * Return the image given the file name
	 * @param fileName - name of pic file
	 */
	public void loadImage(String fileName) {
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setX(int x) {
		xC = x;
	}
	
	public void setY(int y) {
		yC = y;
	}
	
	public int getX() {
		return xC;
	}
	
	public int getY() {
		return yC;
	}
	
	public int getHeight() {
		return img.getHeight();
	}
	
	public int getWidth() {
		return img.getWidth();
	}
	
	public Image getImage() {
		return img;
	}
}
