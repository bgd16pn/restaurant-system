package model;

public class BaseProduct implements MenuItem {

	private static final long serialVersionUID = 8047123666457002022L;
	
	private String description;
	private double price;
	private boolean served;

	public BaseProduct(String description, double price) {
		this.description = description;
		this.price = price;
		served = false;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isServed() {
		return served;
	}

	public void setServed(boolean served) {
		this.served = served;
	}

	@Override
	public double computePrice() {
		return this.price;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BaseProduct)) {
			return false;
		}
		BaseProduct product = (BaseProduct) obj;
		if (product.price != price) {
			return false;
		}
		return product.description.equals(description);
	}

	@Override
	public int hashCode() {
		int res = 17;
		res = res * 31 + (int) price;
		res = res * 31 + description.hashCode();
		res = res * 31 * (served ? 1 : 0);
		return res;
	}

	@Override
	public String toString() {
		return "Item: Description = " + description + ", Price = " + price + ", Served = " + served;
	}

	public MenuItem deepCopy() {
		MenuItem newItem = new BaseProduct(description, price);
		((BaseProduct) newItem).setServed(served);
		return newItem;
	}

}
