
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class MidTerm {

	// private static Map<String, Double> items = new TreeMap<>();
	private static List<Integer> selectByNumber = new ArrayList<>();
	private static List<String> orderNames = new ArrayList<>();
	private static List<Double> orderPrices = new ArrayList<>();
	private static List<Integer> orderItemQty = new ArrayList<>();
	private static List<Product> orderProduct = new ArrayList<>();
	private static List<Product> cart = new ArrayList<>();
	private static Map<Integer, Product> mapItems = new HashMap<>();

	private static Scanner scnr = new Scanner(System.in);
	private static Path filePath = Paths.get("products.txt");

	public static void main(String[] args) {
		try {
			String decission = "";

			System.out.println("Welcome to the Grocery store! ");
			// information();
			System.out.println("1-See the list of Products");
			System.out.println("2-Add a Product");
			System.out.println("3-Delete a Product");
			System.out.println("4-Edit a Product");
			System.out.println("5-Exit");

			while (true) {

				System.out.println("Enter menu number: ");
				int command = scnr.nextInt();
				if (command == 5) {

					break;
				} else if (command == 1) {

//					
					// things.sort((Product c1,Product c2)->
					// c1.getName().toLowerCase().compareTo(c2.getName().toLowerCase()));

					printMenu();
					do {

						// fillItemMap();
						printMenu();
						// scnr.nextLine();
						System.out.println("What item would you like to order?");
						Integer itemNumber = scnr.nextInt();
						// System.out.println(itemNumber);
						while (!isItemExists(itemNumber)) {
							System.out.println("Sorry, We don't have those. Please try again. ");
							printMenu();
							System.out.println("What item would you like to order?");

							itemNumber = scnr.nextInt();

						}

						addOrderItem(itemNumber);
						// Double itemPrice= items.get(itemNumber);
						// System.out.println("Adding "+itemNumber+" to cart at $"+itemPrice);
						scnr.nextLine();
						System.out.println("Would you like to order any thing else (y/n)?");
						
						decission = scnr.nextLine();
						if (decission.equalsIgnoreCase("n")) {

							System.out.println("Thank you for your order!");
							System.out.println("Here's what you got.");

							displayOrderItems();
							/*
							 * System.out.print("Average price per item in order was ");
							 * 
							 * System.out.printf("%.2f", findAverage(orderPrices)); System.out.println();
							 * int maxIdx = findMax(orderPrices);
							 * System.out.println("Highest index of cost item : " + maxIdx); int minIdx =
							 * findMin(orderPrices); System.out.println("Lowest index of cost item : " +
							 * minIdx); System.out.println("Most expensive item ordered : " +
							 * orderNames.get(maxIdx)); System.out.println("Least expensive item ordered : "
							 * + orderNames.get(minIdx));
							 */
							// System.exit(0);
						} else if (decission.equalsIgnoreCase("y")) {
							// studentDetails();
						}

						else {
							throw new Exception();

						}

					} while (decission.equalsIgnoreCase("y") || decission.equalsIgnoreCase("n"));
					/*
					 * } catch (InputMismatchException e) { System.out.println("input mismatch"); }
					 */
					// scnr.close();

					// }

				} else if (command == 2) {
					Product Product = getProductFromUser(scnr);
					System.out.println("Adding " + Product);
					appendLineToFile(Product);
				} /*
					 * else if (command == 3) { // deleteProduct(); } else if (command == 4) { //
					 * editProduct(); }
					 */ else {
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
	/*
	 * private static void deleteProduct() {
	 * 
	 * List<Product> things = readFile(); scnr.nextLine();
	 * 
	 * String Product = Validator.getString(scnr, "Enter Product: ");
	 * 
	 * for (int i = 0; i < things.size(); i++) {
	 * 
	 * if (things.get(i).getName().equalsIgnoreCase(Product)) { Product
	 * removeProduct = things.get(i); things.remove(i);
	 * System.out.println("Deleting " + removeProduct);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * // things.stream().filter(i ->i.getName().equals("")); try {
	 * Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING);
	 * Files.newInputStream(filePath, StandardOpenOption.TRUNCATE_EXISTING); for
	 * (Product thing : things) { appendLineToFile(thing); }
	 * 
	 * } catch (IOException e) { System.out.println("Unable to write to file."); } }
	 * 
	 * private static void editProduct() { scnr.nextLine(); String Product =
	 * Validator.getString(scnr, "Enter Product: "); int population =
	 * Validator.getInt(scnr, "Enter population: "); List<Product> things =
	 * readFile(); for (Product thing : things) { if
	 * (thing.getName().equalsIgnoreCase(Product)) { thing.setPrice(population);
	 * System.out.println("Modifying " + thing);
	 * 
	 * } } try { Files.newBufferedWriter(filePath,
	 * StandardOpenOption.TRUNCATE_EXISTING); Files.newInputStream(filePath,
	 * StandardOpenOption.TRUNCATE_EXISTING); for (Product thing : things) {
	 * appendLineToFile(thing); }
	 * 
	 * } catch (IOException e) { System.out.println("Unable to write to file."); } }
	 */

	private static Product getProductFromUser(Scanner scnr) {
		scnr.nextLine();
		// TODO #1 adjust this for your class, not Product
		String Product = Validator.getString(scnr, "Enter Product: ");
		String category = Validator.getString(scnr, "Enter Category: ");
		String descnription = Validator.getString(scnr, "Enter Descnription: ");

		double price = Validator.getDouble(scnr, "Enter price: ");

		return new Product(Product, category, descnription, price);
	}

	/**
	 * Read all the objects from a file and store them in a List.
	 */
	public static List<Product> readFile() {
		try {
			List<String> lines = Files.readAllLines(filePath);

			// TODO #2 Add code here@@@ convert this list of String lines from
			// the file to a list of our objects. (Product, in my case)// TODO #2 Add code
			// here@@@ convert this list of String lines from
			// the file to a list of our objects. (Player, in my case)

			for (String line : lines) {
				// System.out.println(line);
				String[] parts = line.split("~~~");
				// #2 select each part based on index position and convert in to a correct data
				// type
				// System.out.println(Arrays.toString(parts));
				String Product = parts[0];
				String category = parts[1];
				String descnription = parts[2];

				double price = Double.parseDouble(parts[3]);

				cart.add(new Product(Product, category, descnription, price));
				// Map<Integer,Product> items = new HashMap<>();
				// tems.compute();

			}
			return cart;
		} catch (IOException e) {
			System.out.println("Unable to read file.");
			return new ArrayList<>();
		}
	}

	public static void printMenu() {
		int i = 1;
		List<Product> things = readFile();

		for (Product thing : things) {
			// ObjectComparator comparator = new ObjectComparator();
			// Collections.sort(things, comparator);
			// System.out.println(i++ + ". " + thing);

			mapItems.put(i++, thing);

			for (Map.Entry<Integer, Product> entry : mapItems.entrySet()) {
				// System.out.println(entry.getKey()+"\t\t\t\t"+entry.getValue());
				// System.out.printf("%-20s%20.2f%n", entry.getKey(), entry.getValue());
				System.out.println(
						entry.getKey() + " " + entry.getValue().getName() + "  " + entry.getValue().getPrice());
			}

		}

	}

	/**
	 * Add an object to the end of the file.
	 */
	public static void appendLineToFile(Product thing) {
		String line = "TODO";

		// TODO #3 add code here@@@ convert your object to a string like
		// it should be as a line in the file. store it in the `line` variable.
		line = thing.getName() + "~~~" + thing.getCategory() + "~~~" + thing.getDescription() + "~~~"
				+ thing.getPrice();
		line = String.format("%s~~~%s~~~%s~~~%f", thing.getName(), thing.getCategory(), thing.getDescription(),
				thing.getPrice());
		// line=thing.toString
		// Files.write requires a list of lines. Create a list of one line.
		List<String> lines = Collections.singletonList(line);
		try {
			Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("Unable to write to file.");
		}
	}

	private static void addOrderItem(int itemNumber) {
		Product itemProduct = mapItems.get(itemNumber);
		String itemName = mapItems.get(itemNumber).getName();
		Double itemPrice = mapItems.get(itemNumber).getPrice();
		// Integer itemNumber = mapItems.get(ke)

		System.out.println("Adding " + itemName + " to cart at $" + itemPrice);
		selectByNumber.add(itemNumber);
		orderNames.add(itemName);
		orderPrices.add(itemPrice);
		addOrderQty(itemNumber);

	}

	private static void addOrderQty(int itemNumber) {

		// int itemIdx = orderNames.indexOf(itemNumber);
		int itemIdx = itemNumber;
		int count = Collections.frequency(selectByNumber, itemNumber);

		try {
			orderItemQty.get(itemIdx);
			orderItemQty.set(itemIdx, count);
		} catch (IndexOutOfBoundsException e) {

			orderItemQty.add(count);
		}

	}

	private static void displayOrderItems() {
		ArrayList<Integer> distinctOrderNumbers = new ArrayList<>();

		for (int orderItemNumber : selectByNumber) {

			// If this element is not present in distinctOrderNames
			// then add it
			if (!distinctOrderNumbers.contains(orderItemNumber)) {

				distinctOrderNumbers.add(orderItemNumber);
			}
		}

		for (int orderItemNumber : distinctOrderNumbers) {
			Double itemPrice = mapItems.get(orderItemNumber).getPrice();
			int itemIdx = distinctOrderNumbers.get(orderItemNumber);// orderItemNumber;
			int itemQty = orderItemQty.get(itemIdx);
			String itemName = orderNames.get(orderItemNumber);
			System.out.println(orderItemNumber + " " + itemName + " " + "$" + itemPrice + " " + itemQty);
			// String item = "$"+itemPrice;
			// System.out.printf("%-20s%20.2f%s%n",orderItemName,itemPrice,"$");
			// System.out.printf("%-10s%-1s%-10.2f%n%-10d%n", orderItemName, "$",
			// itemPrice,itemQty);
			// System.out.println();
			// System.out.println("ItemQty " + itemQty);
		}
	}

	private static boolean isItemExists(int itemNumber) {
		boolean isExists = false;
		// System.out.println(items.containsKey(itemName));

		isExists = mapItems.containsKey(itemNumber);
		return isExists;
	}

	/*class ObjectComparator implements Comparator<Product> {

		public int compare(Product obj1, Product obj2) {
			return obj1.getName().toLowerCase().compareTo(obj2.getName().toLowerCase());
		}*/

	}
		

