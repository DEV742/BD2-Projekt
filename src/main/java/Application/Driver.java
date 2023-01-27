package Application;

public class Driver {

	private int x;
	private int y;
	private boolean isBusy;
	private User user;

	private int id;

	public void setID(int idn){
		id = idn;
	}

	public int getID(){
		return  id;
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

	public boolean isIsBusy() {
		return this.isBusy;
	}

	public void setIsBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}