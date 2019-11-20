package bll;

import model.MenuItem;
import model.Order;

public interface RestaurantProcessing {
	void addMenuItem(MenuItem menuItem);
	void deleteMenuItem(MenuItem menuItem);
	void updateMenuItem(MenuItem menuItem);
	
	void addOrder(Order order);
	double computePrice(Order order);
	void generateBill(Order order, String outputPath);
}
