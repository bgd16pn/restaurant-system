package model;

import java.util.Observable;
import java.util.Observer;

public class OrderObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Order) {
			Order order = (Order) arg;
			System.out.println("OrderObserver: New order was added -> " + order);
		}
	}

}
