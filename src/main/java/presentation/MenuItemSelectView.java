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

public class MenuItemSelectView extends JFrame {
	private static final long serialVersionUID = -8708167244075957830L;

	private static final String TAHOMA = "Tahoma";
	private static final int WINDOW_WIDTH = 570;
	private static final int WINDOW_HEIGHT = 670;
	private static final Object[] COLUMN_HEADERS = new Object[] { "Menu item", "Price" };

	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel lblMessage;
	private JButton btnSelect;
	private JButton btnCancel;

	public MenuItemSelectView(Dimension screenSize) {

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds((int) (screenSize.getWidth() / 2.0) - (int) (WINDOW_WIDTH / 2.0),
				(int) (screenSize.getHeight() / 2.0) - (int) (WINDOW_HEIGHT / 2.0), WINDOW_WIDTH, WINDOW_HEIGHT);
		getContentPane().setLayout(null);
		this.setResizable(false);
		this.setTitle("Menu Item selector");
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		tableModel = new DefaultTableModel(COLUMN_HEADERS, 0) {
			private static final long serialVersionUID = -1863105129332870806L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return col == 2;
			}
		};
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setShowVerticalLines(true);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(37, 32, 489, 481);
		getContentPane().add(scrollPane);

		btnSelect = new JButton("Select");
		btnSelect.setBounds(123, 555, 97, 25);
		getContentPane().add(btnSelect);

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(343, 555, 97, 25);
		getContentPane().add(btnCancel);

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

	public void addTableActionListener(ListSelectionListener listSelectionListener) {
		table.getSelectionModel().addListSelectionListener(listSelectionListener);
	}

	public void addCancelButtonActionListener(ActionListener actionListener) {
		btnCancel.addActionListener(actionListener);
	}

	public void addSelectButtonActionListener(ActionListener actionListener) {
		btnSelect.addActionListener(actionListener);
	}

	public int getTableRowIndex() {
		return table.getSelectedRow();
	}

	public void setTableRowAtIndex(int rowIndex, Object[] rowData) {
		for (int i = 0; i < rowData.length; i++) {
			tableModel.setValueAt(rowData[i], rowIndex, i);
		}
	}

	public void addTableMenuItem(Object[] rowData) {
		tableModel.addRow(rowData);
	}

	public void removeTableRow(int rowIndex) {
		tableModel.removeRow(rowIndex);
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

	public void clearTable() {
		tableModel.setDataVector(null, COLUMN_HEADERS);
	}
}
