import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HomePage {

	private JFrame frame;
	private JLabel lblUserName;
	private JLabel lblLevel;
	private JLabel lblXP;
	private JComboBox comboBoxGameMode;
	private JComboBox comboBoxCategory;
	private JComboBox comboBoxDifficultLevel;
	private JButton btnPlay;

	// Variables that hold difficult level and word category
	public static String difficultLevel;
	public static String categoryName;
	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public static void Home() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage window = new HomePage();		
					window.initialize();
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


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 708, 605);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Guessing game ");
		lblNewLabel.setFont(new Font("Algerian", Font.PLAIN, 30));
		lblNewLabel.setBounds(286, 13, 300, 66);
		frame.getContentPane().add(lblNewLabel);
		
		lblUserName = new JLabel("User Name here");
		lblUserName.setBounds(12, 61, 254, 16);
		frame.getContentPane().add(lblUserName);
		
		lblLevel = new JLabel("Their current level");
		lblLevel.setBounds(12, 90, 254, 16);
		frame.getContentPane().add(lblLevel);
		
		lblXP = new JLabel("Their current Xp");
		lblXP.setBounds(12, 119, 254, 16);
		frame.getContentPane().add(lblXP);
		
		comboBoxGameMode = new JComboBox();
		comboBoxGameMode.setBounds(322, 197, 168, 38);
		frame.getContentPane().add(comboBoxGameMode);
		
		JLabel lblNewLabel_1 = new JLabel("Game Mode");
		lblNewLabel_1.setBounds(167, 197, 99, 38);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Difficulty Level");
		lblNewLabel_2.setBounds(167, 352, 99, 38);
		frame.getContentPane().add(lblNewLabel_2);
		
		comboBoxDifficultLevel = new JComboBox();
		comboBoxDifficultLevel.setBounds(322, 352, 168, 38);
		frame.getContentPane().add(comboBoxDifficultLevel);
		
		btnPlay = new JButton("Let's Play");
	
		btnPlay.setBounds(193, 467, 97, 25);
		frame.getContentPane().add(btnPlay);
		
		JButton btnLogOff = new JButton("Log off");
		
		btnLogOff.setBounds(381, 467, 97, 25);
		frame.getContentPane().add(btnLogOff);
		
		comboBoxCategory = new JComboBox();
		comboBoxCategory.setBounds(322, 267, 168, 38);
		frame.getContentPane().add(comboBoxCategory);
		
		JLabel lblWordCategory = new JLabel("Word Category");
		lblWordCategory.setBounds(167, 267, 99, 38);
		frame.getContentPane().add(lblWordCategory);
		
		// Play button event handler
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String gameMode = comboBoxGameMode.getSelectedItem().toString();
				difficultLevel = comboBoxDifficultLevel.getSelectedItem().toString();
				categoryName = comboBoxCategory.getSelectedItem().toString();
		//		DrawingPage draw = new DrawingPage("sthg");
		//		draw.main(new String[] {"sthg"});
		//		frame.dispose();
				// Send the request to the server with these key words
				// Processing with the database
				//
				
				// After that navigate to appropriate mode

				

				if(gameMode.equals("Drawing")) {
					DrawingPage draw = new DrawingPage(difficultLevel);
							draw.main(new String[] {"sthg"});
							frame.dispose();
				}
				else {

					GuessingPage guess = new GuessingPage();
					guess.main(new String[] {"sthg"});
					frame.dispose();

					
					
				}
				
			}
		});
		// Log off button
		btnLogOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage login = new LoginPage();
				login.run();
				frame.dispose();
			}
		});
		
		InitializeComboBox();
		
		DisplayUsersInformation();
	}
	private void InitializeComboBox() {
		String[] difficultMode= Client.getNeededInfor("DIFFICULT_LEVEL");
		// Get combobox selection to determine time period
		String[] timePeriod = Client.getNeededInfor("TIME_PERIOD", "Hard");
		// Get word category name
		String[] wordCategory = Client.getNeededInfor("WORD_CATEGORY");
		// Combo box for game mode
		comboBoxGameMode.addItem("Drawing");
		comboBoxGameMode.addItem("Guessing");
		// Combo box for difficult level

		for ( int i = 1 ; i < difficultMode.length; i++) {
			comboBoxDifficultLevel.addItem(difficultMode[i]);
		}
		// Just to test retrieved time period from the db
		System.out.println("This is the current time period:  "+timePeriod[1]);
		
		for( int i = 1; i < wordCategory.length ; i++) {
			comboBoxCategory.addItem(wordCategory[i]);
		}
		// Combo box for category
		// Get the word category from the database
		// Set the combobox
		//
		
		
		
	}
	
	private void DisplayUsersInformation() {
		// Variables that will hold user's username, level and xp
		String[] userInfor = Client.getNeededInfor("USER_INFO");

		// Display user's information

		lblUserName.setText("Name: " +userInfor[2]);
		lblLevel.setText("Current level: "+userInfor[3]);
		lblXP.setText("Current Xp: "+userInfor[4]);

	}
}
