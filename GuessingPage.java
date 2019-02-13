import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class GuessingPage {

	private JFrame frame;
	private JTextField textFieldGuessing;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuessingPage window = new GuessingPage();
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
	public GuessingPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 738, 669);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textFieldGuessing = new JTextField();
		textFieldGuessing.setBounds(52, 458, 319, 51);
		frame.getContentPane().add(textFieldGuessing);
		textFieldGuessing.setColumns(10);
		
		JButton btnGuessing = new JButton("New button");
		btnGuessing.setBounds(460, 457, 147, 51);
		frame.getContentPane().add(btnGuessing);
		
		JLabel lblNameHere = new JLabel("name here");
		lblNameHere.setBounds(289, 26, 99, 16);
		frame.getContentPane().add(lblNameHere);
		
		JLabel labelUserName = new JLabel("User Name here");
		labelUserName.setBounds(12, 76, 115, 16);
		frame.getContentPane().add(labelUserName);
		
		JLabel labelLevel = new JLabel("Their current level");
		labelLevel.setBounds(12, 105, 153, 16);
		frame.getContentPane().add(labelLevel);
		
		JLabel labelXP = new JLabel("Their current Xp");
		labelXP.setBounds(12, 134, 99, 16);
		frame.getContentPane().add(labelXP);
	}

}
