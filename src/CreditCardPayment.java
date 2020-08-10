import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CreditCardPayment extends Payment {
	private long creditCardNumber;
	private String expdate;
	private int cvv;

	public CreditCardPayment(double amount, int creditCardNumber, String expdate, int cvv) {
		super(amount);
		this.creditCardNumber = creditCardNumber;
		this.expdate = expdate;
		this.cvv = cvv;
	}

	public CreditCardPayment() {
		super();
	}

	public long getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getExpdate() {
		return expdate;
	}

	public void setExpdate(String expdate) {
		this.expdate = expdate;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public static long getLong(Scanner scnr, String prompt) {
		System.out.print(prompt);
		// loop while the input would be bad.
		while (!scnr.hasNextLong()) {
			scnr.nextLine(); // <-- clear out the bad input that they already
								// typed
			System.out.println("That is not a valid integer. Please try again.");
			System.out.print(prompt);
			scnr.nextLine();
		}
		long input = scnr.nextLong();
		// scnr.nextLine(); // <-- clear entire line to get ready for next input
		return input;
	}
	// validating the length of credit card number (16 digits) and CVV (3 digits)
	public static long validateCreditAndCVVNumber(Scanner scnr, String prompt, int maxDigits, String validateMsg) {
		long input;
		do {
			input = getLong(scnr, prompt);
			if (input <= 0 || String.valueOf(input).length() != maxDigits) {
				System.out.println(validateMsg);
			}
		} while (input <= 0 || String.valueOf(input).length() != maxDigits);
		return input;
	}
	// validating the expiration date is in the future and in correct format
	public static String validateExpireDate(Scanner scnr, String prompt) {

		String input = "";

		boolean isValid = false;
		do {
			System.out.print(prompt);
			input = scnr.next();
			String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
			if (input.matches(regex)) {
				// validateDateFormat(input);

				try {

					DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
					df.setLenient(false);
					// System.out.println(input);
					df.parse(input);
					Date inputDate = new Date(input);
					Date date = new Date();

					if ((inputDate.compareTo(date)) >= 0) {
						isValid = true;
					} else {
						System.out.println("Entered date is expired. Please try again.");
					}

				} catch (ParseException e) {
					
					isValid = false;
				}

			} else {
				System.out.println("That is not a valid date (mm/dd/yyyy). Please try again.");
			}
		} while (!isValid);

		return input;

	}

	@Override
	public String toString() {
		// including mask to hide credit card number except the last 4 digits
		String ccn = String.valueOf(creditCardNumber);
		String mask = ccn.replaceAll("[^\\d\\+]", "").replaceAll("\\d(?=\\d{4})", "*");

		return "Credit card number = " + mask + "\n" + "Expiration date = " + expdate + "\n" + "CVV = " + cvv + "\n" + "Amount = " + amount;
	}

	@Override
	double pay(double amount) {

		return amount;
	}

}
