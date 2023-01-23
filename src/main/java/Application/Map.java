package Application;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Map {

	public int mapSize = 10;
	public int[][] map;
	private Driver[] drivers;

	public Driver[] getDrivers() {
		return this.drivers;
	}

	public void setDrivers(Driver[] drivers) {
		this.drivers = drivers;
	}

	public void initMap(int size) {
		map = new int[size][size];
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++) {
				map[i][j] = 0;
			}
		}
	}
	/**
	 * 
	 * @param order
	 */
	public Driver findClosestDriver(Order order) {
		Driver bestDriver = null;
		double bestDist = 999;
		for (int i = 0; i < drivers.length; i++){
			double dist = 0;
			dist = sqrt((drivers[i].getX()-order.getX())^2 + (drivers[i].getY()- order.getY())^2);
			if(bestDist > dist){
				bestDist = dist;
				bestDriver = drivers[i];
			}
		}
		return bestDriver;
	}

}