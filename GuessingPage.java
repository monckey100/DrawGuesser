import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class GuessingPage {

	private JFrame frame;
	private JTextField textFieldGuessing;

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
		
		JLabel labelUserName = new JLabel("User Name here");
		labelUserName.setBounds(12, 76, 115, 16);
		frame.getContentPane().add(labelUserName);
		
		JLabel labelLevel = new JLabel("Their current level");
		labelLevel.setBounds(12, 105, 153, 16);
		frame.getContentPane().add(labelLevel);
		
		JLabel labelXP = new JLabel("Their current Xp");
		labelXP.setBounds(12, 134, 99, 16);
		frame.getContentPane().add(labelXP);
		
		JLabel Countdown = new JLabel("Ready?");
		Countdown.setBounds(22, 163, 111, 33);
		frame.getContentPane().add(Countdown);
		frame.getContentPane().addMouseListener(new MouseAdapter() {
			Timer t;
	    	int sec =5;
			@Override
			public void mouseClicked(MouseEvent e) {
	    		t =new Timer(1000,new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Countdown.setForeground(Color.RED);
						if(sec<1)
						{
							JOptionPane.showMessageDialog(null, "Times up");
							t.stop();
							Countdown.setText("Times Over");
							textFieldGuessing.disable();
				
						}else
						{
							sec--;
							Countdown.setText(" "+sec);
						}										
					}
					
	    		});
	    		frame.getContentPane().removeMouseListener(this);
	    		t.start();
	    		
	    	}
		});
		
		
	}

}
