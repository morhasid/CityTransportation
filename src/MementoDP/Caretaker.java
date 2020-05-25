package MementoDP;

import java.util.ArrayList;

/**
 * Caretaker object will manage all mementos
 */
public class Caretaker {

	private ArrayList<Memento> statesList = new ArrayList<Memento>();
	
	/**
	 * add memento to the list
	 */
	public void addMemento(Memento m) {
		statesList.add(m);
	}
	
	/**
	 * get the last saved memento in the list
	 */
	public Memento getMemento() {
		return statesList.get(statesList.size() - 1);
	}
}
