package assign4;

import org.junit.Test;

import bll.Restaurant;
import model.BaseProduct;
import model.Order;
import model.OrderObserver;

public class TestChefObserver {

	@Test
	public void testObserver() {
		Restaurant restaurant = new Restaurant("MyRestaurant");
		OrderObserver orderObs = new OrderObserver();

		restaurant.getObservable().addObserver(orderObs);

		Order order = new Order(1, 4);
		order.addItem(new BaseProduct("Apa plata", 2.5));
		order.addItem(new BaseProduct("Ceai negru", 7));

		restaurant.addOrder(order);
		System.out.println("Order1 price = " + restaurant.computePrice(order) + "\n");

		Order order2 = new Order(2, 2);
		order2.addItem(new BaseProduct("Iarba matzei", 60));
		order2.addItem(new BaseProduct("Nisip de litiera", 23.4));
		order2.addItem(new BaseProduct("Cutie de carton", 15));

		restaurant.addOrder(order2);
		System.out.println("Order2 price = " + restaurant.computePrice(order2) + "\n");
	}

}
