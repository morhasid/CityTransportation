package vehicles;

/**
 * This class represents Location by 1 Point and an orientation.
 * 
 * @author Mor
 *
 */
public class Location {

	/**
	 * the orientation must be 1 of this 4 options: NORTH, SOUTH, EAST, WEST
	 * 
	 * @author Mor
	 *
	 */
	public enum Direction {
		NORTH, SOUTH, EAST, WEST;
	}

	private Point p;
	private Direction orientation;

	/**
	 * Constructor creates copy of p Point into this location point copy the o
	 * orientation into this location
	 * 
	 * @param p
	 * @param o
	 */
	public Location(Point p, Direction o) {
		this.p = new Point(p);
		this.orientation = o;
	}

	/**
	 * Copy Constructor of Location class
	 * 
	 * @param loc
	 */
	public Location(Location loc) {
		this.orientation = loc.orientation;
		this.p = new Point(loc.p);
	}

	/**
	 * Changes this Location Point to p Point
	 * 
	 * @param p
	 * @return true if changes successfully. else, false
	 */
	public boolean setP(Point p) {
		this.p.setX(p.getX());
		this.p.setY(p.getY());
		return true;
	}

	/**
	 * Get the Point in this Location.
	 * 
	 * @return copy of the Point in this Location.
	 */
	public Point getPoint() {
		return new Point(this.p);
	}

	/**
	 * toString method of Location class
	 */
	public String toString() {
		return "Location [" + p.toString() + ", orientation=" + orientation + "]";
	}

	/**
	 * get the Direction of this location
	 * 
	 * @return orientation
	 */
	public Direction getOrientation() {
		return this.orientation;
	}

	/**
	 * set Direction to the given parameter
	 * 
	 * @param orientation - new direction
	 * @return true if changed successfully
	 */
	public boolean setOrientation(Direction orientation) {
		this.orientation = orientation;
		return true;
	}

}
