
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MidTerm {

	private static List<Order> orderedProduct = new ArrayList<>();
	private static Map<Integer, String> payType = new TreeMap<>();
	private static Scanner scnr = new Scanner(System.in);
	private static Path filePath = Paths.get("product.txt");
	private static Map<Integer, Product> productList = new TreeMap<>();

	public static void main(String[] args) {
		boolean valid = false;

		int command = 0;

		try {

			System.out.println("Welcome to the Grocery store! ");

			System.out.println("1-See the list of Products");
			System.out.println("2-Add a Product");
			System.out.println("3-Exit");
			readFile();

			while (true) {

//				System.out.println("Enter menu number: ");
//				command = scnr.nextInt();
				command = Validator.getPositiveInt(scnr, "Enter menu number: ");
				if (command == 3) {

					break;
				} else if (command == 1) {

					printMenu();

					do {

						Integer itemNumber = Validator.getPositiveInt(scnr, "What item would you like to order? (Enter item ID)");

						while (!isItemExists(itemNumber)) {
							// System.out.println(isItemExists(itemNumber));
							// System.out.println(!isItemExists(itemNumber));
							System.out.println("Sorry, We don't have those. Please try again. ");
							printMenu();

							itemNumber = Validator.getPositiveInt(scnr, "What item would you like to order? (Enter item ID)");

						}
						Integer enterQuantity = Validator.getPositiveInt(scnr, "Enter quantity: ");

						addOrderItem(itemNumber, enterQuantity);

						valid = Validator.getYesNo(scnr, "Would you like to order any thing else (y/n)?");

						if (!valid) {
							System.out.println("Thank you for your order!");
							System.out.println("Here's whats in your cart:");

							double amount = displayOrderItems(itemNumber);
							boolean decission = Validator.getYesNo(scnr, "Are you ready to pay the bill? (y/n) ");
							if (decission) {

								payType();
								System.out.println("How would you like to pay? ");
								int payId = scnr.nextInt();
								if (payId == 1) {
									cash(amount);

								} else if (payId == 2) {
									credit(amount);

								} else if (payId == 3) {
									check(amount);

								}
							}
						}

					} while (valid);

				} else if (command == 2) {
					Product Product = getProductFromUser(scnr);
					System.out.println("Adding " + Product);
					// appendLineToFile(Product);
				} else {
					System.out.println("Invalid command.");
				}
			}
			System.out.println("Goodbye.");
			scnr.close();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);

		}

	}

	private static Product getProductFromUser(Scanner scnr) {
		scnr.nextLine();

		// int id = Validator.getInt(scnr, "Enter id: ");
		int id = productList.size() + 1;
		String name = Validator.getString(scnr, "Enter Product: ");
		String category = Validator.getString(scnr, "Enter Category: ");
		String description = Validator.getString(scnr, "Enter Description: ");

		double price = Validator.getDouble(scnr, "Enter price: ");

		return new Product(id, name, category, description, price);
	}

	/**
	 * Read all the objects from a file and store them in a List.
	 */
	public static Map<Integer, Product> readFile() {
		try {
			List<String> lines = Files.readAllLines(filePath);
			int i = 1;
			for (String line : lines) {

				String[] parts = line.split("~~~");

				int id = Integer.parseInt(parts[0]);
				String name = parts[1];
				String category = parts[2];
				String descnription = parts[3];

				double price = Double.parseDouble(parts[4]);

				productList.put(i++, new Product(id, name, category, descnription, price));

			}
			return productList;
		} catch (IOException e) {
			System.out.println("Unable to read file.");
			return new TreeMap<>();
		}
	}

	public static void printMenu() {

		System.out.printf("%-10s%-10s%-10s%-25s%-10s%n", "Id", "Name", "Category", "Description", "Price");
		System.out.printf("%-10s%-10s%-10s%-25s%-10s%n", "==", "====", "=======", "=========", "=====");

		for (Map.Entry<Integer, Product> entry : productList.entrySet()) {

			System.out.printf("%-10d%-10s%-10s%-25s%-1s%-10.2f%n", entry.getKey(), entry.getValue().getName(),
					entry.getValue().getCategory(), entry.getValue().getDescription(), "$",
					entry.getValue().getPrice());

		}

	}

	/**
	 * Add an object to the end of the file.
	 */
	public static void appendLineToFile(Product thing) {
		String line = "";

		line = thing.getId() + "~~~" + thing.getName() + "~~~" + thing.getCategory() + "~~~" + thing.getDescription()
				+ "~~~" + thing.getPrice();
		line = String.format("%d~~~%s~~~%s~~~%s~~~%f", thing.getId(), thing.getName(), thing.getCategory(),
				thing.getDescription(), thing.getPrice());

		List<String> lines = Collections.singletonList(line);
		try {
			Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("Unable to write to file.");
		}
	}

	private static void addOrderItem(int itemNumber, int enterQuantity) {

		boolean isItemExistsInproductList = false;

		for (int i = 0; i < orderedProduct.size(); i++) {

			if (orderedProduct.get(i).getId() == itemNumber) {
				isItemExistsInproductList = true;
				int quantity = orderedProduct.get(i).getQuantity();

				double price = productList.get(itemNumber).getPrice();
				quantity = quantity + enterQuantity;

				orderedProduct.get(i).setQuantity(quantity);
				orderedProduct.get(i).setPrice(price * quantity);

			}
		}

		if (!isItemExistsInproductList) {

			Order order = new Order(productList.get(itemNumber).getId(), productList.get(itemNumber).getName(),
					productList.get(itemNumber).getCategory(), productList.get(itemNumber).getDescription(),
					productList.get(itemNumber).getPrice() * enterQuantity, enterQuantity);
			orderedProduct.add(order);
		}

		System.out.println("Adding " + productList.get(itemNumber).getName() + " to order at $"
				+ productList.get(itemNumber).getPrice());

	}

	public static double displayOrderItems(int itemNumber) {
		double subtotal = 0;
		double tax = 6;
		double taxtotal = 0;
		double grandtotal = 0;
		System.out.printf("%-10s%-10s%-10s%-10s%n", "Id", "Name", "Quantity", "Price");
		System.out.printf("%-10s%-10s%-10s%-10s%n", "==", "====", "========", "=====");
		

		for (Order order : orderedProduct) {

			System.out.printf("%-10d%-10s%-10d%-1s%-20.2f%n", order.getId(), order.getName(), order.getQuantity(), "$",
					order.getPrice());
			subtotal = subtotal + order.getPrice();
		}
		taxtotal = (subtotal * tax) / 100;

		
		grandtotal = subtotal + taxtotal;
		grandtotal = mathRound(grandtotal);
		System.out.println();
		System.out.println("Subtotal: " + subtotal);
		System.out.print("%tax at " + tax + "% : ");
		System.out.printf("%.2f%n", taxtotal);
		System.out.println("Grand total: " + grandtotal);
		return grandtotal;
	}

	private static double mathRound(double value) {
		double roundValue = Math.round((value * 100.0) / 100);
		return roundValue;
	}

	private static boolean isItemExists(int itemNumber) {
		boolean isExists = false;

		isExists = productList.containsKey(itemNumber);

		return isExists;
	}

	public static void payType() {
		payType.put(1, "Cash");
		payType.put(2, "Credit");
		payType.put(3, "Check");
		System.out.printf("%-20s%-20s%n", "paymentId", "paymentType");
		System.out.printf("%-20s%-20s%n", "=========", "===========");
		for (Map.Entry<Integer, String> entry : payType.entrySet()) {

			System.out.printf("%-20d%-20s%n", entry.getKey(), entry.getValue());
		}
	}

	public static void credit(double amount) {
		Credit credit = new Credit();
		credit.setAmount(amount);

		long creditNum = credit.validateCreditAndCVVNumber(scnr, "Enter credit card number : ", 16,
				"You must enter a valid 16 digit credit card number.");

		credit.setCreditCardNumber(creditNum);

		int cvv = (int) (credit.validateCreditAndCVVNumber(scnr, "Enter credit CVV :", 3,
				"You must enter a valid 3 digit CVV number."));

		credit.setCvv(cvv);

		String expdate = credit.validateExpiryDate(scnr, "Enter credit expire date (mm/dd/yyyy) :");

		credit.setExpdate(expdate);

		credit.pay(amount);
		System.out.println(credit.toString());
	}

	public static void cash(double amount) {
		Cash cash = new Cash();
		cash.setAmount(amount);

		double money = Validator.getDouble(scnr, "Amount due: " + amount + " ");

		while (money < amount) {
			System.out.println("Money is not sufficient, please pay " + amount);
			System.out.print("Amount due: " + amount + ": ");
			money = Validator.getDouble(scnr, "Amount due: " + amount + " ");
		}

		cash.setRecivedAmount(money);
		double change = cash.pay(amount);
		change = mathRound(change);
		System.out.println("Please take your change " + change);
		System.out.println(cash.toString());

	}

	public static void check(double amount) {
		System.out.print("Please enter check number : ");

		String checkNum = scnr.next();

		Check check = new Check();
		check.setAmount(amount);

		check.setCheckNumber(checkNum);
		System.out.println(check.toString());
	}

}
