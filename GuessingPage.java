import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
	String[] timePeriod;
	public int time;
	String[] image;
	static String diffLevel;
	
	/**
	 * Launch the application.
	 */
	
	
	
	public static void main(String[] args ) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuessingPage window = new GuessingPage(diffLevel);
					
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

	public GuessingPage(String diffLevel) {
		getTime(diffLevel);
		initialize();
		
		
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		
		
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textFieldGuessing = new JTextField();
		textFieldGuessing.setBounds(87, 700, 319, 51);
		frame.getContentPane().add(textFieldGuessing);
		textFieldGuessing.setColumns(10);
		
		JButton btnGuessing = new JButton("Answer");
		btnGuessing.setBounds(460, 700, 147, 51);
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
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.getContentPane().removeMouseListener(this);
	    		t =new Timer();
	    		t.scheduleAtFixedRate(new TimerTask() {
					int i = time;
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
							 frame.dispose();
							 FinishScreen fs = new FinishScreen();
							 fs.main(null);
						 }
					}
				}, 0, 1000);
	    		
	    		
	    	}
		});
		String[] imageInfo = getImageAndAnsw();
		convertToImage(imageInfo[2]);
		// Use to display the image
        JLabel lbl=new JLabel();
		lbl.setBounds(110, 200, 700, 500);
		// Get current directory where the image is stored
		String path  = System.getProperty("user.dir");

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path + "//translatedImage.jpeg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon icon=new ImageIcon(img);

		lbl.setIcon(icon);
		frame.getContentPane().add(lbl);
       

	// Event handler when user click guess
		btnGuessing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Get user's answer
				String userAnswer = textFieldGuessing.getText();
				// Get correction answer and drawing id
				//String[] imageInfo = getImageCode();
				// Get user ID
				String[] userID= Client.getNeededInfor("USER_INFO");
				// Compare correct answer
				System.out.print("Answre is "+userAnswer + " "+imageInfo[1]);
				if( userAnswer.toLowerCase().equals(imageInfo[1].toLowerCase())) { 
					JOptionPane.showMessageDialog(null, "That answer is correct !");
					
					//DifficultyLevel,DrawingID,UserID,SucceedTimes,TotalTime
					Client.insertGuess(HomePage.difficultLevel,imageInfo[3],userID[1],"1","1");
					Client.updatePoint(userID[1], HomePage.difficultLevel);
					//
					//Client.insertCorrectGuess(userID[1],imageInfo[3]);
					FinishScreen fn = new FinishScreen();
					fn.main(new String[] {"sthg"});
					frame.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Wrong answer!/nPlease Try Again");
					Client.insertGuess(HomePage.difficultLevel,imageInfo[3],userID[1],"0","1");
					FinishScreen fn = new FinishScreen();
					fn.main(new String[] {"sthg"});
					frame.dispose();
					
				}
				
			}
		});
		
		
	}

	public static void convertToImage(String imageCode) {
	
		PadDraw.ByteToImage(imageCode, "translatedImage.jpeg");
	}
	// Get image and answer array
	public static String[] getImageAndAnsw() {
	//  [1]- correct answer
	// 	[2]- image encoded
	//  [3]- drawing ID

		String[] userID= Client.getNeededInfor("USER_INFO");		
		String[] image = Client.getNeededInfor("GET_IMAGE", userID[1],HomePage.categoryName);
		return image;
	}
	private void DisplayUsersInformation() {
		// Variables that will hold user's username, level and xp
		String[] userInfor = Client.getNeededInfor("USER_INFO");
		// Get information from the database
		
		lblUserName.setText("Name: " +userInfor[2]);
		lblLevel.setText("Current level: "+userInfor[3]);
		lblXP.setText("Current Xp: "+userInfor[4]);
		
		

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
