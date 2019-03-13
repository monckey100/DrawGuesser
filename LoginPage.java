import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class LoginPage {

	private JFrame frame;
	private JTextField textFieldUserName;
	private JPasswordField passwordFieldSignIn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		run();
	}
	
	public static void run() {
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
		
		JLabel lblNameOfThe = new JLabel("Guessing game ");
		lblNameOfThe.setFont(new Font("Algerian", Font.PLAIN, 30));
		lblNameOfThe.setBounds(286, 13, 300, 66);
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
		
		
		// Button Onlick Listener
		// Login button event handler
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Check whether the entered username and password is correct or not
				if(passwordChecking()) {
					// Direct to home page screen
					HomePage homePage = new HomePage();
					homePage.Home();
					// Close previous screen
					frame.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, " Incorrect username or password.\n Please try again");
				}
			}
		});
		
		// Sign up button event handler
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUpPage signUp = new SignUpPage();
				// Redirect to sign up screen
				signUp.SignUp();
				// Close previous screen
				frame.dispose();
			}
		});
	}
	
	Client clientHandling = new Client();
	private boolean passwordChecking() {
		// Variable that will determine whether the user name and password are valid or not
		boolean check = false;
		// Variables that hold username and password
		String loginUserName = textFieldUserName.getText();
		String loginPassword = "";
		// Variables that hold username and password in the database
		
		// Retrieve password
		char[] password = passwordFieldSignIn.getPassword();
		// Convert char array to string
		for(char values : password) {
			loginPassword += values;
		}
		// Compare the user name and password with those in the database
		// If not exists in the database then return false
		// check username
		if (clientHandling.getDatabaseData(loginUserName, "userName").trim().isEmpty())
			return check;
		else {
			// if username exist then check password
			if (!clientHandling.getDatabaseData(loginPassword, "password").trim().isEmpty())
				return check = true;
			else
				return check;
		}
	}
}
