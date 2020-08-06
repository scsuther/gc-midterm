import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;


public class MidTerm {

	
	private static Scanner scnr = new Scanner(System.in);
	private static Path filePath = Paths.get("products.txt");

	public static void main(String[] args) {

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
				List<Product> things = readFile();

//				
				// things.sort((Product c1,Product c2)->
				// c1.getName().toLowerCase().compareTo(c2.getName().toLowerCase()));

				ObjectComparator comparator = new ObjectComparator();
				Collections.sort(things, comparator);

				int i = 1;
				for (Product thing : things) {

					System.out.println(i++ + ". " + thing);
				}
			} else if (command == 2) {
				Product Product = getProductFromUser(scnr);
				System.out.println("Adding " + Product);
				appendLineToFile(Product);
			} /*else if (command == 3) {
//				
				deleteProduct();
			} else if (command == 4) {
//				
				editProduct();
			}*/ else {
				System.out.println("Invalid command.");
			}
		}
		System.out.println("Goodbye.");
		scnr.close();
	}
/*
	private static void deleteProduct() {

		List<Product> things = readFile();
		scnr.nextLine();

		String Product = Validator.getString(scnr, "Enter Product: ");

		for (int i = 0; i < things.size(); i++) {

			if (things.get(i).getName().equalsIgnoreCase(Product)) {
				Product removeProduct = things.get(i);
				things.remove(i);
				System.out.println("Deleting " + removeProduct);

			}

		}

		// things.stream().filter(i ->i.getName().equals(""));
		try {
			Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING);
			Files.newInputStream(filePath, StandardOpenOption.TRUNCATE_EXISTING);
			for (Product thing : things) {
				appendLineToFile(thing);
			}

		} catch (IOException e) {
			System.out.println("Unable to write to file.");
		}
	}

	private static void editProduct() {
		scnr.nextLine();
		String Product = Validator.getString(scnr, "Enter Product: ");
		int population = Validator.getInt(scnr, "Enter population: ");
		List<Product> things = readFile();
		for (Product thing : things) {
			if (thing.getName().equalsIgnoreCase(Product)) {
				thing.setPrice(population);
				System.out.println("Modifying " + thing);

			}
		}
		try {
			Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING);
			Files.newInputStream(filePath, StandardOpenOption.TRUNCATE_EXISTING);
			for (Product thing : things) {
				appendLineToFile(thing);
			}

		} catch (IOException e) {
			System.out.println("Unable to write to file.");
		}
	}*/

	private static Product getProductFromUser(Scanner scnr) {
		scnr.nextLine();
		// TODO #1 adjust this for your class, not Product
		String Product = Validator.getString(scnr, "Enter Product: ");
		String category = Validator.getString(scnr, "Enter Category: ");
		String description = Validator.getString(scnr, "Enter Description: ");
		
		double price = Validator.getDouble(scnr, "Enter price: ");

		return new Product(Product, category,description,price);
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

				countries.add(new Product(Product, category,description,price));

			}
			return countries;
		} catch (IOException e) {
			System.out.println("Unable to read file.");
			return new ArrayList<>();
		}
	}

	/**
	 * Add an object to the end of the file.
	 */
	public static void appendLineToFile(Product thing) {
		String line = "TODO";

		// TODO #3 add code here@@@ convert your object to a string like
		// it should be as a line in the file. store it in the `line` variable.
		line = thing.getName() + "~~~" + thing.getCategory() + "~~~" + thing.getDescription()+"~~~" +thing.getPrice();
		line = String.format("%s~~~%s~~~%s~~~%f", thing.getName(), thing.getCategory(), thing.getDescription(),thing.getPrice());
		// line=thing.toString
		// Files.write requires a list of lines. Create a list of one line.
		List<String> lines = Collections.singletonList(line);
		try {
			Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("Unable to write to file.");
		}
	}

}

class ObjectComparator implements Comparator<Product> {

	public int compare(Product obj1, Product obj2) {
		return obj1.getName().toLowerCase().compareTo(obj2.getName().toLowerCase());
	}


	}