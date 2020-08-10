
public class CashPayment extends Payment {

	private double change;

	private double receivedAmount;

	public CashPayment() {
		super();
	}

	public CashPayment(double amount) {
		super(amount);

	}

	public double getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(double receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	@Override
	double pay(double amount) {
		change = receivedAmount - amount;

		return change;
	}

	@Override
	public String toString() {

		return "Amount: $" + amount + "\n" + "Received Amount: $" + receivedAmount + "\n" + "Change: $" + change;
	}

}
