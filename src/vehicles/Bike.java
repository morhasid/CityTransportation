package vehicles;

import graphics.CityPanel;

/**
 * Class represents a Bike.
 * 
 * @author Mor
 *
 */
public class Bike extends Vehicle{

	private int numOfGears;

	private static final int BIKE_SPEED = 10;
	private static final int BIKE_WHEELS = 2;
	private static final int BIKE_PASSENGERS = 1;

	/**
	 * Constructor of Bike class
	 * 
	 * @param Vid        - id of this Bike
	 * @param color      - color of this Bike
	 * @param numOfWeels - number of wheels of this Bike
	 * @param loc        - Location of this Bike
	 * @param kilo       - how many kilometers passed this Bike
	 * @param Lights     - True if the lights of this Bike are on. else, false.
	 * @param numOfGears - number of gears of this Bike.
	 */
	public Bike(int Vid, Color color, int numOfWeels, Location loc, double kilo, int numOfGears) {
		super(Vid, color, numOfWeels, loc, kilo);
		this.numOfGears = numOfGears;
		this.speed = BIKE_SPEED;
	}

	/**
	 * Constructor of Bike class
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
	 * @param numOfGears
	 */
	public Bike(Color col, CityPanel pan, int numOfGears) {
		super(col, pan);
		this.numOfGears = numOfGears;
		this.numOfWeels = BIKE_WHEELS;
		this.numOfPassengers = BIKE_PASSENGERS;
		this.speed = BIKE_SPEED;
	}
	
	/**
	 * Set low durability for bike
	 * @return LOW_DURABILITY = 1
	 */
	public int getDurability() {
		return Vehicle.LOW_DURABILITY;
	}

	/**
	 * Prints every Vehicle his real name(my function)
	 */
	protected String getName() {
		return "Bike";
	}

	/**
	 * toString method of Bike class.
	 */
	public String toString() {
		return super.toString() + "Number of gears=" + numOfGears + ", Speed=" + this.speed + "]";
	}

	/**
	 * return Fuel/Energy of this bike
	 * Bike don't have Fuel/Energy
	 */
	public int getFuelEnergy() {
		return 0;
	}

	/**
	 * return "Bike"
	 */
	public String getVehicleName() {
		return this.getName();
	}

	/**
	 * IF the Bike can move
	 * 
	 * @return true always, bike don't have fuel/energy
	 */
	public boolean canMove() {
		return true;
	}

	/**
	 * Bike always have energy
	 * 
	 */
	public boolean getFullEnergy() {
		return true;
	}



}
