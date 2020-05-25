package graphics;

import java.util.concurrent.ArrayBlockingQueue;
import vehicles.Vehicle;

/**
 * Queue Manager for all the vehicles in the queue
 */
public class QueueManager extends Thread{

	private ArrayBlockingQueue<Vehicle> queue;
	private CityPanel pan;
	private boolean isFirstRemove;

	/**
	 * Constructor
	 * 
	 * @param pan   - CityPanel
	 * @param queue - for all the vehicles in the queue
	 */
	public QueueManager(CityPanel pan, ArrayBlockingQueue<Vehicle> queue) {
		this.queue = queue;
		this.pan = pan;
		this.isFirstRemove = true;
	}

	/**
	 * add a new vehicle to the queue
	 */
	public synchronized void addToQueue(Vehicle v) {
		try {
			this.queue.put(v);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * remove from queue and start the thread
	 */
	private synchronized void removeFromQueue() throws InterruptedException {
		this.pan.addVehicleToCity(this.queue.take());
	}

	@Override
	public void run() {

		while (true) {
			// Double check the condition in the while loop
			while (CityPanel.vehicle_counter_in_panel >= CityPanel.MAX_VEHICLES_IN_PANEL) {
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						return;
					}
				}
			}
			// While u can remove from the queue and start the waiting threads
			while (!this.queue.isEmpty() && CityPanel.vehicle_counter_in_panel < CityPanel.MAX_VEHICLES_IN_PANEL) {

				++CityPanel.vehicle_counter_in_panel;
				if (!isFirstRemove) {
					try {
						System.out.println("romoving from queue waiting 27 second...");
						sleep(27000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						return;
					}
				}
				try {
					removeFromQueue();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
				isFirstRemove = false;
			}

			isFirstRemove = true;
		}
	}

}
