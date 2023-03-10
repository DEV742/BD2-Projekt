package Application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;


public class Application {

	private  User user;
	private boolean isLoggedIn = false;

	public Map getMap() {
		return map;
	}

	private  Map map;
	private Order order;
	private User_Type mode;
	DatabaseManager db;

	private Driver driver;
	private static Application app;

	private JFrame frame;

	int registeredDriverID;

	public User getUser() {
		return user;
	}

	public void setUser(User us) {
		user = us;
	}

	public User_Type getMode() {
		return this.mode;
	}

	public void setMode(User_Type mode) {
		this.mode = mode;
	}



	public void printMap(){
		System.out.print("   ");
		for (int i = 0; i < map.mapSize; i++){
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < map.mapSize; i++){
			System.out.print(i + "  ");
			for (int j = 0; j < map.mapSize; j++){
				System.out.print(map.map[i][j] + "|");
			}
			System.out.println();
		}
	}

	public void init() {
		map = new Map();
		map.initMap(map.mapSize);
		LoginWindow lw = new LoginWindow();
		lw.setApp(this);
		lw.init();
	}

	public void showMainFrame() {
		MainFrame mf = new MainFrame();
		mf.init();
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		app = new Application();
		app.init();

	}


	public boolean login(String email, String phone, User_Type role) {
		db = new DatabaseManager();
		System.out.println("Email: " + email);
		System.out.println("Phone: " + phone);
		ArrayList<String> ls = this.db.findPersonByCredentials(phone, email);
		if (ls != null){
			User us = new User(ls.get(0), ls.get(1), ls.get(2), ls.get(3));
			setUser(us);
			isLoggedIn = true;
			mode = User_Type.Client;
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param email
	 * @param phone
	 * @param driversLicense
	 * @param pesel
	 */
	public boolean login(String email, String phone, String driversLicense, String pesel) {
		db = new DatabaseManager();
		System.out.println("Email: " + email);
		System.out.println("Phone: " + phone);
		ArrayList<String> ls = this.db.loginDriver(email, phone, driversLicense, pesel);
		if (ls != null){
			User us = new User(ls.get(0), ls.get(1), ls.get(2), ls.get(3));
			setUser(us);
			isLoggedIn = true;
			mode = User_Type.Driver;
			return true;
		}
		return false;
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
	public boolean register(String name, String surname, String email, String phone, User_Type role, String pesel, String driversId) {
		db = new DatabaseManager();
		if(role.equals(User_Type.Client)) {
			boolean result = db.registerNewClient(name, surname, email, phone);
			if(result){
				//registration success dialog
				showMessageDialog(null, "Konto zosta??o pomy??lnie stworzone", "Sukces", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}else{
				//registration failure dialog
				showMessageDialog(null, "Wyst??pi?? b????d podczas za??o??enia nowego konta", "Nieudana rejestracja", JOptionPane.ERROR_MESSAGE);

			}
		}else{
			int result = db.registerNewDriver(name, surname, email, phone, pesel, driversId);
			if(result >= 0){
				registeredDriverID = result;
				//registration success dialog
				showMessageDialog(null, "Konto zosta??o pomy??lnie stworzone", "Sukces", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}else{
				//registration failure dialog
				showMessageDialog(null, "Wyst??pi?? b????d podczas za??o??enia nowego konta", "Nieudana rejestracja", JOptionPane.ERROR_MESSAGE);

			}
		}
		return false;
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