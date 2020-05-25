package vehicles;

import graphics.CityPanel;

/**
 * This class represents a Carriage.
 * 
 * @author Mor
 *
 */
public class Carriage extends Vehicle {

	private PackAnimal animal;

	private static final int CARRIAGE_SPEED = 1;
	private static final int CARRIAGE_WEELS = 4;
	private static final int CARRIAGE_PASSENGERS = 2;

	/**
	 * Constructor of Carriage class.
	 * 
	 * @param Vid    - id of this Carriage
	 * @param color  - color of this Carriage
	 * @param loc    - Location of this Carriage
	 * @param kilo   - how many kilometers passed this Carriage
	 * @param Lights - True if the lights of this Carriage are on. else, false.
	 * @param animal - name of the animal who pull the Carriage.
	 */
	public Carriage(int Vid, Color color, Location loc, double kilo) {
		super(Vid, color, CARRIAGE_WEELS, loc, kilo);
		this.speed = CARRIAGE_SPEED;
	}

	/**
	 * Constructor of Carriage
	 * 
	 * @param col - color of the carriage
	 * @param pan - city panel
	 */
	public Carriage(Color col, CityPanel pan) {
		super(col, pan);
		this.speed = CARRIAGE_SPEED;
		this.numOfWeels = CARRIAGE_WEELS;
		this.numOfPassengers = CARRIAGE_PASSENGERS;
	}

	/**
	 * my function to print every vehicle his real name
	 */
	protected String getName() {
		return "Carriage";
	}

	/**
	 * toString method of Carriage class.
	 */
	public String toString() {
		return super.toString() + "Animal=" + animal + ", Speed=" + this.speed + "]";
	}

	/**
	 * get the animal of this carriage
	 * 
	 * @return this.animal
	 */
	public PackAnimal getAnimal() {
		return animal;
	}

	/**
	 * sets the animal of this carriage
	 * 
	 * @param animal - animal of this carriage
	 * @return true if changed successfully
	 */
	public boolean setAnimal(PackAnimal animal) {
		this.animal = animal;
		return true;
	}

	/**
	 * return the animal's energy
	 */
	public int getFuelEnergy() {
		return animal.getEnergy();
	}

	/**
	 * return "Carriage"
	 */
	public String getVehicleName() {
		return this.getName();
	}

	/**
	 * the carriage can move only if the animal can move
	 */
	public boolean move(Point p) {
		if (this.animal.move(p))
			return super.move(p);
		return false;
	}

	/**
	 * IF the Carriage can move
	 * 
	 * @return true if the Animal can move
	 */
	public boolean canMove() {
		return this.animal.canMove();
	}

	/**
	 * when carriage getMaxEnergy the animal have to eat and update fuelConsumption
	 */
	public boolean getFullEnergy() {
		super.getFullEnergy();
		this.animal.eat();
		this.fuelConsumption += PackAnimal.getMaxEnergy();
		return true;
	}

	/**
	 * returns replicate of this Animal
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
