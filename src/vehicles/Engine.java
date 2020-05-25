package vehicles;

/**
 * This class represents a general Engine.
 * 
 * @author Mor
 *
 */
public abstract class Engine implements Cloneable {

	private int fuelKm, maxFuel;

	/**
	 * Constructor of general Engine
	 * 
	 * @param fuelKm  - fuel consumption per kilometer
	 * @param maxFuel - capacity of the fuel tank
	 */
	public Engine(int fuelKm, int maxFuel) {
		this.fuelKm = fuelKm;
		this.maxFuel = maxFuel;
	}

	/**
	 * Get capacity of fuel tank
	 * 
	 * @return maxF
	 */
	public int getMaxFuel() {
		return maxFuel;
	}

	/**
	 * set the capacity of the fuel tank
	 * 
	 * @param fuel - fuel amount
	 * @return true if changed successfully. else, false.
	 */
	public Boolean setMaxFuel(int fuel) {
		if (fuel <= 0)
			return false;
		this.maxFuel = fuel;
		return true;
	}

	/**
	 * get the amount of Fuel per Kilometer of this Vehicle
	 * 
	 * @return this value
	 */
	public int getFuelPerKm() {
		return this.fuelKm;
	}

	/**
	 * Engine will eventually be an object from SolarEngine or BenzineEngine Each
	 * class will rewrite the method so that it returns a copy of itself
	 * 
	 * @return Copy of this object
	 */
	public abstract Engine getClone();

	/**
	 * toString method of Engine class
	 */
	public String toString() {
		return "Engine [Fuel per KM=" + fuelKm + ", Capacity tank fuel=" + maxFuel + "], ";
	}

	/**
	 * returns replicate of this Engine
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
