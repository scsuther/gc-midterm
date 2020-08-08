
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class MidTerm {

	private static List<Order> orderedProduct = new ArrayList<>();
	private static List<Product> productList = new ArrayList<>();

	private static Scanner scnr = new Scanner(System.in);
	private static Path filePath = Paths.get("product.txt");
	// private static Order order = null;

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

				System.out.println("Enter menu number: ");
				command = scnr.nextInt();
				if (command == 3) {

					break;
				} else if (command == 1) {

					printMenu();
					do {

						Integer itemNumber = Validator.getPositiveInt(scnr, "What item would you like to order?");

						while (!isItemExists(itemNumber)) {
							System.out.println(isItemExists(itemNumber));
							System.out.println(!isItemExists(itemNumber));
							System.out.println("Sorry, We don't have those. Please try again. ");
							printMenu();
							itemNumber = Validator.getPositiveInt(scnr, "What item would you like to order?");

						}
						Integer enterQuantity = Validator.getPositiveInt(scnr, "Enter quantity");

						addOrderItem(itemNumber, enterQuantity);

						valid = Validator.getYesNo(scnr, "Would you like to order any thing else (y/n)?");

						if (!valid) {
							System.out.println("Thank you for your order!");
							System.out.println("Here's what you got.");
							

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
	public static List<Product> readFile() {
		try {
			List<String> lines = Files.readAllLines(filePath);

			for (String line : lines) {

				String[] parts = line.split("~~~");

				int id = Integer.parseInt(parts[0]);
				String name = parts[1];
				String category = parts[2];
				String descnription = parts[3];

				double price = Double.parseDouble(parts[4]);

				productList.add(new Product(id, name, category, descnription, price));

			}
			return productList;
		} catch (IOException e) {
			System.out.println("Unable to read file.");
			return new ArrayList<>();
		}
	}

	public static void printMenu() {
		int i = 1;

		System.out.printf("%-10s%-10s%-10s%-25s%-10s%n", "Id", "Name", "Category", "Description", "Price");
		System.out.printf("%-10s%-10s%-10s%-25s%-10s%n", "==", "====", "=======", "=========", "=====");

		for (Product thing : productList) {
			// ObjectComparator comparator = new ObjectComparator();
			// Collections.sort(things, comparator);
			// System.out.println(i++ + ". " + thing);

			System.out.printf("%-10d%-10s%-10s%-25s%-1s%-10.2f%n", thing.getId(), thing.getName(), thing.getCategory(),
					thing.getDescription(), "$", thing.getPrice());

		}

	}

	/**
	 * Add an object to the end of the file.
	 */
	public static void appendLineToFile(Product thing) {
		String line = "TODO";

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

		int itemIndexNumber = itemNumber - 1;

		for (int i = 0; i < orderedProduct.size(); i++) {

			if (orderedProduct.get(i).getId() == itemNumber) {
				isItemExistsInproductList = true;
				int quantity = orderedProduct.get(i).getQuantity();
				// double price = orderedProduct.get(i).getPrice();
				// System.out.println("quantity" + quantity);
				// quantity++;
				double price = productList.get(itemIndexNumber).getPrice();
				quantity = quantity + enterQuantity;

				orderedProduct.get(i).setQuantity(quantity);
				orderedProduct.get(i).setPrice(price * quantity);

			}
		}

		if (!isItemExistsInproductList) {

			Order order = new Order(productList.get(itemIndexNumber).getId(),
					productList.get(itemIndexNumber).getName(), productList.get(itemIndexNumber).getCategory(),
					productList.get(itemIndexNumber).getDescription(),
					productList.get(itemIndexNumber).getPrice() * enterQuantity, enterQuantity);
			orderedProduct.add(order);
		}

		System.out.println("Adding " + productList.get(itemIndexNumber).getName() + " to productList at $"
				+ productList.get(itemIndexNumber).getPrice());

	}

	public static void displayOrderItems(int itemNumber) {
		double subtotal = 0;
		double tax = 6;
		double taxtotal = 0;
		double grandtotal = 0;
		System.out.println("Your Order productList Items: ");
		System.out.println();
		System.out.printf("%-10s%-10s%-10s%-10s%n", "Id", "Name", "Quantity", "Price");
		System.out.printf("%-10s%-10s%-10s%-10s%n", "==", "====", "========", "=====");
		// System.out.println("==========================");

		for (Order order : orderedProduct) {

			System.out.printf("%-10d%-10s%-10d%-1s%-20.2f%n", order.getId(), order.getName(), order.getQuantity(), "$",
					order.getPrice());
			subtotal = subtotal + order.getPrice();
		}
		taxtotal = (subtotal * tax) / 100;
		grandtotal = subtotal + taxtotal;
		System.out.println();
		System.out.println("=================================");
		System.out.print("subtotal: ");
		System.out.printf("%.2f%n", subtotal);
		System.out.print("%tax at " + tax + "% ");
		System.out.printf("%.2f%n", taxtotal);
		System.out.print("grandtotal: ");
		System.out.printf("%.2f%n", grandtotal);

	}

	private static boolean isItemExists(int itemNumber) {
		boolean isExists = false;

		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).getId() == itemNumber) {
				isExists = true;
			}

		}
		return isExists;
	}

	/*
	 * class ObjectComparator implements Comparator<Product> {
	 * 
	 * public int compare(Product obj1, Product obj2) { return
	 * obj1.getName().toLowerCase().compareTo(obj2.getName().toLowerCase()); }
	 */

}
