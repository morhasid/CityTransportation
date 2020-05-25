package MementoDP;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import vehicles.Vehicle;

/**
 * in every memento object we will save the states
 */
public class Memento {
	
	private Map<Integer, Vehicle> vehiclesDic;
	
	/**
	 * constructor will set a copy of the dictionary of all vehiceles
	 */
	public Memento(Map<Integer, Vehicle> vehiclesDic) {
		// Copy constructor of ConcurrentHashMap
		this.vehiclesDic = new ConcurrentHashMap<Integer, Vehicle>(vehiclesDic);
	}
	
	/**
	 * get the state saved in the memento
	 */
	public Map<Integer, Vehicle> getState(){
		return this.vehiclesDic;
	}

}
