package graphics;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import MementoDP.Caretaker;
import MementoDP.Memento;
import MementoDP.Originator;
import vehicles.Vehicle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;

public class CityFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JMenuBar menubar;
	private static final String[] JMENU_NAMES = { "File", "Help","Save","Load" };
	private static final String[] JMENU_ITEMS = { "Exit", "Help" };
	private static final String[] JMENU_ITEMS_MEMENTO = { "Save state", "Load State" };
	private JMenu[] jmenu;
	private JMenuItem[] jmenuItem;
	private JMenuItem[] jmenuItemsMemento;
	private CityPanel citypanel;
	private Originator originator;
	private Caretaker caretaker;
	private Memento memento;
	
	/**
	 * CityFrame constructor
	 */
	public CityFrame(CityPanel citypanel) {
		super("City");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(806, 658);
		this.setResizable(false);
		this.citypanel = citypanel;

		// Menu Bar
		menubar = new JMenuBar();
		this.setJMenuBar(menubar);

		// initialize JMenu Items
		jmenuItem = new JMenuItem[2];
		for (int i = 0; i < jmenuItem.length; ++i)
			jmenuItem[i] = new JMenuItem(JMENU_ITEMS[i]);

		// initialize JMenu
		jmenu = new JMenu[4];
		for (int i = 0; i < jmenu.length/2; ++i) {
			jmenu[i] = new JMenu(JMENU_NAMES[i]);
			jmenu[i].add(jmenuItem[i]);
			menubar.add(jmenu[i]);
			jmenuItem[i].addActionListener(this); // Add action for JMenu Items
		}
		for(int i=jmenu.length/2; i<jmenu.length; ++i) {
			jmenu[i] = new JMenu(JMENU_NAMES[i]);
			menubar.add(jmenu[i]);
		}
		jmenuItemsMemento = new JMenuItem[JMENU_ITEMS_MEMENTO.length];
		for(int i=0;i<JMENU_ITEMS_MEMENTO.length; ++i) {
			jmenuItemsMemento[i] = new JMenuItem(JMENU_ITEMS_MEMENTO[i]);
			jmenu[i+2].add(jmenuItemsMemento[i]);
			jmenuItemsMemento[i].addActionListener(this);
		}
		
		// initialize
		originator = new Originator();
		caretaker = new Caretaker();
		memento = null;
	}

	/**
	 * Main function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// 0 Vehicles
		Vehicle.initID();
		
		// Create CityPanel
		CityPanel citypanel = CityPanel.getInstance();

		// Create Frame
		JFrame f = new CityFrame(citypanel);

		// Add panel to the Frame
		f.add(citypanel);
		f.setVisible(true);

	}

	/**
	 * Actions for JmenuItems
	 */
	public void actionPerformed(ActionEvent e) {
		// If choose Exit
		if (e.getSource() == this.jmenuItem[0])
			System.exit(0);
		else if (e.getSource() == this.jmenuItem[1]) // If choose Help
			JOptionPane.showMessageDialog(null, "Home Work 2\nGUI", "Message", JOptionPane.INFORMATION_MESSAGE);
		else if(e.getSource() == this.jmenuItemsMemento[0]) { //Choose Save sate
			SaveState();
			JOptionPane.showMessageDialog(null, "Current state saved\nClick Load to restore", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else // If choose Load State
			LoadState();
	}
	
	/**
	 * Load the last saved state in the memento
	 */
	private synchronized void LoadState() {
		if(memento == null) {
			JOptionPane.showMessageDialog(null, "Save state first", "Message", JOptionPane.WARNING_MESSAGE);
			return;
		}
		// get the last memento
		memento = caretaker.getMemento(); 
		originator.setMemento(memento); 
		
		//Set the old vehicle dictionary
		citypanel.setVehiclesDic(originator.getState());
		
		// execute all threads
		CityPanel.pool.shutdown();
		CityPanel.pool = Executors.newFixedThreadPool(CityPanel.THREAD_COUNT);
		for(Vehicle v: citypanel.getVehiclesDic().values())
			CityPanel.pool.execute(v.getAdapter());
		citypanel.repaint();
	}
	
	/**
	 * Save a copy of all vehicles in the memento object
	 */
	private synchronized void SaveState() {
		originator.setState(citypanel.getVehiclesDic());
		memento = originator.createMemento(); 
		 caretaker.addMemento(memento); 
	}

}
