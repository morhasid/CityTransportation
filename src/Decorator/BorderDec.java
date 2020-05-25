package Decorator;

import java.awt.Graphics;

import graphics.interfaces.IDecorateable;
import vehicles.Location;
import vehicles.Location.Direction;
import vehicles.Vehicle;

public class BorderDec extends VehicleDecorator{

	public BorderDec(IDecorateable v) {
		super(v);
	}
	
	@Override
	public void drawObject(Graphics g) {
		
		Vehicle vehicle = (Vehicle) v.removeDecoration();
		Location loc = vehicle.getLocation();
		int size = vehicle.getSize();
		
		if (loc.getOrientation() == Direction.NORTH) // drives to right side
			g.drawRect(loc.getPoint().getX()-size/2, loc.getPoint().getY(), size, size * 2);
		else if (loc.getOrientation() == Direction.SOUTH)// drives to the south side
			g.drawRect( loc.getPoint().getX(), loc.getPoint().getY(), size, size * 2);
		else if (loc.getOrientation() == Direction.EAST) { // drives to the east side
			g.drawRect( loc.getPoint().getX(), loc.getPoint().getY(), size * 2, size);
		} else if (loc.getOrientation() == Direction.WEST) // drives to the west side
			g.drawRect( loc.getPoint().getX(), loc.getPoint().getY(), size * 2, size);
		
	}
	

}
