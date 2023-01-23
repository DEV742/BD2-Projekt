package Application;

public class Application {

	private User user;
	private boolean isLoggedIn = false;
	private Map map;
	private Order order;
	private User_Type mode;

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User_Type getMode() {
		return this.mode;
	}

	public void setMode(User_Type mode) {
		this.mode = mode;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO - implement Application.main
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param email
	 * @param phone
	 * @param role
	 * @param driversLicense
	 * @param pesel
	 */
	public boolean login(String email, String phone, User_Type role, String driversLicense, String pesel) {
		// TODO - implement Application.login
		throw new UnsupportedOperationException();
	}

	public void logout() {
		// TODO - implement Application.logout
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param name
	 * @param surname
	 * @param email
	 * @param phone
	 * @param role
	 * @param pesel
	 * @param driversId
	 */
	public void register(String name, String surname, String email, String phone, User_Type role, String pesel, String driversId) {
		// TODO - implement Application.register
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param client
	 */
	public boolean placeOrder(int x, int y, User client) {
		// TODO - implement Application.placeOrder
		throw new UnsupportedOperationException();
	}

}