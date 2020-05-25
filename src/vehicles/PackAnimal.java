package vehicles;

import graphics.interfaces.IAnimal;

public class PackAnimal implements IAnimal, Cloneable {

	private String name;
	private int energy;
	private Carriage carriage;

	private static final int MAX_ENERGY = 1000; // every animal max energy
	private static final int ENERGY_PER_KM = 20; // every animal energy for 1 KM

	/**
	 * Constructor PackAnimal class
	 * 
	 * @param name   - name of the animal
	 * @param energy - level of the energy of the animal
	 */
	public PackAnimal(String name, int energy) {
		this.name = name;
		this.energy = energy;
	}

	/**
	 * Constructor for Pack Animal
	 */
	public PackAnimal() {
		this.name = "Pack Animal";
		this.energy = MAX_ENERGY;
	}

	/**
	 * sets the carriage for this animal
	 * 
	 * @param carriage - the carriage of this animal
	 * @return true if changed successfully
	 */
	public boolean setCarriage(Carriage carriage) {
		this.carriage = carriage;
		return true;
	}

	/**
	 * returns the name of the animal
	 * 
	 * @return this.name
	 */
	public String getAnimalName() {
		return this.name;
	}

	/**
	 * when an animal eats the level of energy will update to the maximum
	 * 
	 * @return true if ate successfully. else, false
	 */
	public boolean eat() {
		this.energy = PackAnimal.MAX_ENERGY;
		return true;
	}

	/**
	 * move this animal to p point if the energy is enough return true if moved
	 * successfully. else, false.
	 */
	public boolean move(Point p) {

		// Calculate energy needed to move
		int distance = this.carriage.getLocation().getPoint().getDistance(p);
		int energyNeeded = distance * PackAnimal.ENERGY_PER_KM;

		// Not enough Energy, didn't move
		if (this.energy < energyNeeded) {
			return false;
		}

		// if the energy is enough
		this.energy -= energyNeeded;
		return true;
	}

	/**
	 * returns the Vehicle name of this animal
	 */
	public String getVehicleName() {
		return carriage.getName();
	}

	/**
	 * returns the Vehicle speed of this animal
	 */
	public int getSpeed() {
		return carriage.getSpeed();
	}

	/**
	 * returns energy consumption
	 */
	public int getFuelConsumption() {
		return this.carriage.getFuelConsumption();
	}

	/**
	 * If the animal can move
	 */
	public boolean canMove() {
		return this.energy >= ENERGY_PER_KM;
	}

	/**
	 * get the number of max energy
	 * 
	 * @return MAX_ENERGY = 1000
	 */
	public static int getMaxEnergy() {
		return MAX_ENERGY;
	}

	/**
	 * getMaxEnergy the animal have to eat
	 * 
	 */
	public boolean getFullEnergy() {
		eat();
		return true;
	}

	/**
	 * energy of the animal
	 * 
	 * @return this.energy
	 */
	public int getEnergy() {
		return this.energy;
	}

	/**
	 * returns replicate of this Animal
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}


	/**
	 * durability of the animal same as carriage
	 */
	public int getDurability() {
		return this.carriage.getDurability();
	}

}
