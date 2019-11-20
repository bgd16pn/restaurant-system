package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

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

import model.BaseProduct;
import model.MenuItem;
import model.Order;

public class ChefView extends JFrame implements Observer {

	private static final long serialVersionUID = -1906392552772341451L;

	private static final String TAHOMA = "Tahoma";
	private static final int WINDOW_WIDTH = 570;
	private static final int WINDOW_HEIGHT = 670;

	private JTable table;
	private JLabel lblMessage;
	private JButton btnServe;
	private DefaultTableModel tableModel;

	public ChefView(Dimension screenSize) {

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds((int) (screenSize.getWidth() / 2.0) - (int) (WINDOW_WIDTH / 2.0),
				(int) (screenSize.getHeight() / 2.0) - (int) (WINDOW_HEIGHT / 2.0), WINDOW_WIDTH, WINDOW_HEIGHT);
		getContentPane().setLayout(null);
		this.setResizable(false);
		this.setTitle("Chef Mode");
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		tableModel = new DefaultTableModel(new Object[] { "Menu item", "Table Id", "Order Id" }, 0) {
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
		scrollPane.setBounds(37, 32, 489, 481);
		getContentPane().add(scrollPane);

		btnServe = new JButton("Serve item");
		btnServe.setBounds(233, 555, 97, 25);
		getContentPane().add(btnServe);

		lblMessage = new JLabel("Message");
		lblMessage.setFont(new Font(TAHOMA, Font.BOLD, 14));
		lblMessage.setBounds(37, 593, 489, 29);
		lblMessage.setForeground(Color.RED);
		lblMessage.setVisible(false);
		getContentPane().add(lblMessage);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Order) {
			Order order = (Order) arg;
			System.out.println("ChefObserver: New order was added -> " + order);
			for (MenuItem item : order.getOrderedItems()) {
				addTableOrder(
						new Object[] { ((BaseProduct) item).getDescription(), order.getTableId(), order.getId() });
			}
		}
	}

	public int getTableRowIndex() {
		return table.getSelectedRow();
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

	public void removeTableRow(int rowIndex) {
		tableModel.removeRow(rowIndex);
	}

	public void addTableActionListener(ListSelectionListener listSelectionListener) {
		table.getSelectionModel().addListSelectionListener(listSelectionListener);
	}

	public void addServeButtonActionListener(ActionListener actionListener) {
		btnServe.addActionListener(actionListener);
	}

	public void addTableOrder(Object[] rowData) {
		tableModel.addRow(rowData);
	}

	public void displayMessage(String message) {
		Runnable runnable = () -> {
			lblMessage.setText(message);
			lblMessage.setVisible(true);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				lblMessage.setVisible(false);
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
