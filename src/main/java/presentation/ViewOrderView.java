package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class ViewOrderView extends JFrame {

	private static final long serialVersionUID = -4427270651778209632L;

	private static final String TAHOMA = "Tahoma";
	private static final int WINDOW_WIDTH = 570;
	private static final int WINDOW_HEIGHT = 670;
	private static final Object[] COLUMN_HEADERS = new Object[] { "Menu item", "Price" };

	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel lblMessage;
	private JButton btnAddItem;
	private JButton btnRemoveItem;
	private JLabel lblDate;
	private JLabel lblOrderToInsert;
	private JLabel lblTableToInsert;
	private JLabel lblDateToInsert;
	private JTextField orderIdTextField;
	private JTextField tableIdTextField;
	private JButton btnSave;
	private JButton btnPlaceOrder;

	public ViewOrderView(Dimension screenSize) {

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds((int) (screenSize.getWidth() / 2.0) - (int) (WINDOW_WIDTH / 2.0),
				(int) (screenSize.getHeight() / 2.0) - (int) (WINDOW_HEIGHT / 2.0), WINDOW_WIDTH, WINDOW_HEIGHT);
		getContentPane().setLayout(null);
		this.setResizable(false);
		this.setTitle("View order");
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		tableModel = new DefaultTableModel(COLUMN_HEADERS, 0) {
			private static final long serialVersionUID = 3653891547373258314L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setShowVerticalLines(true);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(37, 51, 489, 462);
		getContentPane().add(scrollPane);

		btnAddItem = new JButton("Add item");
		btnAddItem.setBounds(58, 555, 97, 25);
		getContentPane().add(btnAddItem);

		btnRemoveItem = new JButton("Remove item");
		btnRemoveItem.setBounds(213, 555, 116, 25);
		getContentPane().add(btnRemoveItem);

		lblMessage = new JLabel("Message");
		lblMessage.setFont(new Font(TAHOMA, Font.BOLD, 14));
		lblMessage.setBounds(37, 593, 489, 29);
		lblMessage.setVisible(false);
		getContentPane().add(lblMessage);

		JLabel lblOrderId = new JLabel("Order id");
		lblOrderId.setFont(new Font(TAHOMA, Font.BOLD, 13));
		lblOrderId.setHorizontalAlignment(SwingConstants.LEFT);
		lblOrderId.setBounds(37, 18, 56, 20);
		getContentPane().add(lblOrderId);

		JLabel lblTable = new JLabel("Table");
		lblTable.setFont(new Font(TAHOMA, Font.BOLD, 13));
		lblTable.setHorizontalAlignment(SwingConstants.LEFT);
		lblTable.setBounds(207, 18, 44, 20);
		getContentPane().add(lblTable);

		lblDate = new JLabel("Date");
		lblDate.setFont(new Font(TAHOMA, Font.BOLD, 13));
		lblDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate.setBounds(331, 19, 44, 20);
		getContentPane().add(lblDate);

		lblOrderToInsert = new JLabel("orderId");
		lblOrderToInsert.setBounds(105, 18, 56, 20);
		getContentPane().add(lblOrderToInsert);

		lblTableToInsert = new JLabel("table");
		lblTableToInsert.setBounds(263, 18, 56, 20);
		getContentPane().add(lblTableToInsert);

		lblDateToInsert = new JLabel("date");
		lblDateToInsert.setBounds(383, 19, 143, 20);
		getContentPane().add(lblDateToInsert);

		orderIdTextField = new JTextField();
		orderIdTextField.setBounds(98, 17, 71, 22);
		getContentPane().add(orderIdTextField);
		orderIdTextField.setColumns(10);
		orderIdTextField.setVisible(false);

		tableIdTextField = new JTextField();
		tableIdTextField.setColumns(10);
		tableIdTextField.setBounds(251, 17, 56, 22);
		getContentPane().add(tableIdTextField);
		tableIdTextField.setVisible(false);

		btnSave = new JButton("Save");
		btnSave.setBounds(331, 13, 97, 25);
		getContentPane().add(btnSave);
		btnSave.setVisible(false);

		btnPlaceOrder = new JButton("Update order");
		btnPlaceOrder.setFont(new Font(TAHOMA, Font.BOLD, 13));
		btnPlaceOrder.setForeground(Color.MAGENTA);
		btnPlaceOrder.setBounds(387, 555, 124, 25);
		getContentPane().add(btnPlaceOrder);
	}

	public String[] getTableRow() {
		String[] rowData = new String[table.getColumnCount()];
		if (table.getSelectedRow() == -1) {
			return new String[0];
		}
		for (int i = 0; i < rowData.length; i++) {
			rowData[i] = table.getValueAt(table.getSelectedRow(), i).toString();
		}
		return rowData;
	}

	public void addTableRow(Object[] rowData) {
		tableModel.addRow(rowData);
	}

	public void removeTableRow(int rowIndex) {
		tableModel.removeRow(rowIndex);
	}

	public void addTableActionListener(ListSelectionListener listSelectionListener) {
		table.getSelectionModel().addListSelectionListener(listSelectionListener);
	}

	public void addAddItemButtonActionListener(ActionListener actionListener) {
		btnAddItem.addActionListener(actionListener);
	}

	public void addRemoveItemButtonActionListener(ActionListener actionListener) {
		btnRemoveItem.addActionListener(actionListener);
	}

	public void addSaveItemButtonActionListener(ActionListener actionListener) {
		btnSave.addActionListener(actionListener);
	}

	public void addPlaceOrderButtonActionListener(ActionListener actionListener) {
		btnPlaceOrder.addActionListener(actionListener);
	}

	public void setSaveButtonVisible(boolean flag) {
		btnSave.setVisible(flag);
		btnAddItem.setVisible(!flag);
		btnRemoveItem.setVisible(!flag);
		btnPlaceOrder.setVisible(!flag);
		lblDate.setVisible(!flag);
		lblDateToInsert.setVisible(!flag);
	}

	public void setOrderIdField(String id) {
		orderIdTextField.setText(id);
		lblOrderToInsert.setText(id);
	}

	public String getOrderIdField() {
		return orderIdTextField.getText();
	}

	public void setOrderIdFieldVisible(boolean flag) {
		orderIdTextField.setVisible(flag);
		lblOrderToInsert.setVisible(!flag);
	}

	public void setTableIdField(String id) {
		tableIdTextField.setText(id);
		lblTableToInsert.setText(id);
	}

	public String getTableIdField() {
		return tableIdTextField.getText();
	}

	public void setDateFieldVisible(boolean flag) {
		lblDateToInsert.setVisible(flag);
		lblDate.setVisible(flag);
	}

	public void setDateField(String date) {
		lblDateToInsert.setText(date);
	}

	public void setTableIdFieldVisible(boolean flag) {
		tableIdTextField.setVisible(flag);
		lblTableToInsert.setVisible(!flag);
	}

	public void clearTable() {
		tableModel.setDataVector(null, COLUMN_HEADERS);
	}

	public int getTableRowIndex() {
		return table.getSelectedRow();
	}

	public void setTableEnabled(boolean flag) {
		table.setEnabled(flag);
	}

	public void setAddItemButtonVisible(boolean flag) {
		btnAddItem.setVisible(flag);
	}

	public void setRemoveItemButtonVisible(boolean flag) {
		btnRemoveItem.setVisible(flag);
	}

	public void setPlaceOrderButtonVisible(boolean flag) {
		btnPlaceOrder.setVisible(flag);
	}

	public void displayMessage(String message, String type) {
		Runnable runnable = () -> {
			lblMessage.setText(message);
			if (type.equals("Error")) {
				lblMessage.setForeground(Color.RED);
			} else if (type.equals("Success")) {
				lblMessage.setForeground(Color.GREEN);
			}
			lblMessage.setVisible(true);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				lblMessage.setVisible(false);
				lblMessage.setForeground(Color.BLACK);
			}
		};

		new Thread(runnable).start();
	}

	public boolean displayConfirmation(String message) {
		int option = JOptionPane.showConfirmDialog(this, message, "Confirm your selection",
				JOptionPane.OK_CANCEL_OPTION);
		return option == JOptionPane.OK_OPTION;
	}

}
