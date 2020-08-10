
public class Credit extends Payment {

	private String cardNumber;
	private String date;
	private String cvv;

	public Credit() {
		super();
	}

	public Credit(String cardNumber, String date, String cvv) {
		super();
		this.cardNumber = cardNumber;
		this.date = date;
		this.cvv = cvv;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	@Override
	public String toString() {
		return "Credit [cardNumber=" + cardNumber + ", date=" + date + ", cvv=" + cvv + ", amount=" + amount + "]";
	}

	public void CreditDetails() {
		System.out.println("Payment method: Credit - " + this.getCardNumber());
		// setCardNumber = scnr.next();
		System.out.println("Expiration date: " + this.getDate());
		// setDate = scnr.next();
		System.out.println("CVV: " + this.getCvv());
		// set cvv = scnr.ext();
		System.out.println("Payment amount: " + amount);

	}

}
