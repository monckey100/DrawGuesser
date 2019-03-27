import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class SignUpPage {

	private JFrame frame;
	private JTextField textFieldUserNameSignUp;
	private JPasswordField passwordFieldSignUp;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldEmail;

	/**
	 * Launch the application.
	 */
	public static void SignUp() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpPage window = new SignUpPage();
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
	public SignUpPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 830, 758);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Guessing game ");
		lblNewLabel.setFont(new Font("Algerian", Font.PLAIN, 30));
		lblNewLabel.setBounds(286, 13, 300, 66);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Enter your user name");
		lblNewLabel_1.setBounds(102, 402, 127, 35);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Enter your password");
		lblNewLabel_2.setBounds(102, 475, 127, 35);
		frame.getContentPane().add(lblNewLabel_2);
		
		textFieldUserNameSignUp = new JTextField();
		textFieldUserNameSignUp.setBounds(280, 400, 306, 38);
		frame.getContentPane().add(textFieldUserNameSignUp);
		textFieldUserNameSignUp.setColumns(10);
		
		passwordFieldSignUp = new JPasswordField();
		passwordFieldSignUp.setBounds(280, 473, 306, 38);
		frame.getContentPane().add(passwordFieldSignUp);
		
		JButton btnConfirmSignUp = new JButton("Confirm");
		
		btnConfirmSignUp.setBounds(102, 611, 140, 38);
		frame.getContentPane().add(btnConfirmSignUp);
		
		JButton btnCancel = new JButton("Cancel");
		

		btnCancel.setBounds(459, 611, 140, 38);
		frame.getContentPane().add(btnCancel);
		
		JLabel lblNewLabel_3 = new JLabel("Enter your email");
		lblNewLabel_3.setBounds(102, 338, 127, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Enter your last name");
		lblNewLabel_4.setBounds(102, 272, 127, 16);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Enter your first name");
		lblNewLabel_5.setBounds(102, 195, 127, 16);
		frame.getContentPane().add(lblNewLabel_5);
		
		textFieldFirstName = new JTextField();
		textFieldFirstName.setBounds(280, 184, 306, 38);
		frame.getContentPane().add(textFieldFirstName);
		textFieldFirstName.setColumns(10);
		
		textFieldLastName = new JTextField();
		textFieldLastName.setBounds(280, 261, 306, 38);
		frame.getContentPane().add(textFieldLastName);
		textFieldLastName.setColumns(10);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(280, 327, 306, 38);
		frame.getContentPane().add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JButton btnReset = new JButton("Reset");
		
		btnReset.setBounds(279, 611, 140, 38);
		frame.getContentPane().add(btnReset);
		// Confirm button event handler
		btnConfirmSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// No empty input allow
				if (emptyChecking()) {
					JOptionPane.showMessageDialog(null, " Unvalid input.\n Please try again");
				}
				else {
					// Update the user's data to the database
					if(signUpPasswordChecking()) {
						JOptionPane.showMessageDialog(null, " Successfully add your information in the database !");
						frame.dispose();
						LoginPage loginPage = new LoginPage();
						loginPage.run();
						
					}
					// Existing data inside the database
					else {						
						JOptionPane.showMessageDialog(null, " Existing email or username .\n Please try again");
					}
				}
			}
		});
		// Reset button event handler
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Clear all the text fields
				textFieldFirstName.setText("");
				textFieldLastName.setText("");
				textFieldEmail.setText("");
				textFieldUserNameSignUp.setText("");
				passwordFieldSignUp.setText("");
			}
		});
		// Cancel button event handler
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Redirect to login page
				LoginPage loginPage = new LoginPage();
				loginPage.run();
				frame.dispose();
			}
		});
	}
	
	
	
	private boolean signUpPasswordChecking() {
		// Variable that will determine whether the email, username and password are valid or not
		String firstName = textFieldFirstName.getText();
		String lastName = textFieldLastName.getText();
		String userName = textFieldUserNameSignUp.getText();
		String email = textFieldEmail.getText();
		String signUpUserName = textFieldUserNameSignUp.getText();
		String signUpPassword = "";
		
		// Retrieve password
		char[] password = passwordFieldSignUp.getPassword();
		// Convert char array to string
		for(char values : password) {
			signUpPassword += values;
		}
		// Compare the email, username and password with those in the database
		// If exists in the database then return false 
		// If not then return true
		
		return Client.signup(userName, email, signUpPassword, firstName, lastName);
	}
	
	private boolean emptyChecking() {
		// Variable that will help determine empty fields
		boolean check = false;
		if( textFieldFirstName.getText().isEmpty() ||
			textFieldLastName.getText().isEmpty()  ||
			textFieldEmail.getText().isEmpty() ||
			textFieldUserNameSignUp.getText().isEmpty() ||
			(passwordFieldSignUp.getPassword().equals(null))
		  )
		{
			check = true;
		}
	
			
		
		
		return check;
	}
}
