
public class Cash extends Payment {

	private double amountTendered;
	private double change;

	public Cash() {
		super();
	}

	public Cash(double amountTendered, double change) {
		super();
		this.amountTendered = amountTendered;
		this.change = change;
	}

	public double getAmountTendered() {
		return amountTendered;
	}

	public void setAmountTendered(double amountTendered) {
		this.amountTendered = amountTendered;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	@Override
	public String toString() {
		return "Cash [amountTendered=" + amountTendered + ", change=" + change + ", amount=" + amount + "]";
	}
	
	public void CashDetails() {
		System.out.println("Payment method: Cash - " + this.getAmountTendered());
		// setAmountTendered = scnr.next();
		// take amount tendered minus grand total = change
		System.out.println("Payment amount: " + amount);

	}

}
