package Application;

public class User {

	private String name;
	private String surname;
	private String email;
	private String phone;
	private User_Type role;
	private String pesel;
	private String driversLicense;

	public User(String name, String surname, String phone, String email) {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User_Type getRole() {
		return this.role;
	}

	public void setRole(User_Type role) {
		this.role = role;
	}

	public String getPesel() {
		return this.pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public String getDriversLicense() {
		return this.driversLicense;
	}

	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}

}