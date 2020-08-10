

public abstract class Payment {

	double amount;

	abstract double pay(double amount);

	public Payment() {
		super();
	}

	public Payment(double amount) {
		super();
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
