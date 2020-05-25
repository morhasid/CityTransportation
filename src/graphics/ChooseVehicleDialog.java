package graphics;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import graphics.CityPanel.Choose;
import vehicles.Vehicle;

/**
 * Chooses vehicle from CItyPanel
 * 
 *
 */
public class ChooseVehicleDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final String[] BUTTONS_NAMES = { "Ok", "Cencel" };
	private JRadioButton[] vehiclesRadio;
	private ButtonGroup group;
	private JButton[] buttons;
	private JPanel[] inPanels;
	private Map<Integer, Vehicle> vehiclesDic;
	private int vehicleID;
	private CityPanel pan;
	private Choose choose;

	/**
	 * ChooseVehicleDialog Constructor
	 * 
	 * @param vehiclesDic - Vehicle dictionary
	 * @param pan         - CityPanel
	 * @param toFuel      - true if choose to refuel, false if choose to lights
	 *                    operation
	 */
	public ChooseVehicleDialog(Map<Integer, Vehicle> vehiclesDic, CityPanel pan, Choose choose) {
		super(new JFrame(), "Coose vehicle from the city");
		setSize(350, 500);
		setLayout(new BorderLayout());

		this.pan = pan;
		this.vehiclesDic = vehiclesDic;
		this.choose = choose;
		group = new ButtonGroup();
		inPanels = new JPanel[BUTTONS_NAMES.length];
		buttons = new JButton[BUTTONS_NAMES.length];
		vehiclesRadio = new JRadioButton[this.vehiclesDic.values().size()];

		for (int i = 0; i < BUTTONS_NAMES.length; ++i) {
			inPanels[i] = new JPanel();
		}

		inPanels[0].setBorder(BorderFactory.createTitledBorder("Choose one vehicle ID"));
		inPanels[0].setLayout(new GridLayout(vehiclesDic.values().size(), 1));

		int i = 0;
		for (Vehicle v : this.vehiclesDic.values()) {
			if (v != null) {
				boolean isSelect = i == 0 ? true : false;
				vehiclesRadio[i] = new JRadioButton(Integer.toString(v.getVid()), isSelect);
				group.add(vehiclesRadio[i]);
				inPanels[0].add(vehiclesRadio[i]);
				++i;
			}
		}

		for (i = 0; i < BUTTONS_NAMES.length; ++i) {
			buttons[i] = new JButton(BUTTONS_NAMES[i]);
			buttons[i].addActionListener(this);
			inPanels[1].add(buttons[i]);
		}

		add(inPanels[0], BorderLayout.CENTER);
		add(inPanels[1], BorderLayout.SOUTH);
	}

	/**
	 * Action listener for OK and Cancel JButtons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[1]) // Cancel
			setVisible(false);
		else { // OK
			getIDfromRadio();
			setVisible(false);
			Vehicle v = vehiclesDic.get(this.vehicleID);
			if (this.choose == Choose.Fuel)
				this.pan.FuelFood(v);
			else
				this.pan.Lights(v);
		}
	}

	/**
	 * get the vehicle ID from JRadio button
	 */
	private void getIDfromRadio() {
		for (int i = 0; i < vehiclesDic.values().size(); ++i) {
			if (vehiclesRadio[i].isSelected()) {
				this.vehicleID = Integer.parseInt(vehiclesRadio[i].getText());
				break;
			}
		}
	}

}
