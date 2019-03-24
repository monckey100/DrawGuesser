import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FinishScreen {

	private JFrame frame;
	private JLabel lblUserName;
	private JLabel lblLevel;
	private JLabel lblXP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FinishScreen window = new FinishScreen();
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
	public FinishScreen() {
		initialize();
		DisplayUsersInformation();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 801, 713);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblUserName = new JLabel("User Name here");
		lblUserName.setBounds(12, 93, 115, 16);
		frame.getContentPane().add(lblUserName);
		
		lblLevel = new JLabel("Their current level");
		lblLevel.setBounds(12, 122, 153, 16);
		frame.getContentPane().add(lblLevel);
		
		lblXP = new JLabel("Their current Xp");
		lblXP.setBounds(12, 151, 99, 16);
		frame.getContentPane().add(lblXP);
		
		JLabel lblNameHere = new JLabel("Guessing game ");
		lblNameHere.setFont(new Font("Algerian", Font.PLAIN, 30));
		lblNameHere.setBounds(286, 13, 300, 66);
		frame.getContentPane().add(lblNameHere);
		
		JLabel lblTotalGuessFor = new JLabel("Total Guesses for this image");
		lblTotalGuessFor.setBounds(127, 216, 215, 38);
		frame.getContentPane().add(lblTotalGuessFor);
		
		JLabel lblNumberOfCorrect = new JLabel("Number of correct guesses");
		lblNumberOfCorrect.setBounds(127, 277, 215, 38);
		frame.getContentPane().add(lblNumberOfCorrect);
		
		JLabel lblTotalGuesses = new JLabel("New label");
		lblTotalGuesses.setBounds(460, 227, 56, 16);
		frame.getContentPane().add(lblTotalGuesses);
		
		JLabel lblCorrectGuesses = new JLabel("New label");
		lblCorrectGuesses.setBounds(460, 288, 56, 16);
		frame.getContentPane().add(lblCorrectGuesses);
		
		JButton btnReturnHomePage = new JButton("Return to home page");
		btnReturnHomePage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				HomePage homePage = new HomePage();
				homePage.Home();
			}
		});
		btnReturnHomePage.setBounds(271, 473, 201, 54);
		frame.getContentPane().add(btnReturnHomePage);
	}
	private void DisplayUsersInformation() {
		// Variables that will hold user's username, level and xp
		String[] userInfor = Client.getNeededInfor("USER_INFO");
		// Get information from the database
		//
		//System.out.print(userInfor[2]);
		//System.out.print(userInfor[3]);
		// Display user's information
		lblUserName.setText("Name: " +userInfor[1]);
		lblLevel.setText("Current level: "+userInfor[2]);
		lblXP.setText("Current Xp: "+userInfor[3]);
		

	}

}
