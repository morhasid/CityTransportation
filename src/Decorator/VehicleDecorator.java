package Decorator;

import java.awt.Graphics;

import graphics.interfaces.IDecorateable;

public abstract class VehicleDecorator implements IDecorateable{
	
	protected IDecorateable v;
	
	public VehicleDecorator(IDecorateable v) {
		this.v = v;
	}
	
	public boolean isOnLights() {
		return v.isOnLights();
	}
	
	public IDecorateable removeDecoration() {
		return this.v.removeDecoration();
	}
	
	public void drawObject(Graphics g) {
		v.drawObject(g);
	}

}
