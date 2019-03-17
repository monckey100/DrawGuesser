import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;

public class FinishScreen {

	private JFrame frame;

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 801, 713);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel labelUserName = new JLabel("User Name here");
		labelUserName.setBounds(12, 93, 115, 16);
		frame.getContentPane().add(labelUserName);
		
		JLabel labelLevel = new JLabel("Their current level");
		labelLevel.setBounds(12, 122, 153, 16);
		frame.getContentPane().add(labelLevel);
		
		JLabel labelXP = new JLabel("Their current Xp");
		labelXP.setBounds(12, 151, 99, 16);
		frame.getContentPane().add(labelXP);
		
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
		btnReturnHomePage.setBounds(271, 473, 201, 54);
		frame.getContentPane().add(btnReturnHomePage);
	}

}
