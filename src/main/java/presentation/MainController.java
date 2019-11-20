package presentation;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Logger;

import javax.swing.UIManager;

import bll.Restaurant;
import model.BaseProduct;
import model.MenuItem;
import model.Order;

public class MainController {

	protected static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final String ERROR_ITEM_SELECTION = "Please select an item first";
	private static final String ERROR = "Error";
	private static final String SUCCESS = "Success";
	private static final String MENU_EXPORT_PATH = "src\\main\\resources\\menu.dat";
	private static final String BILL_OUTPUT_PATH = "src\\main\\resources\\ordersBills\\";

	private AdminView adminView;
	private AddMenuItemView addMenuItemView;
	private WaiterView waiterView;
	private ViewOrderView viewOrderView;
	private ChefView chefView;
	private LoginView loginView;

	private String operation;
	private Restaurant restaurant;
	private Order workingOrder;
	private MenuItemSelectView menuItemSelectView;

	public void start() {
		System.out.println("App started....");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		adminView = new AdminView(screenSize);
		addMenuItemView = new AddMenuItemView(screenSize);
		waiterView = new WaiterView(screenSize);
		viewOrderView = new ViewOrderView(screenSize);
		chefView = new ChefView(screenSize);
		loginView = new LoginView(screenSize);
		menuItemSelectView = new MenuItemSelectView(screenSize);

		restaurant = new Restaurant("");
		restaurant = restaurant.deserialize(MENU_EXPORT_PATH);
		restaurant.getObservable().addObserver(chefView);
		adminView.setRestaurantName(restaurant.getName());

		for (MenuItem item : restaurant.getMenu()) {
			adminView.addTableMenuItem(new Object[] { ((BaseProduct) item).getDescription(), item.computePrice() });
		}

		initializeLoginViewButtons();
		initializeAdminViewButtons();
		initializeWaiterViewButtons();
		initializeChefViewButtons();
		initializeAddMenuItemViewButtons();
		initializeViewOrderViewButtons();
		initializeMenuItemSelectViewButtons();

		loginView.setVisible(false);
		adminView.setVisible(true);
		addMenuItemView.setVisible(false);
		waiterView.setVisible(true);
		viewOrderView.setVisible(false);
		chefView.setVisible(true);
	}

	private void initializeLoginViewButtons() {
		loginView.addAdministratorLoginButtonActionListener(e -> {
			adminView.setVisible(true);
			loginView.setVisible(false);
		});

		loginView.addWaiterLoginButtonActionListener(e -> {
			waiterView.setVisible(true);
			loginView.setVisible(false);
		});

		loginView.addChefLoginButtonActionListener(e -> {
			chefView.setVisible(true);
			loginView.setVisible(false);
		});
	}

	private void initializeChefViewButtons() {
		chefView.addServeButtonActionListener(e -> {
			String[] rowData = chefView.getTableRow();
			if (rowData.length == 0) {
				chefView.displayMessage(ERROR_ITEM_SELECTION);
			} else {
				Order order = restaurant.getOrder(Integer.parseInt(rowData[2]));
				for (MenuItem item : order.getOrderedItems()) {
					if (((BaseProduct) item).getDescription().equals(rowData[0])) {
						((BaseProduct) item).setServed(true);
					}
				}
				chefView.removeTableRow(chefView.getTableRowIndex());
			}
		});
	}

	private void initializeMenuItemSelectViewButtons() {
		menuItemSelectView.addSelectButtonActionListener(e -> {
			String[] rowData = menuItemSelectView.getTableRow();
			if (rowData.length == 0) {
				menuItemSelectView.displayMessage(ERROR_ITEM_SELECTION, ERROR);
			} else {
				String description = rowData[0];
				double price = Double.parseDouble(rowData[1]);

				MenuItem item = restaurant.getMenuItem(description);
				workingOrder.addItem(item);

				viewOrderView.addTableRow(new Object[] { description, price });
				menuItemSelectView.setVisible(false);
				viewOrderView.setTableEnabled(true);
			}
		});

		menuItemSelectView.addCancelButtonActionListener(e -> {
			viewOrderView.setTableEnabled(true);
			menuItemSelectView.setVisible(false);
		});
	}

	private void initializeViewOrderViewButtons() {
		viewOrderView.addPlaceOrderButtonActionListener(e -> {
			if (workingOrder.getOrderedItems().isEmpty()) {
				viewOrderView.displayMessage("Please add a product", ERROR);
				return;
			}

			if (workingOrder.getStatus().equals("INACTIVE")) {
				workingOrder.setStatus("ACTIVE");
				restaurant.addOrder(workingOrder);
				waiterView.addTableOrder(new Object[] { workingOrder.getId(), workingOrder.getDate(),
						workingOrder.getTableId(), restaurant.computePrice(workingOrder), workingOrder.getStatus() });
			} else {
				restaurant.updateOrder(workingOrder);
				waiterView.setTableRowAtIndex(viewOrderView.getTableRowIndex(),
						new Object[] { workingOrder.getId(), workingOrder.getDate(), workingOrder.getTableId(),
								restaurant.computePrice(workingOrder), workingOrder.getStatus() });
			}

			viewOrderView.setVisible(false);
			waiterView.setTableEnabled(true);
		});

		viewOrderView.addSaveItemButtonActionListener(e -> {
			String orderId = viewOrderView.getOrderIdField();
			String tableId = viewOrderView.getTableIdField();
			if (validateOrderData(orderId, tableId)) {
				workingOrder = new Order(Integer.parseInt(orderId), Integer.parseInt(tableId));
				viewOrderView.setSaveButtonVisible(false);
				viewOrderView.setTableIdFieldVisible(false);
				viewOrderView.setOrderIdFieldVisible(false);
				viewOrderView.setOrderIdField(orderId);
				viewOrderView.setTableIdField(tableId);
				viewOrderView.setDateField(workingOrder.getDate());
			}
		});

		viewOrderView.addAddItemButtonActionListener(e -> {
			viewOrderView.setTableEnabled(false);
			menuItemSelectView.clearTable();
			for (MenuItem item : restaurant.getMenu()) {
				menuItemSelectView
						.addTableMenuItem(new Object[] { ((BaseProduct) item).getDescription(), item.computePrice() });
			}
			menuItemSelectView.setVisible(true);
		});

		viewOrderView.addRemoveItemButtonActionListener(e -> {
			String[] rowData = viewOrderView.getTableRow();
			if (rowData.length == 0) {
				viewOrderView.displayMessage(ERROR_ITEM_SELECTION, ERROR);
			} else {
				String description = rowData[0];
				MenuItem item = null;
				for (MenuItem orderedItem : workingOrder.getOrderedItems()) {
					if (((BaseProduct) orderedItem).getDescription().equals(description)) {
						item = orderedItem;
						break;
					}
				}
				workingOrder.getOrderedItems().remove(item);
				viewOrderView.removeTableRow(viewOrderView.getTableRowIndex());
				viewOrderView.displayMessage("Item successfully removed", SUCCESS);
			}
		});
	}

	private void initializeWaiterViewButtons() {
		waiterView.addNewOrderButtonActionListener(e -> {
			waiterView.setTableEnabled(false);

			viewOrderView.clearTable();
			viewOrderView.setOrderIdField("");
			viewOrderView.setTableIdField("");
			viewOrderView.setSaveButtonVisible(true);
			viewOrderView.setOrderIdFieldVisible(true);
			viewOrderView.setTableIdFieldVisible(true);
			viewOrderView.setVisible(true);
		});

		waiterView.addGenerateBillButtonActionListener(e -> {
			String[] rowData = waiterView.getTableRow();
			if (rowData.length == 0) {
				waiterView.displayMessage(ERROR_ITEM_SELECTION, ERROR);
			} else {
				int orderId = Integer.parseInt(rowData[0]);
				workingOrder = restaurant.getOrder(orderId);

				if (workingOrder.getStatus().equals("FINISHED")) {
					waiterView.displayMessage("Order was already finalized", ERROR);
					return;
				}

				for (MenuItem item : workingOrder.getOrderedItems()) {
					if (!((BaseProduct) item).isServed()) {
						waiterView.displayMessage("Not all items were served", ERROR);
						return;
					}
				}

				restaurant.generateBill(workingOrder, BILL_OUTPUT_PATH + "order_" + workingOrder.getId() + ".txt");
				rowData[4] = "FINISHED";

				waiterView.setTableRowAtIndex(waiterView.getTableRowIndex(), rowData);
				waiterView.displayMessage("Bill generation was a success", SUCCESS);
			}
		});

		waiterView.addViewOrderButtonActionListener(e -> {
			String[] rowData = waiterView.getTableRow();
			if (rowData.length == 0) {
				waiterView.displayMessage("Please select an order first", ERROR);
			} else {
				int orderId = Integer.parseInt(rowData[0]);
				workingOrder = restaurant.getOrder(orderId);
				if (workingOrder == null) {
					waiterView.displayMessage("Order does not exist", ERROR);
					return;
				}

				viewOrderView.clearTable();
				for (MenuItem item : workingOrder.getOrderedItems()) {
					viewOrderView
							.addTableRow(new Object[] { ((BaseProduct) item).getDescription(), item.computePrice() });
				}

				boolean status = workingOrder.getStatus().equals("INACTIVE");
				viewOrderView.setAddItemButtonVisible(status);
				viewOrderView.setRemoveItemButtonVisible(status);
				viewOrderView.setPlaceOrderButtonVisible(status);
				viewOrderView.setVisible(true);
			}
		});
	}

	private void initializeAddMenuItemViewButtons() {
		addMenuItemView.addSaveButtonActionListener(e -> {
			String description = addMenuItemView.getDescription();
			String price = addMenuItemView.getPrice();
			if (validateNewMenuItem(description, price)) {
				if (operation.equals("create")) {
					adminView.addTableMenuItem(new Object[] { description, price });
					restaurant.addMenuItem(new BaseProduct(description, Double.parseDouble(price)));
				} else if (operation.equals("edit")) {
					MenuItem item = restaurant.getMenuItem(description);
					restaurant.deleteMenuItem(item);
					((BaseProduct) item).setPrice(Double.parseDouble(price));
					restaurant.addMenuItem(item);
					adminView.setTableRowAtIndex(adminView.getTableRowIndex(), new Object[] { description, price });
				}

				restaurant.serialize(MENU_EXPORT_PATH);

				addMenuItemView.setVisible(false);
				addMenuItemView.setDescription("");
				addMenuItemView.setPrice("");
				operation = "";
			}
		});
	}

	private void initializeAdminViewButtons() {
		adminView.addCreateButtonActionListener(e -> {
			addMenuItemView.setVisible(true);
			operation = "create";
		});

		adminView.addEditButtonActionListener(e -> {
			String[] rowData = adminView.getTableRow();
			if (rowData.length == 0) {
				adminView.displayessage(ERROR_ITEM_SELECTION, ERROR);
			} else {
				addMenuItemView.setDescription(rowData[0]);
				addMenuItemView.setPrice(rowData[1]);
				addMenuItemView.setVisible(true);
				operation = "edit";
			}
		});

		adminView.addDeleteButtonActionListener(e -> {
			String[] rowData = adminView.getTableRow();
			if (rowData.length == 0) {
				adminView.displayessage(ERROR_ITEM_SELECTION, ERROR);
			} else if (adminView.displayConfirmation("Are you sure you want to delete this entry?")) {
				MenuItem item = restaurant.getMenuItem(rowData[0]);
				restaurant.deleteMenuItem(item);
				adminView.removeTableRow(adminView.getTableRowIndex());
				restaurant.serialize(MENU_EXPORT_PATH);
			}
		});
	}

	private boolean validateOrderData(String orderId, String tableId) {
		if (orderId.equals("") || tableId.equals("")) {
			viewOrderView.displayMessage("Please fill all the fields", ERROR);
			return false;
		}

		if (Integer.parseInt(orderId) < 0) {
			viewOrderView.displayMessage("Invalid orderId format", ERROR);
			return false;
		}

		if (Integer.parseInt(tableId) < 0) {
			viewOrderView.displayMessage("Invalid tableId format", ERROR);
			return false;
		}

		return true;
	}

	private boolean validateNewMenuItem(String description, String price) {
		if (description.equals("") || price.equals("")) {
			addMenuItemView.displayMessage("Please fill all the fields");
			return false;
		}
		double itemPrice = -1;
		try {
			itemPrice = Double.parseDouble(price);
		} catch (NumberFormatException e) {
			addMenuItemView.displayMessage("Invalid price format ");
			return false;
		}

		if (itemPrice < 0) {
			addMenuItemView.displayMessage("Invalid price");
			return false;
		}
		return true;
	}

}
