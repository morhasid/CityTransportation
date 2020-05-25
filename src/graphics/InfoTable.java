package graphics;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import graphics.interfaces.IDecorateable;
import vehicles.Vehicle;

public class InfoTable implements Observer{
	
	static private volatile InfoTable instance = null;
	
	private static final String[] columnNames = { "Vehicle", "ID", "Color", "Wheels", "Speed", "Fuel amount",
			"Distance", "Fuel consumption", "Lights", "last Crash ID" };
	private JTable infoTable;
	private DefaultTableModel model;
	private JFrame tableFrame;
	private Map<Integer, Object[]> tableDic;
	private Map<Integer, Vehicle> vehiclesDic;
	
	/**
	 * Get CityPanel object (Thread Safe Singleton)
	 */
	public static InfoTable getInstance(Map<Integer, Vehicle> vehiclesDic) {
		if (instance == null)
			synchronized (InfoTable.class) {
				if (instance == null)
					instance = new InfoTable(vehiclesDic);
			}
		return instance;
	}
		
	/**
	 * Singleton class represents the info table
	 */
	private InfoTable(Map<Integer, Vehicle> vehiclesDic) {
		
		//Set Vehicles dictionaries
		this.vehiclesDic = vehiclesDic;
		
		// initialize Dictionary for info table
		tableDic = new ConcurrentHashMap<Integer, Object[]>();
	}
	
	/**
	 * Info table will update every change in vehicles
	 */
	public void update(Observable o, Object arg) {
		updateTable();
	}
	
	/**
	 * Update table and show frame
	 */
	public void ShowTable() {
		updateTable();
		tableFrame.setVisible(true);
		
	}
	
	/**
	 * Update table rows
	 */
	public synchronized void updateTable() {
		
		// Create the Frame and tale for the info
		tableFrame = new JFrame("Info");
		infoTable = new JTable();
		
		// create a table model
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);

		// set the model to the table
		infoTable.setModel(model);

		// create JScrollPane
		JScrollPane pane = new JScrollPane(infoTable);
		pane.setBounds(0, 0, 880, 200);
		tableFrame.setLayout(null);
		tableFrame.add(pane);
		tableFrame.setSize(900, 400);
		
		for (Vehicle v : vehiclesDic.values()) {

			Object[] row = { v.getVehicleName(), v.getVid(), v.getColor(), v.getWheels(), v.getSpeed(),
					v.getFuelEnergy(), v.getKio(), v.getFuelConsumption(), ((IDecorateable)v).isOnLights(), v.getLastCrashID() };

			int id = v.getVid();
			if (tableDic.containsKey(id))
				tableDic.replace(id, row);
			else
				tableDic.put(id, row);
		}
		// add row(s) to the model
		for (Object[] r : tableDic.values())
			model.addRow(r);
		
	}


}
