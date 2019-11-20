package presentation;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JButton;

public class AddMenuItemView extends JFrame {

	private static final long serialVersionUID = -1943769432039881150L;
	
	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 320;
	private static final String TAHOMA = "Tahoma";

	private JButton btnSave;
	private JLabel lblMessage;
	private JTextField priceTextField;
	private JTextArea descriptionTextArea;

	public AddMenuItemView(Dimension screenSize) {

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds((int) (screenSize.getWidth() / 2.0) - (int) (WINDOW_WIDTH / 2.0),
				(int) (screenSize.getHeight() / 2.0) - (int) (WINDOW_HEIGHT / 2.0), WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setResizable(false);
		this.setTitle("Menu Item");
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		getContentPane().setLayout(null);

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font(TAHOMA, Font.BOLD, 13));
		lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescription.setBounds(107, 28, 80, 25);
		getContentPane().add(lblDescription);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font(TAHOMA, Font.BOLD, 13));
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setBounds(107, 121, 80, 25);
		getContentPane().add(lblPrice);

		priceTextField = new JTextField();
		priceTextField.setColumns(10);
		priceTextField.setBounds(89, 148, 116, 25);
		getContentPane().add(priceTextField);

		descriptionTextArea = new JTextArea();
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setFont(new Font(TAHOMA, Font.PLAIN, 13));
		descriptionTextArea.setTabSize(0);

		JScrollPane scrollPane = new JScrollPane(descriptionTextArea);
		scrollPane.setBounds(33, 58, 228, 50);
		getContentPane().add(scrollPane);

		btnSave = new JButton("Save");
		btnSave.setBounds(98, 216, 97, 25);
		getContentPane().add(btnSave);

		lblMessage = new JLabel("Error Message");
		lblMessage.setFont(new Font(TAHOMA, Font.BOLD, 13));
		lblMessage.setBounds(12, 264, 249, 16);
		lblMessage.setForeground(Color.RED);
		lblMessage.setVisible(false);
		getContentPane().add(lblMessage);
	}

	public String getDescription() {
		return descriptionTextArea.getText();
	}

	public void setDescription(String description) {
		descriptionTextArea.setText(description);
	}

	public String getPrice() {
		return priceTextField.getText();
	}

	public void setPrice(String price) {
		priceTextField.setText(price);
	}

	public void addSaveButtonActionListener(ActionListener actionListener) {
		btnSave.addActionListener(actionListener);
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
}
