package assign4;

import org.junit.Assert;
import org.junit.Test;

import bll.Restaurant;
import model.BaseProduct;
import model.MenuItem;
import model.Order;

public class TestRestaurant {

	@Test
	public void TestMenu() {
		Restaurant restaurant = new Restaurant("MyRestaurant");
		MenuItem p1 = new BaseProduct("Supa de linte", 11.2);
		MenuItem p2 = new BaseProduct("Apa de ploaie", 34.27);
		MenuItem p3 = new BaseProduct("Foame de lup", 55.9);
		MenuItem p4 = new BaseProduct("Rabdari prajite", 100);

		restaurant.addMenuItem(p1);
		restaurant.addMenuItem(p2);
		restaurant.addMenuItem(p3);
		restaurant.addMenuItem(p4);

		System.out.println("Restaurant Menu:");
		for (MenuItem item : restaurant.getMenu()) {
			System.out.println(item);
		}

		Order order = new Order(1, 4);
		order.addItem(p3);
		order.addItem(p4);
		restaurant.addOrder(order);
		Assert.assertTrue(restaurant.isWellFormed());

		Order order2 = new Order(2, 4);
		order.addItem(new BaseProduct("Foi scrise la TS", 1000));
		restaurant.addOrder(order2);
		Assert.assertFalse(restaurant.isWellFormed());

		Order order3 = new Order(3, 4);
		restaurant.addOrder(order3);
		Assert.assertFalse(restaurant.isWellFormed());

		Order order4 = new Order(1, 4);
		order4.addItem(p4);
		restaurant.addOrder(order4);
		Assert.assertFalse(restaurant.isWellFormed());
	}

	@Test
	public void testSerialization() {

		String outputPath = "src\\main\\resources\\menu.dat";

		Restaurant restaurant = new Restaurant("MyRestaurant");
		MenuItem p1 = new BaseProduct("Supa de linte", 11.2);
		MenuItem p2 = new BaseProduct("Apa de ploaie", 34.27);
		MenuItem p3 = new BaseProduct("Foame de lup", 55.9);
		MenuItem p4 = new BaseProduct("Rabdari prajite", 100);
		restaurant.addMenuItem(p1);
		restaurant.addMenuItem(p2);
		restaurant.addMenuItem(p3);
		restaurant.addMenuItem(p4);

		restaurant.serialize(outputPath);
	}

	@Test
	public void testDeserialization() {

		String inputPath = "src\\main\\resources\\menu.dat";
		Restaurant restaurant = new Restaurant("MyRestaurant");
		restaurant = restaurant.deserialize(inputPath);

		System.out.println(restaurant);
	}

}
