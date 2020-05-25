package vehicles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import graphics.CityPanel;
import graphics.interfaces.IDecorateable;
import graphics.interfaces.IDrawable;
import graphics.interfaces.IMoveable;
import vehicles.Location.Direction;

/**
 * Vehicle class represents a general vehicle
 * 
 * @author Mor Hasid 204676332 Zohar Kahlon 311530059
 *
 */
public abstract class Vehicle implements IMoveable, IDrawable, Cloneable, IDecorateable {

	public enum Color {
		red, green, white, silver
	}

	protected int size;
	protected int Vid;
	protected int speed;
	protected Color color;
	protected int numOfWeels;
	protected int numOfPassengers;
	protected Location loc;
	protected double kilo;
	protected boolean Lights;
	protected int fuelConsumption;
	protected CityPanel pan;
	protected BufferedImage img1, img2, img3, img4;
	private int lastCrashID;
	private Runnable adapter;

	protected static final int LOW_DURABILITY = 1;
	protected static final int NORMAL_DURABILITY = 5;
	protected static final int HIGH_DURABILITY = 10;
	private static final int PIXEL_SIZE = 50;
	private static final int MIN_AGE = 18;
	private static int VEHICLE_ID;

	/**
	 * Constructor of Vehicle class
	 * 
	 * @param Vid        - id of this Vehicle
	 * @param color      - color of this Vehicle
	 * @param numOfWeels - number of wheels of this Vehicle
	 * @param loc        - Location of this Vehicle
	 * @param kilo       - how many kilometers passed this Vehicle
	 * @param Lights     - True if the lights of this Vehicle are on. else, false.
	 */
	public Vehicle(int Vid, Color color, int numOfWeels, Location loc, double kilo) {
		this.Vid = Vid;
		this.color = color;
		this.numOfWeels = numOfWeels;
		this.loc = new Location(loc);
		this.kilo = kilo;
		this.Lights = false;
		this.lastCrashID = 0;
	}

	/**
	 * Constructor for Vehicle Class
	 * 
	 * @param size
	 * @param Vid
	 * @param col
	 * @param wheels
	 * @param loc
	 * @param kilo
	 * @param Lights
	 * @param fuelConsumption
	 * @param pan
	 * @param img1
	 * @param img2
	 * @param img3
	 * @param img4
	 */
	public Vehicle(Color col, CityPanel pan) {
		this.size = PIXEL_SIZE;
		this.Vid = VEHICLE_ID;
		incID();
		this.color = col;
		this.loc = new Location(new Point(0, 0), Direction.EAST);
		this.kilo = 0;
		this.Lights = false;
		this.fuelConsumption = 0;
		this.pan = pan;
	}

	/**
	 * True if the lights are on
	 */
	public boolean isOnLights() {
		return false;
	}
	
	/**
	 * removes decorate from object
	 */
	public IDecorateable removeDecoration() {
		return this;
	}
	
	/**
	 * Set normal durability by default
	 * @return NORMAL_DURABILITY = 5
	 */
	public int getDurability() {
		return Vehicle.NORMAL_DURABILITY;
	}
	
	/**
	 * get the thread of this vehicle
	 */
	public Runnable getAdapter() {
		return adapter;
	}

	/**
	 * set the thread of this vehicle
	 */
	public void setAdapter(Runnable adapter) {
		this.adapter = adapter;
	}
	
	/**
	 * ID to 1000 at the start of the program
	 * 
	 * @return true if changed
	 */
	public static boolean initID() {
		Vehicle.VEHICLE_ID = 1000;
		return true;
	}

	/**
	 * Increment ID by one every new vehicle
	 * 
	 * @return
	 */
	private static boolean incID() {
		++Vehicle.VEHICLE_ID;
		return true;
	}

	/**
	 * get the pixel size of this vehicle
	 * @return PIXEL_SIZE
	 */
	public int getSize() {
		return Vehicle.PIXEL_SIZE;
	}
	
	/**
	 * drive with this Vehicle from this Location to p Point
	 * 
	 * @param p
	 * @return false if the vehicle didn't move. else,true.
	 */
	public boolean drive(Point p) {
		if (this.loc.getPoint().equals(p))
			return false;

		Point tempP = new Point(this.loc.getPoint());
		if (this.loc.setP(p))
			this.kilo += tempP.getDistance(p);
		return true;
	}

	/**
	 * my function to print every vehicle his real name (Bike, car...)
	 * 
	 * @return
	 */
	protected abstract String getName();

	/**
	 * toString method for Vehicle class.
	 */
	public String toString() {
		return getName() + " [ID=" + Vid + ", color=" + color + ", numOfWeels=" + numOfWeels + ", " + loc.toString()
				+ ", kilometers=" + kilo + ", Lights=" + (Lights == true ? "On" : "Off") + ", ";
	}

	/**
	 * Get function for inherit classes
	 * 
	 * @return this Location object
	 */
	public Location getLocation() {
		return loc;
	}

	/**
	 * get function for Vehicle ID
	 * 
	 * @return this Vid
	 */
	public int getVid() {
		return this.Vid;
	}

	/**
	 * load all this Vehicle images to img1,2,3,4 from path
	 */
	public void loadImages() {
		try {
			String path = "graphics/" + this.getColor() + this.getName();
			img1 = ImageIO.read(new File(path + "North.png"));
			img2 = ImageIO.read(new File(path + "South.png"));
			img3 = ImageIO.read(new File(path + "East.png"));
			img4 = ImageIO.read(new File(path + "West.png"));
		} catch (IOException e) {
			System.out.println("Cannot load " + this.getName() + " image");
		}
	}

	/**
	 * my function to convert color name to java.awt.Color
	 * 
	 * @param c - name of the color
	 * @return java.awt.Color
	 */
	public static java.awt.Color nameToJavaColor(String c) {
		if (c.equals("red") || c.equals("Red"))
			return java.awt.Color.red;
		else if (c.equals("white") || c.equals("White"))
			return java.awt.Color.white;
		else if (c.equals("silver") || c.equals("Silver"))
			return (java.awt.Color.lightGray);
		else
			return java.awt.Color.green;
	}

	/**
	 * lights operation will turn on the lights when its off and vice versa
	 */
	public void lightsOperation() {
		Lights = !Lights;
	}

	/**
	 * draw the vehicle according to the Direction
	 */
	public void drawObject(Graphics g) {

		if (loc.getOrientation() == Direction.NORTH) // drives to right side
			g.drawImage(img1, loc.getPoint().getX(), loc.getPoint().getY(), size, size * 2, pan);
		else if (loc.getOrientation() == Direction.SOUTH)// drives to the south side
			g.drawImage(img2, loc.getPoint().getX(), loc.getPoint().getY(), size, size * 2, pan);
		else if (loc.getOrientation() == Direction.EAST) { // drives to the east side
			g.drawImage(img3, loc.getPoint().getX(), loc.getPoint().getY(), size * 2, size, pan);
		} else if (loc.getOrientation() == Direction.WEST) // drives to the west side
			g.drawImage(img4, loc.getPoint().getX(), loc.getPoint().getY(), size * 2, size, pan);

	}

	/**
	 * return vehicle speed
	 */
	public int getSpeed() {
		return this.speed;
	}

	/**
	 * return sum of Consumptions
	 */
	public int getFuelConsumption() {
		return this.fuelConsumption;
	}

	/**
	 * set the Direction get the next Point to move the vehicle
	 * 
	 * @return Point to move
	 */
	public Point nextLocation(int difference) {
		int x = loc.getPoint().getX(), y = loc.getPoint().getY();

		if (x == 0 && y == 0) 
			topLeft();
		else if (y == 0 && x == pan.getWidth() - difference)
			topRight();
		else if (x == pan.getWidth() - difference && y == pan.getHeight() / 2 - difference)
			middleRight();
		else if (x == pan.getWidth() - difference && y == pan.getHeight() - difference)
			bottomRight();
		else if (x == 0 && y == pan.getHeight() - difference) 
			bottomLeft();
		else if (x == 0 && y == pan.getHeight() / 2 - difference)
			middleLeft();
		
		return keepGoing();
	}
	
	/**
	 * vehicle's location at the top left corner
	 * function will decide which direction to change
	 * -------------------------|
	 *	(v)					    |
	 * 		------------		|
	 * 							|
	 * 		------------		|
	 * 							|
	 * -------------------------|
	 */
	protected void topLeft() {
		Direction d = loc.getOrientation();
		if (d == Direction.NORTH) {
			loc.setOrientation(Direction.EAST);
		} else if (d == Direction.WEST) {
			loc.setOrientation(Direction.SOUTH);
		}
	}
	
	/**
	 * vehicle's location at the top right corner
	 * function will decide which direction to change
	 * -------------------------|
	 *						(v) |
	 * 		------------		|
	 * 							|
	 * 		------------		|
	 * 							|
	 * -------------------------|
	 */
	protected void topRight(){
		Direction d = loc.getOrientation();
		if (d == Direction.EAST) {
			loc.setOrientation(Direction.SOUTH);
		} else if (d == Direction.NORTH) {
			loc.setOrientation(Direction.WEST);
		}
	}
	
	/**
	 * vehicle's location at the middle right corner
	 * function will decide which direction to change
	 * -------------------------|
	 *						    |
	 * 		------------		|
	 * 						(v)	|
	 * 		------------		|
	 * 							|
	 * -------------------------|
	 */
	protected void middleRight() {
		Random rand = new Random();
		Direction d = loc.getOrientation();

		if (d == Direction.SOUTH || d == Direction.NORTH) {
			if (rand.nextBoolean())
				loc.setOrientation(Direction.WEST);
		}
		if (d == Direction.EAST) {
			if (rand.nextBoolean())
				loc.setOrientation(Direction.SOUTH);
			else
				loc.setOrientation(Direction.NORTH);
		}
	}

	/**
	 * vehicle's location at the bottom right corner
	 * function will decide which direction to change
	 * -------------------------|
	 *						    |
	 * 		------------		|
	 * 							|
	 * 		------------		|
	 * 						(v)	|
	 * -------------------------|
	 */
	protected void bottomRight() {
		Direction d = loc.getOrientation();
		if (d == Direction.SOUTH)
			loc.setOrientation(Direction.WEST);
		else if (d == Direction.EAST)
			loc.setOrientation(Direction.NORTH);
	}
	
	/**
	 * vehicle's location at the bottom left corner
	 * function will decide which direction to change
	 * -------------------------|
	 *						    |
	 * 		------------		|
	 * 							|
	 * 		------------		|
	 * (v)						|
	 * -------------------------|
	 */
	protected void bottomLeft() {
		Direction d = loc.getOrientation();
		if (d == Direction.WEST) {
			loc.setOrientation(Direction.NORTH);
		} else if (d == Direction.SOUTH)
			loc.setOrientation(Direction.EAST);
	}
	
	/**
	 * vehicle's location at the middle left corner
	 * function will decide which direction to change
	 * -------------------------|
	 *						    |
	 * 		------------		|
	 * (v)						|
	 * 		------------		|
	 * 							|
	 * -------------------------|
	 */
	protected void middleLeft() {
		Random rand = new Random();
		Direction d = loc.getOrientation();
		
		if (d == Direction.WEST) {
			if (rand.nextBoolean())
				loc.setOrientation(Direction.NORTH);
			else
				loc.setOrientation(Direction.SOUTH);
		}
		else if (d == Direction.SOUTH || d == Direction.NORTH) {
			if (rand.nextBoolean())
				loc.setOrientation(Direction.EAST);
		}
	}
	
	/**
	 * return the next point to move the same direction
	 * 
	 * @return Point to move
	 */
  	protected Point keepGoing() {
		if (loc.getOrientation() == Direction.EAST)
			return new Point(loc.getPoint().getX() + this.getSpeed(), loc.getPoint().getY());
		if (loc.getOrientation() == Direction.WEST)
			return new Point(loc.getPoint().getX() - this.getSpeed(), loc.getPoint().getY());
		if (loc.getOrientation() == Direction.SOUTH)
			return new Point(loc.getPoint().getX(), loc.getPoint().getY() + this.getSpeed());
		// if Direction is North
		return new Point(loc.getPoint().getX(), loc.getPoint().getY() - this.getSpeed());
	}

	/**
	 * return the name of the color of this vehicle
	 */
	public String getColor() {
		return color.name();
	}

	/**
	 * @return the minAge
	 */
	public static int getMinAge() {
		return MIN_AGE;
	}

	/**
	 * if the vehicle can move, move the vehicle to p point and call paintComponent
	 */
	public boolean move(Point p) {
		if (canMove()) {
			this.drive(p);
			pan.repaint();
			return true;
		}
		return false;
	}

	/**
	 * my function to convert color name to enum Color
	 * 
	 * @param c - name of the color
	 * @return my enum Color
	 */
	public static Color nameToColor(String c) {
		if (c.equals("red") || c.equals("Red"))
			return Color.red;
		else if (c.equals("white") || c.equals("White"))
			return Color.white;
		else if (c.equals("silver") || c.equals("Silver"))
			return (Color.silver);
		else
			return Color.green;
	}

	/**
	 * return vehicle number of wheels
	 * 
	 * @return this vehicle wheels
	 */
	public int getWheels() {
		return this.numOfWeels;
	}

	/**
	 * indicate for this vehicle lights
	 * 
	 * @return true if the lights are on. else, false
	 */
	public boolean getLights() {
		return this.Lights;
	}
	
	/**
	 * if the vehicle refueled
	 */
	public boolean getFullEnergy() {
		synchronized(adapter) {
			adapter.notify();
		}
		
		return true;
	}

	/**
	 * sum of the kilometers
	 * 
	 * @return this vehicle sum KM
	 */
	public double getKio() {
		return this.kilo;
	}

	/**
	 * get the fuel amount for Car and the animal's energy for Carriage
	 * 
	 * @return
	 */
	public abstract int getFuelEnergy();

	/**
	 * @return the lastCrashID
	 */
	public int getLastCrashID() {
		return lastCrashID;
	}

	/**
	 * @param lastCrashID the lastCrashID to set
	 */
	public void setLastCrashID(int lastCrashID) {
		this.lastCrashID = lastCrashID;
	}
	
}
