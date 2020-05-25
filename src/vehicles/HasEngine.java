package vehicles;

import java.util.Scanner;

import graphics.CityPanel;

/**
 * This class represents a general Vehicle that has an engine
 * 
 * @author Mor Hasid 204676332 Zohar Kahlon 311530059
 */
public abstract class HasEngine extends Vehicle {

	protected Engine engine;
	protected int fuleAmaount;

	// Scanner Object
	static Scanner input = new Scanner(System.in);

	/**
	 * Constructor of HasEngine class
	 * 
	 * @param Vid         - id of this Vehicle
	 * @param color       - color of this Vehicle
	 * @param numOfWeels  - number of wheels of this Vehicle
	 * @param loc         - Location of this Vehicle
	 * @param kilo        - how many kilometers passed this Vehicle
	 * @param Lights      - True if the lights of this Vehicle are on. else, false.
	 * @param engine      - represents the engine of this Vehicle
	 * @param fuelAmaount - current fuel of this Vehicle
	 * @param minAge      - the minimum age who allowed to drive this Vehicle.
	 */
	public HasEngine(Color col, CityPanel pan, Engine engine) {
		super(col, pan);
		this.engine = engine.getClone(); // Copy of Solar/Benzene Engine object
	}

	/**
	 * Sets the current fuel to the max capacity
	 * 
	 * @return False if the tank fuel is full. else, true.
	 */
	public boolean Refuel() {
		if (this.fuleAmaount == engine.getMaxFuel())
			return false;
		this.fuleAmaount = engine.getMaxFuel();
		return true;
	}

	/**
	 * toString method of HasEngine class.
	 */
	public String toString() {

		return super.toString() + "Current fuel=" + fuleAmaount + ", Minimum Age=" + Vehicle.getMinAge() + ", ";
	}

	/**
	 * get Engine of this Vehicle
	 * 
	 * @return this engine reference
	 */
	protected Engine getEngine() {
		return this.engine;
	}

}
