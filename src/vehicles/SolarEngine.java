package vehicles;

/**
 * Class represents type of Engine called Solar Engine
 * 
 * @author Mor
 *
 */
public class SolarEngine extends Engine {

	// Solar Engine fuel per KM
	private static final int SOLAR_FUELKM = 1;

	/**
	 * Constructor for SolarEngine class
	 * 
	 * @param maxFuel - capacity of the fuel of this Solar Engine
	 */
	public SolarEngine(int maxFuel) {
		super(SOLAR_FUELKM, maxFuel);
	}

	/**
	 * return copy of this Engine object
	 */
	public Engine getClone() {
		return new SolarEngine(this.getMaxFuel());
	}

}
