package vehicles;

import graphics.CityPanel;

/**
 * Class represents a Car
 * 
 * @author Mor
 *
 */
public class Car extends HasEngine {

	// Car's number of wheels - 4 and Car's fuel capacity - 40 and Car's speed - 4
	// KM/h
	private static final int CAR_WEELS = 4;
	public static final int CAR_MAX_FULE = 40;
	private static final int CAR_SPEED = 4;
	private static final int CAR_PASSENGERS = 5;
	
	/**
	 * Set High durability level for car
	 * @return HIGH_DURABILITY = 10
	 */
	public int getDurability() {
		return Vehicle.HIGH_DURABILITY;
	}

	/**
	 * Constructor Car class.
	 * 
	 * @param Vid             - id of this Car
	 * @param color           - color of this Car
	 * @param numOfWeels      - number of wheels of this Car
	 * @param loc             - Location of this Car
	 * @param kilo            - how many kilometers passed this Car
	 * @param Lights          - True if the lights of this Car are on. else, false.
	 * @param engine          - represents the engine of this Car
	 * @param fuelAmaount     - current fuel of this Car
	 * @param minAge          - the minimum age who allowed to drive this Car.
	 * @param numOfPassengers - number of passengers in this Car
	 */
	public Car(Color col, CityPanel pan, Engine engine) {
		super(col, pan, engine);
		this.numOfPassengers = CAR_PASSENGERS;
		this.numOfWeels = CAR_WEELS;

		// Promises that every car fuel capacity will be 40.
		// because we can get(by mistake) at main function another value of this
		// variable in SolarEngine constructor.
		this.getEngine().setMaxFuel(CAR_MAX_FULE);
		this.fuleAmaount = CAR_MAX_FULE;
		this.speed = CAR_SPEED;
	}

	/**
	 * return the real name of the Vehicle(my function)
	 */
	protected String getName() {
		return "Car";
	}

	/**
	 * toString method of Car class
	 */
	public String toString() {
		return super.toString() + this.getEngine().toString() + "Passngers number=" + numOfPassengers + "]";
	}

	/**
	 * The same speed for all cars
	 * 
	 * @return every car speed
	 */
	public static int getCarSpeed() {
		return CAR_SPEED;
	}

	/**
	 * return "Car"
	 */
	public String getVehicleName() {
		return "Car";
	}

	/**
	 * for a Car will return the fuel amount
	 */
	public int getFuelEnergy() {
		return this.fuleAmaount;
	}

	/**
	 * Drives to p Point, update the fuel returns true if the Car moved. else,
	 * false.
	 */
	public boolean drive(Point p) {
		// int choose;

		// Calculate fuel needed
		int distance = this.getLocation().getPoint().getDistance(p);
		int fuelNeeded = this.engine.getFuelPerKm() * distance;

		/*
		 * // If the fuel is not enough, Vehicle should not move if (this.fuleAmaount <
		 * fuelNeeded) { do { System.out.println(
		 * "fuel is not enough, vehicle didn't move\n" +
		 * "Do you want to refule now?\n1. Yes\n2. No"); choose = input.nextInt(); }
		 * while (!(choose >= 1 && choose <= 2));
		 * 
		 * // User choose not to refuel if (choose == 2) return false;
		 * 
		 * // If user choose to refuel this.Refuel(); }
		 */
		// If the points aren't equal, move the Vehicle and update fuel
		if (super.drive(p)) {
			this.fuleAmaount -= fuelNeeded;
			return true;
		}
		// If the points are equals, Vehicle didn't move.
		return false;
	}

	/**
	 * IF the car can move
	 * 
	 * @return true if the fuel enough for minimum 1 KM
	 */
	public boolean canMove() {
		return this.fuleAmaount >= engine.getFuelPerKm();
	}

	/**
	 * when Car getMaxEnergy we have to refuel and update fuelConsumption
	 */
	public boolean getFullEnergy() {
		super.getFullEnergy();
		this.Refuel();
		this.fuelConsumption += CAR_MAX_FULE;
		return true;
	}

	/**
	 * get the type of this car's engine
	 * 
	 * @return true if SolarEngine, false if BenzineEngine
	 */
	public boolean isSolarCar() {
		return this.engine instanceof SolarEngine;
	}

	/**
	 * get the next point for car object
	 */
	public Point nextLocation(int difference) {
		return super.nextLocation(difference + 2);
	}
	
	/**
	 * returns replicate of this Car
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Car carClone = (Car) super.clone();
		carClone.engine = (Engine) this.engine.clone();
		return carClone;
	}

}
