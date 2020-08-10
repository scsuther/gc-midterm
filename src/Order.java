
public class Order extends Product{
private int quantity;
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(int quantity) {
		super();
		this.quantity = quantity;
	}
	
	public Order(int id, String name, String category, String description, double price,int quantity) {
		super(id, name, category, description, price);
		this.quantity=quantity;
		// TODO Auto-generated constructor stub
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
