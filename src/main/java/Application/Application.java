package Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Application {

	private static User user;
	private static boolean isLoggedIn = false;
	private static Map map;
	private Order order;
	private User_Type mode;
	private static DatabaseManager db;

	public User getUser() {
		return user;
	}

	public static void setUser(User us) {
		user = us;
	}

	public User_Type getMode() {
		return this.mode;
	}

	public void setMode(User_Type mode) {
		this.mode = mode;
	}



	public static void printMap(){
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

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		map = new Map();
		db = new DatabaseManager();
		map.initMap(map.mapSize);

		while(true) {
			int choice = 0;

			System.out.println("================ Uber App =============");
			if (!isLoggedIn) {
				System.out.println("1. Login");
			}else{
				System.out.println("1. Show map");
			}
			System.out.println("2. Exit");
			Scanner in = new Scanner(System.in);
			choice = in.nextInt();
			in.nextLine();
			switch (choice) {
				case 1:
					if(!isLoggedIn) {
						//login
						String email;
						String number;
						String role;
						System.out.println("Enter your email: ");
						email = in.nextLine();
						System.out.println(email);
						System.out.println("Enter your phone number: ");
						number = in.nextLine();
						System.out.println(number);

						System.out.println("Specify whether you want to log in as a driver or a client (1 or 0): ");
						role = in.nextLine();

						if (role.equals("0")) {
							boolean result = login(email, number, User_Type.Client);
							if (result) {
								System.out.println("You have succesfully logged in");
							} else {
								System.out.println("There was an error during your login. Please check the login credentials and try again");
							}
						} else {
							//login as a driver
						}
					}else{
						printMap();
					}
					break;
				case 2:
					//exit
					System.exit(0);
					break;
			}
		}
	}


	public static boolean login(String email, String phone, User_Type role) {
		ArrayList<String> ls = db.findPersonByCredentials(phone, email);
		if (ls != null){
			User us = new User(ls.get(0), ls.get(1), ls.get(2), ls.get(3));
			setUser(us);
			isLoggedIn = true;
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param email
	 * @param phone
	 * @param role
	 * @param driversLicense
	 * @param pesel
	 */
	public static boolean login(String email, String phone, User_Type role, String driversLicense, String pesel) {
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