
public class Check extends Payment {

	private String checkNumber;

	public Check() {
		super();
	}

	public Check(String checkNumber) {
		super();
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
		return "Check [checkNumber=" + checkNumber + ", amount=" + amount + ", paymentType=" + paymentType + "]";
	}
	
	public void CheckDetails() {
		System.out.println("Payment method: Check - " + this.getCheckNumber());
		// setCheckNumber = scnr.next();
		System.out.println("Payment amount: " + amount);

	}

}
