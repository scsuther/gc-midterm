
public class Cash extends Payment {

	private double change;

	private double recivedAmount;

	public Cash() {
		super();
	}

	public Cash(double amount) {
		super(amount);

	}

	public double getRecivedAmount() {
		return recivedAmount;
	}

	public void setRecivedAmount(double recivedAmount) {
		this.recivedAmount = recivedAmount;
	}

	@Override
	double pay(double amount) {
		change = recivedAmount - amount;

		return change;
	}

	@Override
	public String toString() {

		return "Amount : " + amount + "\n" + "Recived Amount : " + recivedAmount + "\n" + "Change : " + change;
	}

}
