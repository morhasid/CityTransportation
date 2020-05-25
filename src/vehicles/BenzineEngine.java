package vehicles;

/**
 * Class represents type of Engine called Benzene Engine
 * 
 * @author Mor
 *
 */
public class BenzineEngine extends Engine {

	// Benzene Engine fuel per KM
	private static final int BENZINE_FUELKM = 2;

	/**
	 * Constructor for BenzineEngine class
	 * 
	 * @param maxFuel - capacity of the fuel of this BenzineEngine
	 */
	public BenzineEngine(int maxFuel) {
		super(BENZINE_FUELKM, maxFuel);
	}

	/**
	 * return copy of this object
	 */
	public Engine getClone() {
		return new BenzineEngine(this.getMaxFuel());
	}

}
