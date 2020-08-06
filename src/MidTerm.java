import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MidTerm {

	
		
		// System.out.println("Welcome to  Lakshmi's Market!");
		// System.out.println("Please choose an item from the menu below: ");
		// Note: NIO uses the Path class, not just a file path String.
		private static Path filePath = Paths.get("products.txt");

		public static void main(String[] args) {
			Scanner scnr = new Scanner(System.in);

			while (true) {
				System.out.print("Enter a command (list, add, quit): ");
				String command = scnr.nextLine();
				if (command.equals("quit")) {
					break;
				} else if (command.equals("list")) {
					List<Product> things = readFile();
					int i = 1; 
					for (Product thing : things) {
						System.out.println(i++ + ". " + thing);
					}
				} else if (command.equals("add")) {
					Product Product = getProductFromUser(scnr);
					System.out.println("Adding " + Product);
					appendLineToFile(Product);
				} else {
					System.out.println("Invalid command.");
				}
			}
			System.out.println("Goodbye.");
			scnr.close();
		}
		
		private static Product getProductFromUser(Scanner scnr) {
			// TODO #1 adjust this for your class, not Product
			String name = Validator.getString(scnr, "Enter Product name:");
			String category = Validator.getString(scnr, "Enter category:");
			String description = Validator.getString(scnr, "Enter description:");
			double price = Validator.getDouble(scnr, "Enter price:");
			return new Product(name, category, description, price);
		}

		/**
		 * Read all the objects from a file and store them in a List.
		 */
		public static List<Product> readFile() {
			try {
				List<String> lines = Files.readAllLines(filePath);
				
				List<Product> Product = new ArrayList<>();
				for (String line : lines) {
					// #1 split the line based on the delimiter
					String[] parts = line.split("~~~");
					// #2 select each part based on index position and convert
					// to the correct type
					String name = parts[0];
					String category = parts[1];
					String description = parts[2];
					double price = Double.parseDouble(parts[3]);
					Product.add(new Product(name, category, description, price));
				}
				
				return Product;
			} catch (IOException e) {
				System.out.println("Unable to read file.");
				return new ArrayList<>();
			}
		}

		/**
		 * Add an object to the end of the file.
		 */
		public static void appendLineToFile(Product thing) {
			String line = thing.getName() + "~~~" + thing.getcategory();		
			// TODO #3 add code here... convert your object to a string like
			// it should be as a line in the file. store it in the `line` variable.
			
			// Files.write requires a list of lines. Create a list of one line.
			List<String> lines = Collections.singletonList(line);
			try {
				Files.write(filePath, lines, StandardOpenOption.CREATE,
						StandardOpenOption.APPEND);
			} catch (IOException e) {
				System.out.println("Unable to write to file.");
			}
		}

	}