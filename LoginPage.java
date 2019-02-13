import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPage {

	private JFrame frame;
	private JTextField textFieldUserName;
	private JPasswordField passwordFieldSignIn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 845, 709);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNameOfThe = new JLabel("Name of the game will be changed later");
		lblNameOfThe.setBounds(292, 61, 370, 16);
		frame.getContentPane().add(lblNameOfThe);
		
		JLabel lblNewLabel = new JLabel("User Name");
		lblNewLabel.setBounds(175, 216, 80, 35);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(175, 283, 80, 35);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnLogin = new JButton("Login");
	
		btnLogin.setBounds(282, 408, 97, 25);
		frame.getContentPane().add(btnLogin);
		
		JButton btnSignUp = new JButton("Sign Up");
		
		btnSignUp.setBounds(476, 408, 97, 25);
		frame.getContentPane().add(btnSignUp);
		
		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(282, 214, 291, 38);
		frame.getContentPane().add(textFieldUserName);
		textFieldUserName.setColumns(10);
		
		passwordFieldSignIn = new JPasswordField();
		passwordFieldSignIn.setBounds(282, 281, 291, 38);
		frame.getContentPane().add(passwordFieldSignIn);
	}
}
