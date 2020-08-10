
public class CheckPayment extends Payment {
	private String checkNumber;

	public CheckPayment() {
		super();
	}

	public CheckPayment(double amount, String checkNumber) {
		super(amount);
		this.checkNumber = checkNumber;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	@Override
	public String toString() {
		return "Check Number = " + checkNumber + "\n" + "Amount = $" + amount;
	}

	@Override
	double pay(double amount) {

		return amount;
	}

}
