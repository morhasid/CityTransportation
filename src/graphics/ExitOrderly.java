package graphics;

import java.util.Observable;

/**
 * ExitOrderly object will make sure safe exit
 */
public class ExitOrderly extends Observable{
	
	static private volatile ExitOrderly instance = null;
	
	private CityPanel citypanel;
	
	/**
	 * private constructor
	 */
	private ExitOrderly(CityPanel citypanel) {
		this.citypanel = citypanel;
	}
	
	/**
	 * Get ExitOrderly object
	 */
	public static ExitOrderly getInstance(CityPanel citypanel) {
		if (instance == null)
			synchronized (ExitOrderly.class) {
				if (instance == null)
					instance = new ExitOrderly(citypanel);
			}
		return instance;
	}
	
	/**
	 * Stop all threads
	 */
	public void Exit() {
		citypanel.getVehiclesDic().clear();
		setChanged();
		notifyObservers();
		CityPanel.pool.shutdown();
	}

}
