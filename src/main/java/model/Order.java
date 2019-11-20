package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

	private int orderId;
	private Date date;
	private int tableId;
	private String status;
	private List<MenuItem> orderedItems;

	public Order(int orderId, int tableId) {
		this.orderId = orderId;
		this.tableId = tableId;
		this.date = new Date();
		this.status = "INACTIVE";
		this.orderedItems = new ArrayList<>();
	}

	public List<MenuItem> getOrderedItems() {
		return orderedItems;
	}

	public void addItem(MenuItem menuItem) {
		orderedItems.add(menuItem);
	}

	public int getId() {
		return orderId;
	}

	public int getTableId() {
		return tableId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Order: Id = " + orderId + ", Date = " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date)
				+ ", TableId = " + tableId + ", Status = " + status + "\n\t" + orderedItems;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Order)) {
			return false;
		}
		Order order = (Order) obj;
		if (order.orderId != orderId) {
			return false;
		}
		if (tableId != order.tableId) {
			return false;
		}
		if (!order.date.equals(date)) {
			return false;
		}
		if (order.orderedItems.size() != orderedItems.size()) {
			return false;
		}
		int size = orderedItems.size();
		for (int i = 0; i < size; i++) {
			if (!orderedItems.get(i).equals(order.orderedItems.get(i))) {
				return false;
			}
		}
		return status.equals(order.getStatus());
	}

	@Override
	public int hashCode() {
		int res = 17;
		res = res * 31 + orderId;
		res = res * 31 + date.hashCode();
		res = res * 31 + tableId;
		res = res * 31 + orderedItems.hashCode();
		res = res * 31 + status.hashCode();
		return res;
	}

	public String getDate() {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
	}
}
