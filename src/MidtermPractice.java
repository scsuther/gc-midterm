
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.*;

public class MidtermPractice {
	private static Scanner scr;
	private static Map<String, Double> items = new TreeMap<>();
	private static List<String> orderNames = new ArrayList<>();
	private static List<Double> orderPrices = new ArrayList<>();
	private static List<Integer> orderItemQty = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("Welcome to the GC Market!");
		Scanner scr = new Scanner(System.in);
		String decission = "";
		try {

			do {

				// fillItemMap();
				readFile();
				// scr.nextLine();
				System.out.println("What item would you like to order?");
				String itemName = scr.nextLine().toLowerCase();
				// System.out.println(itemName);
				while (!isItemExists(itemName)) {
					System.out.println("Sorry, We don't have those. Please try again. ");
					readFile();
					System.out.println("What item would you like to order?");

					itemName = scr.nextLine().toLowerCase();

				}

				addOrderItem(itemName);
				// Double itemPrice= items.get(itemName);
				// System.out.println("Adding "+itemName+" to cart at $"+itemPrice);
				System.out.println("Would you like to order any thing else (y/n)?");
				decission = scr.nextLine();
				if (decission.equalsIgnoreCase("n")) {

					System.out.println("Thank you for your order!");
					System.out.println("Here's what you got.");

					displayOrderItems();
					// System.out.print("Average price per item in order was ");

					// System.out.printf("%.2f", findAverage(orderPrices));
					System.out.println();
					// int maxIdx = findMax(orderPrices);
					// System.out.println("Highest index of cost item : " + maxIdx);
					// int minIdx = findMin(orderPrices);
					// System.out.println("Lowest index of cost item : " + minIdx);
					// System.out.println("Most expensive item ordered : " +
					// orderNames.get(maxIdx));
					// System.out.println("Least expensive item ordered : " +
					// orderNames.get(minIdx));

					System.exit(0);
				} else if (decission.equalsIgnoreCase("y")) {
					// studentDetails();
				}

				else {
					throw new Exception();

				}

			} while (decission.equalsIgnoreCase("y") || decission.equalsIgnoreCase("n"));
		} catch (InputMismatchException e) {
			System.out.println("input mismatch");
		} catch (Exception e) {
			System.out.println("invalid input");
			System.exit(0);

		}

		// scr.close();

	}

	// private static void //fillItemMap() {
	// TODO Auto-generated method stub

	// }

	private static void addOrderItem(String itemName) {
		Double itemPrice = items.get(itemName);
		System.out.println("Adding " + itemName + " to cart at $" + itemPrice);
		orderNames.add(itemName);
		orderPrices.add(itemPrice);
		addOrderQty(itemName);

	}

	private static void addOrderQty(String itemName) {

		/*
		 * for(String orderItemName:orderNames) {
		 * 
		 * Double itemPrice= items.get(orderItemName);
		 * System.out.printf("%-20s%20.2f%s%n",orderItemName,itemPrice,"$"); }
		 */
		int itemIdx = orderNames.indexOf(itemName);
		int count = Collections.frequency(orderNames, itemName);

		// System.out.println(itemIdx);
		// System.out.println(count);
		// System.out.println(orderItemQty);

		try {
			orderItemQty.get(itemIdx);
			orderItemQty.set(itemIdx, count);
		} catch (IndexOutOfBoundsException e) {
			// orderItemQty.add(itemIdx, count);
			orderItemQty.add(count);
		}

		// System.out.println(count);
		// System.out.println(orderItemQty);

	}

	private static void displayOrderItems() {
		ArrayList<String> distinctOrderNames = new ArrayList<String>();

		for (String orderItemName : orderNames) {

			// If this element is not present in distinctOrderNames
			// then add it
			if (!distinctOrderNames.contains(orderItemName)) {

				distinctOrderNames.add(orderItemName);
			}
		}

		for (String orderItemName : distinctOrderNames) {
			Double itemPrice = items.get(orderItemName);
			int itemIdx = distinctOrderNames.indexOf(orderItemName);
			int itemQty = orderItemQty.get(itemIdx);
			// System.out.println(orderItemName+"\t\t\t\t"+ "$"+itemPrice);
			// String item = "$"+itemPrice;
			// System.out.printf("%-20s%20.2f%s%n",orderItemName,itemPrice,"$");
			System.out.printf("%-10s%-1s%-10.2f%n%-10d%n", orderItemName, "$", itemPrice, itemQty);
			// System.out.println();
			// System.out.println("ItemQty " + itemQty);
		}
	}
	/*
	 * private static void displayOrderItems1() { for(int
	 * i=0;i<orderNames.size();i++) { String s = orderNames.get(i); Double
	 * itemPrice= items.get(s);
	 * 
	 * System.out.println(s+"\t\t"+ "$"+itemPrice); } }
	 */

	private static boolean isItemExists(String itemName) {
		boolean isExists = false;
		// System.out.println(items.containsKey(itemName));

		isExists = items.containsKey(itemName);
		return isExists;
	}

	// private static void fillItemMap() {
	// items.put("apple", .99);
//		items.put("bananna", .59);
//		items.put("cauliflower", 1.59);
//		items.put("dragonfruit", 2.19);
//		items.put("elderberry", 1.79);
//		items.put("figs", 2.09);
//		items.put("grape fruit", 1.99);
//		items.put("honeydew", 3.49);

	String filePath = "products.txt";
	HashMap<String, Double> map = new HashMap<String, Double>();
	
	String line;

	{

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("~~~", 3);
				if (parts.length >= 2) {
					String key = parts[0];
					Double value = Double.parseDouble(parts[3]);
					map.put(key, value);
				} else {
					System.out.println("ignoring line: " + line);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		
			
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		for (String key : map.keySet()) {
			System.out.println(key + ":" + map.get(key));
		}
	
			
	
		}

	

	public static List<Product> readFile() {
		try {
			List<String> lines = Files.readAllLines(filePath);

			// TODO #2 Add code here@@@ convert this list of String lines from
			// the file to a list of our objects. (Product, in my case)// TODO #2 Add code
			// here@@@ convert this list of String lines from
			// the file to a list of our objects. (Player, in my case)
			List<Product> countries = new ArrayList<>();
			for (String line : lines) {
				// System.out.println(line);
				String[] parts = line.split("~~~");
				// #2 select each part based on index position and convert in to a correct data
				// type
				// System.out.println(Arrays.toString(parts));
				String Product = parts[0];
				String category = parts[1];
				String description = parts[2];
				double price = Double.parseDouble(parts[3]);

				//countries.add(new Product(Product, category,description,price));

			}
			return countries;
		} catch (IOException e) {
			System.out.println("Unable to read file.");
			return new ArrayList<>();
		}

	}


	}

