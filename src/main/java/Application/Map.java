package Application;

import java.lang.reflect.Array;
import java.net.Inet4Address;
import java.util.ArrayList;

import static java.lang.Math.*;

public class Map {

	public int mapSize = 5;
	public String[][] map;

	public double costModifier = 5.0;
	private ArrayList<Driver> drivers = new ArrayList<Driver>();

	public ArrayList<Driver> getDrivers() {
		return this.drivers;
	}

	public void setDrivers(ArrayList<Driver> drivers) {
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
					streetId++;
				}
			}
		}
	}
	/**
	 * 
	 * @param order
	 */
	public Driver findClosestDriver(Order order) {

		DatabaseManager db = new DatabaseManager();
		ArrayList<Driver> drv = db.getAvailableDrivers();

		for (Driver driv : drv){
			drivers.add(driv);
		}

		Driver bestDriver = null;
		double bestDist = 999;
		for (int i = 0; i < drivers.size(); i++){
			double dist = 0;
			dist = sqrt(abs((drivers.get(i).getX()-order.getX()))^2 + abs((drivers.get(i).getY()- order.getY()))^2);
			if(bestDist > dist){
				bestDist = dist;
				bestDriver = drivers.get(i);
			}
		}
		return bestDriver;
	}

	public double calculateCost(String street1, String street2) {
		double cost = 0;
		ArrayList<Integer> coords1 = getCoordinates(street1);
		ArrayList<Integer> coords2 = getCoordinates(street2);

		cost = Math.sqrt(abs((coords1.get(0) - coords2.get(0)))^2+abs((coords1.get(1) - coords2.get(1)))^2) * costModifier;
		return cost;
 	}

	public ArrayList<Integer> getCoordinates(String street) {
		ArrayList<Integer> coords = new ArrayList<>();
		for (int i = 0; i < mapSize; i++) {
			for (int j = 0; j < mapSize; j++) {
				if(street.equals(map[i][j])){
					coords.add(j);
					coords.add(i);
					return coords;
				}
			}
		}

		return coords;
	}

}