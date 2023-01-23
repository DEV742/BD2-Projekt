package Application;

public class Payment {

	private int id;
	private double quota;
	private Payment_Method attribute;

	public double getQuota() {
		return this.quota;
	}

	public void setQuota(double quota) {
		this.quota = quota;
	}

	public Payment_Method getAttribute() {
		return this.attribute;
	}

	public void setAttribute(Payment_Method attribute) {
		this.attribute = attribute;
	}

}