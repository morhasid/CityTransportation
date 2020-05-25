package graphics;

import java.awt.Graphics;

import vehicles.Point;

/**
 * Contain all the interfaces in this Project
 * 
 * @author Mor
 *
 */
public class interfaces {

	/**
	 * Describes the behavior of all objects that can be drawn
	 * 
	 * @author Mor
	 *
	 */
	public interface IDrawable {

		public void loadImages();

		public void drawObject(Graphics g);

		public String getColor();
	}

	/**
	 * Describes the behavior of all objects that can be moved
	 * 
	 * @author Mor
	 *
	 */
	public interface IMoveable {
		public String getVehicleName();

		public int getSpeed();

		public int getFuelConsumption();

		public boolean move(Point p);

		public boolean canMove();

		public boolean getFullEnergy();
		
		public int getDurability();
	}

	/**
	 * Describes the behavior of all the animal that can move
	 * 
	 * @author Mor
	 *
	 */
	public interface IAnimal extends IMoveable {
		public String getAnimalName();

		public boolean eat();
	}

	/**
	 * functions for all object we can replicate
	 * 
	 * @author Mor
	 *
	 */
	public interface ICloneable {
		public Object clone();
	}

	public interface IDecorateable{
		public boolean isOnLights();
		
		public IDecorateable removeDecoration();
		
		public void drawObject(Graphics g) ;
	}
}