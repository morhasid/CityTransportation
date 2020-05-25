package vehicles;

import graphics.interfaces.ICloneable;

/**
 * This class represents a single point
 * 
 * @author Mor Hasid 204676332 Zohar Kahlon 311530059
 * 
 *
 */
public class Point implements ICloneable {
	private int x, y;

	/**
	 * Default Constructor sets the Point parameters x and y to 0
	 */
	public Point() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Constructor sets the Point parameters x and y
	 * 
	 * @param x
	 * @param y
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Copy Constructor of Point class
	 * 
	 * @param p
	 */
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * get the value of x in this Point
	 * 
	 * @return x value of this Point
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * get the value of y in this Point
	 * 
	 * @return y value of this Point
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Changes the x value in this Point
	 * 
	 * @param x
	 * @return true if changed successfully. else, false
	 */
	public boolean setX(int x) {
		this.x = x;
		return true;
	}

	/**
	 * Changes the y value in this Point
	 * 
	 * @param y
	 * @return true if changed successfully. else, false
	 */
	public boolean setY(int y) {
		this.y = y;
		return true;
	}

	/**
	 * Checks if two points are equal
	 * 
	 * @param p
	 * @return true if this Point equals p Point. else, false
	 */
	public boolean equals(Point p) {
		return ((this.x == p.x) && (this.y == p.y));
	}

	/**
	 * get distance between this Point and p Point
	 * 
	 * @param p
	 * @return distance calculation
	 */
	public int getDistance(Point p) {
		return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
	}

	/**
	 * toString method of Point class
	 */
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	/**
	 * returns replicate of this Point
	 */
	public Object clone() {
		return new Point(this);
	}

}
