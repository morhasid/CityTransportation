package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Decorator.LightsDec;
import graphics.interfaces.IDecorateable;
import vehicles.AdaperOfVehicle;
import vehicles.Car;
import vehicles.Carriage;
import vehicles.Vehicle;
import vehicles.VehicleFactory;

public class CityPanel extends JPanel implements ActionListener, Observer {

	private static final long serialVersionUID = 1L;
	static final int MAX_VEHICLES = 10;
	static final int MAX_VEHICLES_IN_PANEL = 5;
	static final int THREAD_COUNT = 5;
	private BufferedImage BackGroundImg;
	private static final String BACKGROUND_PATH = "graphics/cityBackground.png";
	private static final String[] PANEL_FIELD_NAMES = { "Add Vehicle", "Clear", "Fuel/Food", "Lights", "Info", "Exit" };
	private JButton[] jbuttons;
	static int vehicles_counter;
	static int vehicle_counter_in_panel;
	static ExecutorService pool;
	private Map<Integer, Vehicle> vehiclesDic;
	private Runnable adapter;
	//private ArrayBlockingQueue<Vehicle> queue;
	private QueueManager queueThread;
	static private volatile CityPanel instance = null;
	private static InfoTable infotable = null;
	private ExitOrderly exit;
	
	// Constant variebles - choose from city
	public enum Choose{
		Fuel,Lights
	}


	/**
	 * CityPanel constructor
	 */
	private CityPanel() {

		// 0 vehicles
		vehicles_counter = 0;
		vehicle_counter_in_panel = 0;

		// Initialize Vehicles Dictionary
		vehiclesDic = new ConcurrentHashMap<Integer, Vehicle>();
		AdaperOfVehicle.setDictionary(vehiclesDic);

		// Initialize Vehicles Queue
		//queue = new ArrayBlockingQueue<Vehicle>(MAX_VEHICLES);

		// tread didn't start
		queueThread = null;

		// Load background image
		try {
			BackGroundImg = ImageIO.read(new File(BACKGROUND_PATH));
		} catch (IOException e) {
			System.out.println("Cannot load background image");
		}

		// CityPanel change from default FlowLayout to BorderLayout
		this.setLayout(new BorderLayout());

		// panel_fields will be a panel that contains all the buttons at the bottom of
		// the CityPanel
		JPanel panel_fields = new JPanel(new GridLayout(1, 6));

		// Set panel_fields at the bottom of the CityPanel
		add(panel_fields, BorderLayout.PAGE_END);

		// initialize JButtons, add them to panel_fields panel, add Listeners to them
		jbuttons = new JButton[PANEL_FIELD_NAMES.length];
		for (int i = 0; i < PANEL_FIELD_NAMES.length; ++i) {
			jbuttons[i] = new JButton(PANEL_FIELD_NAMES[i]);
			jbuttons[i].addActionListener(this);
			panel_fields.add(jbuttons[i]);
		}
		
		// Thread pool definition
		pool = Executors.newFixedThreadPool(THREAD_COUNT);
		
		// Create one InfoTable object
		infotable = InfoTable.getInstance(vehiclesDic);
		
		// when exit the system AdapterInstance will change the flag loop in every vehicle
		 exit = ExitOrderly.getInstance(this);
	}

	/**
	 * Get CityPanel object (Thread Safe Singleton)
	 */
	public static CityPanel getInstance() {
		if (instance == null)
			synchronized (CityPanel.class) {
				if (instance == null)
					instance = new CityPanel();
			}
		return instance;
	}

	/**
	 * function set the background city and draw the vehicles
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BackGroundImg, 0, 0, getWidth(), getHeight(), this); // Set background image

		for (Vehicle v : vehiclesDic.values())
			v.drawObject(g);
	}

	/**
	 * Listener to all buttons
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbuttons[0]) // "Add Vehicle"
			Dialog();
		else if (e.getSource() == jbuttons[1]) // "Clear"
			Clear();
		else if (e.getSource() == jbuttons[2]) // "Fuel/Food"
			choose(Choose.Fuel);
		else if (e.getSource() == jbuttons[3]) // "Lights"
			choose(Choose.Lights);
		else if (e.getSource() == jbuttons[4]) // "Info"
			info();
		else if (e.getSource() == jbuttons[5]) // "Exit"
			exit();

	}

	/**
	 * choose dialog vehicle from all vehicles in panel
	 * 
	 * @param isFuel- true if we want to choose a vehicle to refuel, false if we
	 *        want to turn on/off lights
	 */
	private void choose(Choose choose) {
		if (vehicles_counter == 0) {
			JOptionPane.showMessageDialog(null, "Add at least 1 vehicle to the city", "Choose vehicle",
					JOptionPane.WARNING_MESSAGE);
		}

		else {
			ChooseVehicleDialog dia = new ChooseVehicleDialog(vehiclesDic, this, choose);
			dia.setVisible(true);
		}
	}

	/**
	 * If the vehicle exist turn on the lights when its off and vice versa
	 */
	public void Lights(IDecorateable v) {
		synchronized (infotable) {
			if (v == null)
				return;
			if(!v.isOnLights())
				v = new LightsDec(v);
			else
				v.removeDecoration();
		}

	}

	/**
	 * create an array of objects to set the row data in this info Dictionary
	 */
	public void updateTable() {
		infotable.updateTable();
	}

	/**
	 * show all vehicles info in JTable
	 */
	private void info() {
		infotable.ShowTable();
	}

	/**
	 * Chooses add vehicle: if there are more than MAX_VEHICLES vehicle(s) show
	 * warning else, create frame to get all vehicle details
	 */
	private void Dialog() {
		if (vehicles_counter >= MAX_VEHICLES) { // If we added already the max vehicles
			JOptionPane.showMessageDialog(null, "You can't add more\nthan " + MAX_VEHICLES + " vehicle(s)",
					"Add new vehicle", JOptionPane.WARNING_MESSAGE);
			return;
		}

		AddVehicleDialog dialog = new AddVehicleDialog(this);
		dialog.setVisible(true);
	}

	/**
	 * add vehicle to panel
	 * 
	 * Create vehicle object
	 * 
	 * @param vehicleName - type of the vehicle
	 * @param colorName   - color of the vehicle
	 * @param numOfGears  - bike's number of gears
	 */
	public void addVehicle(String vehicleName, String colorName, int numOfGears) {
		// Factory Method
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle v = vehicleFactory.getVehicle(vehicleName, colorName, numOfGears, this);
		
		v.loadImages();
		if ((vehicle_counter_in_panel >= 0) && (vehicle_counter_in_panel < MAX_VEHICLES_IN_PANEL)) {
			++vehicle_counter_in_panel;
		} else { // If 5 <= vehicle_counter <= 10

			JOptionPane.showMessageDialog(null, "The vehicle added to the queue", "Add new vehicle",
					JOptionPane.INFORMATION_MESSAGE);
			
			/*

			if (queueThread == null) { // If the thread didn't start
				queueThread = new QueueManager(this, this.queue);
				queueThread.start();
			}
			queueThread.addToQueue(v);
			*/
		}
		++vehicles_counter;
		addVehicleToCity(v);
	}

	/**
	 * add the vehicle to the panel as a Thread and start the thread
	 * 
	 * @param v
	 */
	public void addVehicleToCity(Vehicle v) {
		AdaperOfVehicle vehicleAdapter = new AdaperOfVehicle(v); // Create Runnable
		
		// Add observers to the vehicles
		// CityPanel for the red button and info Table for update the table
		vehicleAdapter.addObserver(this);
		vehicleAdapter.addObserver(infotable);
		
		// Add all vehicles to the exit object
		exit.addObserver(vehicleAdapter);
		
		adapter = vehicleAdapter;
		v.setAdapter(adapter); // Reference to the Runnable in the vehicle object
		this.vehiclesDic.put(v.getVid(), v);
		pool.execute(adapter);
		this.repaint(); // call paintComponent
	}

	/**
	 * clear the panel from all vehicles
	 */
	public void Clear() {
		updateTable(); // Update the info vehicles Dictionary

		vehicles_counter = 0;
		vehicle_counter_in_panel = 0;
		vehiclesDic.clear();

		// if there are vehicles in queue, remove them
		if (this.queueThread != null)
			synchronized (this.queueThread) {
				this.queueThread.notify();
			}
		repaint();
	}

	/**
	 * Clear the panel from vehicles and exit program
	 */
	private void exit() {
		// This will notify all vehicles to end their work
		exit.Exit();
		
		// End the queue thread
		//if (queueThread != null)
			//queueThread.interrupt();
		System.exit(0);
	}

	/**
	 * fuel car and give food to the carriage's animal
	 */
	public void FuelFood(Vehicle v) {

		// if vehicle doesn't exist
		if (v == null) {
			return;
		}

		String[] names = { "Benzine", "Solar", "Food" };
		int choose = JOptionPane.showOptionDialog(this, "Please choose food", "Fuel for cars/Food for animals",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, names, names[2]);

		try {
			// if chooses Benzene or Solar
			if (choose == 0 || choose == 1) {
				if (!(v instanceof Car))
					throw new Exception();

				boolean isSolar = ((Car) v).isSolarCar();
				if ((choose == 0 && isSolar) || (choose == 1 && !isSolar))
					throw new Exception();
			}
			// if chooses Food
			else if (choose == 2) {
				if (!(v instanceof Carriage))
					throw new Exception();
			}
			// Function in IMovable interface will fuel car and give food to the carriage's
			// animal
			v.getFullEnergy();
			repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error for trying to refuel/feed!", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * remove vehicle from cityPanel
	 * 
	 * @param v - vehicle to remove
	 */
	public void removeVehiclefromCity(Vehicle v) {
		this.vehiclesDic.remove(v.getVid());
		--vehicles_counter;
		--vehicle_counter_in_panel;
	}

	/**
	 * 
	 * @return the thread running the queue
	 */
	public QueueManager getQueueManager() {
		return this.queueThread;
	}

	/**
	 * returns all vehicles dictionary
	 */
	public Map<Integer, Vehicle> getVehiclesDic(){
		return this.vehiclesDic;
	}
	
	/**
	 * Set Dictionary of all vehicles in the panel
	 */
	public void setVehiclesDic(Map<Integer, Vehicle> vehicles) {
		this.vehiclesDic = vehicles;
	}

	
	/**
	 * Change color of fuel button only if any vehicle is out of fuel
	 */
	public void update(Observable o, Object arg) {
		//Change color only if out of fuel
		if((boolean) arg)
			this.jbuttons[2].setBackground(Color.RED);
	}
	
}
