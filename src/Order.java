
public class Order extends Product {
	private int quantity;

	public Order() {
		super();

	}

	public Order(int quantity) {
		super();
		this.quantity = quantity;
	}

	public Order(int id, String name, String category, String description, double price, int quantity) {
		super(id, name, category, description, price);
		this.quantity = quantity;

	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Order [quantity=" + quantity + ", getId()=" + getId() + ", getName()=" + getName() + ", getCategory()="
				+ getCategory() + ", getDescription()=" + getDescription() + ", getPrice()=" + getPrice() + "]";
	}

}
