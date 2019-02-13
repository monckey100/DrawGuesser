import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	public static void main(String[] args) {
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
		
		JLabel lblNewLabel = new JLabel("name here");
		lblNewLabel.setBounds(313, 33, 122, 16);
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
	}
}
