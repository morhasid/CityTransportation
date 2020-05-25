package graphics;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

public class AddVehicleDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final String[] COLOR_NAMES = { "Red", "Green", "Silver", "White" };
	private static final String[] VEHICLE_NAMES = { "Benzine Car", "Solar Car", "Bike", "Carriage" };
	private static final String[] TITELS = { "Choose a vehicle type", "Choose a color", "Choose bike gears" };
	private static final String[] BUTTONS_NAMES = { "Ok", "Cencel" };
	private JRadioButton[] colorsRadio, vehiclesRadio;
	private ButtonGroup[] group;
	private JPanel[] inPanels;
	private CityPanel citypanel;
	private JButton[] buttons;
	private String chooseVehicle, chooseColor;
	private JSlider gearsSlide;

	/**
	 * AddVehicleDialog Constructor
	 * 
	 * @param pan - CityPanel - the panel we should add the vehicle to
	 */
	public AddVehicleDialog(CityPanel pan) {
		super(new JFrame(), "Add a vehicle to the city");
		setSize(1000, 350);
		setLayout(new BorderLayout());
		citypanel = pan;

		// Initialize all JButtons, JRadioButtons, Panels, Groups
		group = new ButtonGroup[TITELS.length];
		inPanels = new JPanel[TITELS.length];
		vehiclesRadio = new JRadioButton[VEHICLE_NAMES.length];
		colorsRadio = new JRadioButton[COLOR_NAMES.length];
		buttons = new JButton[BUTTONS_NAMES.length];

		for (int i = 0; i < TITELS.length; ++i) {
			group[i] = new ButtonGroup();
			inPanels[i] = new JPanel();
			inPanels[i].setBorder(BorderFactory.createTitledBorder(TITELS[i]));
			inPanels[i].setLayout(new GridLayout(1, 4));
		}

		for (int i = 0; i < VEHICLE_NAMES.length; ++i) {
			boolean isSelect = i == 0 ? true : false;
			vehiclesRadio[i] = new JRadioButton(VEHICLE_NAMES[i], isSelect);
			vehiclesRadio[i].addActionListener(this);
			colorsRadio[i] = new JRadioButton(COLOR_NAMES[i], isSelect);
			group[0].add(vehiclesRadio[i]);
			group[1].add(colorsRadio[i]);
			inPanels[0].add(vehiclesRadio[i]);
			inPanels[1].add(colorsRadio[i]);
		}

		// JPanel for the 2 buttons OK and Cancel
		JPanel buttons_panel = new JPanel();
		buttons_panel.setLayout(new GridLayout(1, 2));

		for (int i = 0; i < BUTTONS_NAMES.length; ++i) {
			buttons[i] = new JButton(BUTTONS_NAMES[i]);
			buttons[i].addActionListener(this);
			buttons_panel.add(buttons[i]);
		}

		// Slider decelerations
		gearsSlide = new JSlider(0, 10);
		gearsSlide.setMajorTickSpacing(2);
		gearsSlide.setMinorTickSpacing(1);
		gearsSlide.setPaintTicks(true);
		gearsSlide.setPaintLabels(true);
		gearsSlide.setEnabled(false);

		// Add Slide to the panel slide
		inPanels[2].add(gearsSlide);
		// Add panel slide to vehicles panel
		inPanels[0].add(inPanels[2], BorderLayout.SOUTH);

		// Add panels to frame
		add(inPanels[0], BorderLayout.NORTH);
		add(inPanels[1], BorderLayout.CENTER);
		add(buttons_panel, BorderLayout.SOUTH);
	}

	/**
	 * Listener for all this JDialog buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[1]) // Choose Cancel
			setVisible(false);
		else if (e.getSource() == buttons[0]) { // choose OK
			itemStateChanged();
			citypanel.addVehicle(this.chooseVehicle, this.chooseColor, this.gearsSlide.getValue());
			setVisible(false);
		}
		// The gear slide will be enable only if Bike JRadioButton selected
		else if (e.getSource() == vehiclesRadio[2])
			gearsSlide.setEnabled(true);
		else
			gearsSlide.setEnabled(false);
	}

	/**
	 * Function get the values from the user choose get what JRadioButtons are
	 * selected
	 */
	public void itemStateChanged() {
		int i;
		for (i = 0; i < VEHICLE_NAMES.length; ++i)
			if (vehiclesRadio[i].isSelected()) {
				this.chooseVehicle = VEHICLE_NAMES[i];
				break;
			}

		for (i = 0; i < COLOR_NAMES.length; ++i)
			if (colorsRadio[i].isSelected()) {
				this.chooseColor = COLOR_NAMES[i];
				break;
			}
	}
}
