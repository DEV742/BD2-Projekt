package Application;

import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Map {

	public int mapSize = 5;
	public String[][] map;
	private Driver[] drivers;

	public Driver[] getDrivers() {
		return this.drivers;
	}

	public void setDrivers(Driver[] drivers) {
		this.drivers = drivers;
	}

	public void initMap(int size) {

		DatabaseManager db = new DatabaseManager();
		ArrayList<String> streets = db.getStreets();
		map = new String[size][size];
		int streetId = 0;
		if (streets != null) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					map[i][j] = streets.get(streetId);
				}
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