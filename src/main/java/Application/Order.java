package Application;

import javax.xml.crypto.Data;

public class Order {

	private int id;
	private String date;
	private User client;
	private int x;
	private int y;
	private double cost;
	private Driver driver;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public User getClient() {
		return this.client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getCost() {
		return this.cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Driver getDriver() {
		return this.driver;
	}

	public void setDriver(Driver driver) {
		DatabaseManager db = new DatabaseManager();
		boolean result = db.addDriverToOrder(getId(), driver.getID());
		this.driver = driver;
	}

	public double calculateCost() {
		// TODO - implement Order.calculateCost
		throw new UnsupportedOperationException();
	}

}