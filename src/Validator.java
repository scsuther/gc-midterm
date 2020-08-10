import java.util.Scanner;

public class Validator

{

	public static String getString(Scanner scnr, String prompt) {
		System.out.print(prompt);
		return scnr.nextLine();
	}

	public static int getInt(Scanner scnr, String prompt) {
		System.out.print(prompt);
		// loop while the input would be bad.
		while (!scnr.hasNextInt()) {
			scnr.nextLine(); // <-- clear out the bad input that they already
								// typed
			System.out.println("That is not a valid integer. Please try again.");
			System.out.print(prompt);
		}
		int input = scnr.nextInt();
		scnr.nextLine(); // <-- clear entire line to ready for next input
		return input;
	}

	public static int getPositiveInt(Scanner scnr, String prompt) {
		int input;
		do {
			input = getInt(scnr, prompt);
			if (input <= 0) {
				System.out.println("You must enter a positive number.");
			}
		} while (input <= 0);
		return input;
	}

	public static double getDouble(Scanner scnr, String prompt) {
		System.out.print(prompt);
		// loop while the input would be bad.
		while (!scnr.hasNextDouble()) {
			scnr.nextLine(); // <-- clear out the bad input that they already
								// typed
			System.out.println("That is not a valid number. Please try again.");
			System.out.print(prompt);
		}
		double input = scnr.nextDouble();
		scnr.nextLine(); // <-- clear entire line to ready for next input
		return input;
	}

	// valid: YES, yes, y, Y / No, no, N, n
	public static boolean getYesNo(Scanner scnr, String prompt) {
		String input;
		boolean isValid;
		do {
			System.out.println(prompt);
			input = scnr.nextLine();
			isValid = "yes".equalsIgnoreCase(input) || "no".equalsIgnoreCase(input) || "y".equalsIgnoreCase(input)
					|| "n".equalsIgnoreCase(input);
			if (!isValid) {
				System.out.println("Invalid response. Enter yes or no.");
			}
		} while (!isValid);

		return input.toLowerCase().startsWith("y");
	}

}
