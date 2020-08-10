import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

			System.out.println("Welcome to the grocery store! ");

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
					// listOfProducts();
					do {

						Integer itemNumber = Validator.getPositiveInt(scnr,
								"What item would you like to order? (enter item ID) ");

						while (!isItemExists(itemNumber)) {
							System.out.println(isItemExists(itemNumber));
							System.out.println(!isItemExists(itemNumber));
							System.out.println("Sorry, We don't have those. Please try again. ");
							printMenu();
							// listOfProducts();
							itemNumber = Validator.getPositiveInt(scnr,
									"What item would you like to order? (enter item ID) ");

						}
						Integer enterQuantity = Validator.getPositiveInt(scnr, "Enter quantity: ");

						addOrderItem(itemNumber, enterQuantity);

						valid = Validator.getYesNo(scnr, "Would you like to order any thing else (y/n)? ");

						if (!valid) {
							System.out.println("Thank you for your order!");

							displayOrderItems(itemNumber);

						}

					} while (valid);

				} else if (command == 2) {
					Product Product = getProductFromUser(scnr);
					System.out.println("Adding " + Product);
					appendLineToFile(Product);
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

		int id = Validator.getInt(scnr, "Enter id: ");
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
		String line;

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

		System.out.println("Adding " + productList.get(itemNumber).getName() + " to your cart at $ "
				+ productList.get(itemNumber).getPrice() + " each.");

	}

	public static void displayOrderItems(int itemNumber) {
		double subtotal = 0;
		double tax = 6;
		double taxtotal = 0;
		double grandtotal = 0;

		System.out.println("Your current order is: ");
		System.out.println();
		System.out.printf("%-10s%-10s%-10s%-10s%n", "Id", "Name", "Price", "Quantity");
		System.out.printf("%-10s%-10s%-10s%-10s%n", "==", "====", "=====", "========");
		// System.out.println("==========================");

		for (Order order : orderedProduct) {

			System.out.printf("%-10d%-10s%-1s%-20.2f%-10d%n", order.getId(), order.getName(), "$", order.getPrice(),
					order.getQuantity());
			subtotal = subtotal + order.getPrice();
		}
		taxtotal = (subtotal * tax) / 100;
		grandtotal = subtotal + taxtotal;
		System.out.println();
		// System.out.println("subtotal: " + subtotal);
		System.out.print("Subtotal: $");
		System.out.printf("%.2f%n", subtotal);
		System.out.print("Tax at " + tax + "%: ");
		System.out.printf("%.2f%n", taxtotal);
		System.out.print("Grand Total: $");
		System.out.printf("%.2f%n", grandtotal);
		System.out.println();
		System.out.println("How would you like to pay? (cash, check, or credit): ");
		// paymentType = scnr.next();

	}

	private static boolean isItemExists(int itemNumber) {
		boolean isExists = false;

		isExists = productList.containsKey(itemNumber);

		return isExists;
	}

}
