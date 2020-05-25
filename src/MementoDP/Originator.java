package MementoDP;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import vehicles.Vehicle;

public class Originator {
	
	private Map<Integer, Vehicle> state;
	
	/**
	 * Sets the current state 
	 */
	public void setState(Map<Integer, Vehicle> state) {
		this.state = state;
	}
	
	/**
	 * returns the current state
	 */
	public Map<Integer, Vehicle> getState(){
		return new ConcurrentHashMap<Integer, Vehicle>(this.state);
	}
	
	/**
	 * Creates new memento with the current state
	 */
	public Memento createMemento() {
		return new Memento(state);
	}
	
	/**
	 * set the last saved state to the new current state
	 */
	public void setMemento(Memento memento) {
		state = memento.getState();
	}

}
