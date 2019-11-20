package bll;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import component.BillGenerator;
import model.BaseProduct;
import model.CompositeProduct;
import model.MenuItem;
import model.ObservableType;
import model.Order;

public class Restaurant implements RestaurantProcessing, Serializable {

	private static final long serialVersionUID = -337031331447499454L;

	protected static final Logger LOGGER = Logger.getLogger(Restaurant.class.getName());

	private String name;
	private Set<MenuItem> menu;
	private ObservableType observable;
	private transient Map<Order, List<MenuItem>> orders;

	public Restaurant(String name) {
		this.name = name;
		this.menu = new HashSet<>();
		this.orders = new HashMap<>();
		this.observable = new ObservableType();
	}

	public String getName() {
		return name;
	}

	public ObservableType getObservable() {
		return observable;
	}

	public Set<MenuItem> getMenu() {
		return menu;
	}

	/**
	 * Adds a new item in the menu items set
	 * 
	 * @pre menu != null && menuItem != null
	 * @post menu.size() == menu.size()@pre + 1
	 * @invariant isWellFormed()
	 */
	public void addMenuItem(MenuItem menuItem) {
		menu.add(menuItem);
	}

	/**
	 * Removes the item in the menu items set
	 * 
	 * @pre menu.size() > 0 && menuItem != null
	 * @post menu.size() == menu.size()@pre - 1
	 * @invariant isWellFormed()
	 */
	public void deleteMenuItem(MenuItem menuItem) {
		menu.remove(menuItem);
	}

	public void updateMenuItem(MenuItem menuItem) {
		deleteMenuItem(menuItem);
		addMenuItem(menuItem);
	}

	public void addOrder(Order order) {
		orders.put(order, order.getOrderedItems());
		observable.setChanged();
		observable.notifyObservers(order);
	}

	public double computePrice(Order order) {
		CompositeProduct orderedProducts = new CompositeProduct();
		for (MenuItem item : orders.get(order)) {
			orderedProducts.addMenuItem(item);
		}
		return orderedProducts.computePrice();
	}

	public void generateBill(Order order, String outputPath) {
		order.setStatus("FINISHED");
		BillGenerator.generateBill(this, order, outputPath);
	}

	public Order getOrder(int orderId) {
		Iterator<Order> it = orders.keySet().iterator();
		while (it.hasNext()) {
			Order order = it.next();
			if (order.getId() == orderId) {
				return order;
			}
		}
		return null;
	}

	public MenuItem getMenuItem(String description) {
		Iterator<MenuItem> it = menu.iterator();
		while (it.hasNext()) {
			MenuItem item = it.next();
			if (((BaseProduct) item).getDescription().equals(description)) {
				return ((BaseProduct) item).deepCopy();
			}
		}
		return null;
	}

	public void updateOrder(Order order) {
		Order toDel = null;
		for (Order ord : orders.keySet()) {
			if (ord.getId() == order.getId()) {
				toDel = ord;
				break;
			}
		}
		orders.remove(toDel);
		orders.put(order, order.getOrderedItems());
	}

	public Restaurant deserialize(String menuFilePath) {
		ObjectInputStream instream = null;
		Restaurant newRestaurant = null;
		try (FileInputStream file = new FileInputStream(menuFilePath)) {
			instream = new ObjectInputStream(file);
			newRestaurant = (Restaurant) instream.readObject();
			instream.close();
			LOGGER.log(Level.INFO, "Successfully deserialized!");
		} catch (IOException | ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}

		if (newRestaurant == null) {
			newRestaurant = new Restaurant("");
		}

		newRestaurant.orders = new HashMap<>();
		return newRestaurant;
	}

	public void serialize(String filename) {
		ObjectOutputStream outstream = null;
		try (FileOutputStream file = new FileOutputStream(filename)) {
			outstream = new ObjectOutputStream(file);
			outstream.writeObject(this);
			outstream.close();
			LOGGER.log(Level.INFO, "Successfully serialized!");
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
	}

	/**
	 * Any item of the ordered items must be in the menu. An order must have at
	 * least one item. Orders must have distinct id.
	 * 
	 * @return is well formed
	 */
	public boolean isWellFormed() {
		int size = orders.size();
		List<Order> ordersI = orders.keySet().stream().collect(Collectors.toList());
		List<Order> ordersJ = ordersI;
		for (int i = 0; i < size - 1; i++) {
			for (int j = i; j < size; j++) {
				if (ordersI.get(i).getId() == ordersJ.get(j).getId()) {
					return false;
				}
			}
		}
		for (Order order : orders.keySet()) {
			if (order.getOrderedItems().isEmpty()) {
				return false;
			}
		}
		for (List<MenuItem> orderedItems : orders.values()) {
			for (MenuItem item : orderedItems) {
				if (!menu.contains(item)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Restaurant: Name = " + name + ", Menu = " + menu;
	}

}
