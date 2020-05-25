package vehicles;

import graphics.CityPanel;

/**
 * Factory Method 
 * creates and returns a vehicle according to parameters
 *
 */
public class VehicleFactory {
	
	public Vehicle getVehicle(String vehicleName, String colorName, int numOfGears,CityPanel cityPanel) {
		Vehicle v = null;
		Vehicle.Color col = Vehicle.nameToColor(colorName);
		
		if(vehicleName.equals("Bike"))
			v= new Bike(col, cityPanel, numOfGears);
		
		else if(vehicleName.equals("Carriage")) {
			v = new Carriage(col, cityPanel);
			PackAnimal animal = new PackAnimal();
			animal.setCarriage((Carriage) v);
			((Carriage) v).setAnimal(animal);
		}
		else if(vehicleName.equals("Solar Car"))
			v= new Car(col, cityPanel, new SolarEngine(Car.CAR_MAX_FULE));
		else
			v= new Car(col, cityPanel, new BenzineEngine(Car.CAR_MAX_FULE));
		
		return v;
	}
}
