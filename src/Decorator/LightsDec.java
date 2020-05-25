package Decorator;

import graphics.interfaces.IDecorateable;

public class LightsDec extends VehicleDecorator{

	public LightsDec(IDecorateable v) {
		super(v);
	}
	
	public boolean isOnLights() {
		return !v.isOnLights();
	}

}
