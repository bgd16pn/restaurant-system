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

public class WaiterView extends JFrame {

	private static final long serialVersionUID = 2326652750247710422L;

	private static final String TAHOMA = "Tahoma";
	private static final int WINDOW_WIDTH = 670;
	private static final int WINDOW_HEIGHT = 670;

	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel lblMessage;
	private JButton btnNewOrder;
	private JButton btnViewOrder;
	private JButton btnGenerateBill;

	public WaiterView(Dimension screenSize) {

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds((int) (screenSize.getWidth() / 2.0) - (int) (WINDOW_WIDTH / 2.0),
				(int) (screenSize.getHeight() / 2.0) - (int) (WINDOW_HEIGHT / 2.0), WINDOW_WIDTH, WINDOW_HEIGHT);
		getContentPane().setLayout(null);
		this.setResizable(false);
		this.setTitle("Waiter Mode");
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		tableModel = new DefaultTableModel(new Object[] { "OrderId", "Date", "Table", "Price", "Status" }, 0) {
			private static final long serialVersionUID = 4765017032731172939L;

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
		scrollPane.setBounds(38, 32, 587, 498);
		getContentPane().add(scrollPane);

		btnNewOrder = new JButton("New order");
		btnNewOrder.setBounds(87, 555, 97, 25);
		getContentPane().add(btnNewOrder);

		btnViewOrder = new JButton("View Order");
		btnViewOrder.setBounds(271, 555, 97, 25);
		getContentPane().add(btnViewOrder);

		btnGenerateBill = new JButton("Generate bill");
		btnGenerateBill.setBounds(455, 555, 122, 25);
		getContentPane().add(btnGenerateBill);

		lblMessage = new JLabel("Message");
		lblMessage.setFont(new Font(TAHOMA, Font.BOLD, 14));
		lblMessage.setBounds(37, 593, 489, 29);
		getContentPane().add(lblMessage);
		lblMessage.setVisible(false);
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
	
	public void setTableRowAtIndex(int rowIndex, Object[] rowData) {
		for (int i = 0; i < rowData.length; i++) {
			tableModel.setValueAt(rowData[i], rowIndex, i);
		}
	}

	public void addTableOrder(Object[] rowData) {
		tableModel.addRow(rowData);
	}

	public void removeTableRow(int rowIndex) {
		tableModel.removeRow(rowIndex);
	}

	public void addTableActionListener(ListSelectionListener listSelectionListener) {
		table.getSelectionModel().addListSelectionListener(listSelectionListener);
	}

	public void addNewOrderButtonActionListener(ActionListener actionListener) {
		btnNewOrder.addActionListener(actionListener);
	}

	public void addViewOrderButtonActionListener(ActionListener actionListener) {
		btnViewOrder.addActionListener(actionListener);
	}

	public void addGenerateBillButtonActionListener(ActionListener actionListener) {
		btnGenerateBill.addActionListener(actionListener);
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

	public int getTableRowIndex() {
		return table.getSelectedRow();
	}

	public void setTableEnabled(boolean flag) {
		table.setEnabled(flag);
	}

}
