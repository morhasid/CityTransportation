package vehicles;

import java.awt.Graphics;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import graphics.CityPanel;
import graphics.interfaces.IDecorateable;

/**
 * Adapter from vehicle class to vehicle class supports pseudo parallel
 * programming
 * 
 * @author Mor
 *
 */
public class AdaperOfVehicle extends Observable implements Runnable, Observer, IDecorateable {

	private Vehicle v;
	private static Map<Integer, Vehicle> vehiclesDic;
	private static Vehicle crashed1, crashed2;
	private boolean flag = true;

	/**
	 * Constructor
	 */
	public AdaperOfVehicle(Vehicle v) {
		this.v = v;
	}

	/**
	 * set all Vehicles Dictionary
	 */
	public static void setDictionary(Map<Integer, Vehicle> vehicleDic) {
		vehiclesDic = vehicleDic;
	}

	/**
	 * check if 2 vehicles crashed
	 * 
	 * @return true if crashed and set the static Vehicles crashed1, crashed2
	 */
	private boolean checkCrash() {
		for (Vehicle v1 : vehiclesDic.values())
			for (Vehicle v2 : vehiclesDic.values()) {
				if (v1 != v2) {
					if ((Math.abs(
							v1.getLocation().getPoint().getX() - v2.getLocation().getPoint().getX()) <= v1.getSize()
									* 2)
							&& (Math.abs(v1.getLocation().getPoint().getY() - v2.getLocation().getPoint().getY()) <= v1
									.getSize() * 2)) {
						synchronized (this) {
							crashed1 = v1;
							crashed2 = v2;
						}
						return true;
					}
				}
			}
		return false;
	}

	/**
	 * Remove the appropriate vehicles after crash
	 */
	private synchronized void crashTreatment() {
		CityPanel citypanel = crashed1.pan;
		crashed1.setLastCrashID(crashed2.getVid());
		crashed2.setLastCrashID(crashed1.getVid());
		citypanel.updateTable();

		if (crashed1.getDurability() == crashed2.getDurability()) {
			citypanel.removeVehiclefromCity(crashed1);
			citypanel.removeVehiclefromCity(crashed2);
		} else if (crashed1.getDurability() > crashed2.getDurability())
			citypanel.removeVehiclefromCity(crashed2);
		else
			citypanel.removeVehiclefromCity(crashed1);

		// if the Queue thread didn't starts yet
		if (citypanel.getQueueManager() != null)
			synchronized (citypanel.getQueueManager()) {
				citypanel.getQueueManager().notify();
			}
	}

	@Override
	public void run() {

		// While we didn't exit the system or thread interrupt
		while (flag) {
			if (checkCrash()) {
				crashTreatment();
				continue;
			}
			// move the vehicle
			if (v.move(v.nextLocation(v.getSize()))) {
				
				// boolean parameter in order to not change the fuel button to red
				notifyObservers(false);
				try {
					Thread.sleep(1000 / v.getSpeed());
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
			}
			// if the vehicle can't move, wait for fuel/energy.
			else {
				// Change the fuel button to RED
				setChanged();
				notifyObservers(true);
				try {
					synchronized (v.getAdapter()) {
						v.getAdapter().wait();
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
			}
		}
	}

	/**
	 * this will stop the Thread by stopping the while loop
	 */
	public synchronized void update(Observable o, Object arg) {
		StopThread();
	}
	
	public void StopThread() {
		flag = false;
	}

	@Override
	public boolean isOnLights() {
		return v.isOnLights();
	}

	@Override
	public IDecorateable removeDecoration() {
		return v.removeDecoration();
	}

	@Override
	public void drawObject(Graphics g) {
		v.drawObject(g);
	}

}
