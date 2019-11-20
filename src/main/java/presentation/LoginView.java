package presentation;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LoginView extends JFrame {
	private static final long serialVersionUID = 7505197291928938776L;

	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 320;
	private static final String TAHOMA = "Tahoma";

	private JButton btnChef;
	private JButton btnWaiter;
	private JButton btnAdministrator;

	public LoginView(Dimension screenSize) {

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds((int) (screenSize.getWidth() / 2.0) - (int) (WINDOW_WIDTH / 2.0),
				(int) (screenSize.getHeight() / 2.0) - (int) (WINDOW_HEIGHT / 2.0), WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setResizable(false);
		this.setTitle("Restaurant manager");
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		getContentPane().setLayout(null);

		JLabel lblRestaurantManagementApp = new JLabel("Welcome");
		lblRestaurantManagementApp.setHorizontalAlignment(SwingConstants.CENTER);
		lblRestaurantManagementApp.setFont(new Font(TAHOMA, Font.BOLD, 22));
		lblRestaurantManagementApp.setBounds(12, 35, 270, 38);
		getContentPane().add(lblRestaurantManagementApp);

		btnAdministrator = new JButton("Administrator");
		btnAdministrator.setBounds(84, 117, 125, 25);
		getContentPane().add(btnAdministrator);

		btnWaiter = new JButton("Waiter");
		btnWaiter.setBounds(84, 165, 125, 25);
		getContentPane().add(btnWaiter);

		btnChef = new JButton("Chef");
		btnChef.setBounds(84, 211, 125, 25);
		getContentPane().add(btnChef);
	}

	public void addAdministratorLoginButtonActionListener(ActionListener actionListener) {
		btnAdministrator.addActionListener(actionListener);
	}

	public void addWaiterLoginButtonActionListener(ActionListener actionListener) {
		btnWaiter.addActionListener(actionListener);
	}

	public void addChefLoginButtonActionListener(ActionListener actionListener) {
		btnChef.addActionListener(actionListener);
	}
}
