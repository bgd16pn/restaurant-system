package model;

import java.util.ArrayList;
import java.util.List;

public class CompositeProduct implements MenuItem {
	private static final long serialVersionUID = -2964233375658462827L;
	
	private List<MenuItem> menuItems;

	public CompositeProduct() {
		menuItems = new ArrayList<>();
	}

	@Override
	public double computePrice() {
		double price = 0;
		for (MenuItem item : menuItems) {
			price += item.computePrice();
		}
		return price;
	}

	public List<MenuItem> getAll() {
		return menuItems;
	}

	public void addMenuItem(MenuItem menuItem) {
		menuItems.add(menuItem);
	}

	public void deleteMenuItem(MenuItem menuItem) {
		menuItems.remove(menuItem);
	}

	public void updateMenuItem(MenuItem menuItem) {
		deleteMenuItem(menuItem);
		addMenuItem(menuItem);
	}
}
