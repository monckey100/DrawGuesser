import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseMotionAdapter;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class GuessingPage {

	private JFrame frame;
	private JLabel lblUserName;
	private JLabel lblLevel;
	private JLabel lblXP;
	private JTextField textFieldGuessing;
	public int time;
	/**
	 * Launch the application.
	 */
	public static void main( ) {
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
	
	public GuessingPage(String diffLevel) {
		getClass() ;
		getTime(diffLevel);		
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
		textFieldGuessing.setBounds(87, 457, 319, 51);
		frame.getContentPane().add(textFieldGuessing);
		textFieldGuessing.setColumns(10);
		
		JButton btnGuessing = new JButton("Answer");
		btnGuessing.setBounds(460, 457, 147, 51);
		frame.getContentPane().add(btnGuessing);
		
		JLabel lblNameHere = new JLabel("Guessing game ");
		lblNameHere.setFont(new Font("Algerian", Font.PLAIN, 30));
		lblNameHere.setBounds(286, 13, 300, 66);
		frame.getContentPane().add(lblNameHere);
		
		lblUserName = new JLabel("User Name here");
		lblUserName.setBounds(12, 76, 115, 16);
		frame.getContentPane().add(lblUserName);
		
		lblLevel = new JLabel("Their current level");
		lblLevel.setBounds(12, 105, 153, 16);
		frame.getContentPane().add(lblLevel);
		
		lblXP = new JLabel("Their current Xp");
		lblXP.setBounds(12, 134, 99, 16);
		frame.getContentPane().add(lblXP);
		DisplayUsersInformation();
		JLabel Countdown = new JLabel("Ready?");
		Countdown.setBounds(22, 163, 111, 33);
		frame.getContentPane().add(Countdown);
		frame.getContentPane().addMouseListener(new MouseAdapter() {
					
			
			Timer t;
	    	int sec =time;
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.getContentPane().removeMouseListener(this);
	    		t =new Timer();
	    		t.scheduleAtFixedRate(new TimerTask() {
					int i = 180;
					@Override
					public void run() {
						Countdown.setForeground(Color.RED);
						Countdown.setText(""+(i--));
						 if(i<0)
						 {
							 
							 t.cancel();
							 JOptionPane.showMessageDialog(null, "Times up");
							 Countdown.setText("Times Over");
							 textFieldGuessing.disable();
						 }
					}
				}, 0, 1000);
	    		
	    		
	    	}
		});
		
		
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
	public void getTime(String diffLevel)
	{
		switch(diffLevel)
		{
		case "Easy" :
			time =180;
			break;
		case "Hard" :
			time =60;
			break;
		case "Intermediate" :
			time =120;
			break;
		
		
		}
	}


}
