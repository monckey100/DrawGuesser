    import java.awt.Graphics;
    import java.awt.*;
    import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.text.*;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


    public class DrawingPage{
    	static int time;
    	public static String[] q;
        // Get random word from the database 
    	public static String[] wordArray () {
    		  String[] word= Client.getNeededInfor("GET_WORD", HomePage.categoryName);
    		  return word;
    	}
       

    	

     public static void main(String[] args){     

    JFrame frame = new JFrame("Draw Something");    

    Container content = frame.getContentPane();
    frame.getContentPane().setLayout(null);
    
    JLabel lblTopic = new JLabel("Topic :");
    lblTopic.setBounds(10, 79, 188, 41);
    frame.getContentPane().add(lblTopic);

     PadDraw drawPad = new PadDraw();      
    drawPad.setBounds(12, 133, 577, 443);


    content.add(drawPad);

    

    

    JPanel panel = new JPanel();
    panel.setBounds(0, 0, 482, 68);

    panel.setPreferredSize(new Dimension(100, 68));
    panel.setMinimumSize(new Dimension(100, 68));
    panel.setMaximumSize(new Dimension(100, 68));

            JButton twoX = new JButton ("2");
            twoX.addActionListener(new ActionListener(){

                public void actionPerformed(ActionEvent e){
            drawPad.clear();
        }
    });             


    JButton yellowButton = new JButton("YELLOW");
    yellowButton.setForeground(new Color(204, 204, 0));

    yellowButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            drawPad.yellow();
        }

    });


    JButton greenButton = new JButton("GREEN");
    greenButton.setForeground(new Color(0, 204, 0));

    greenButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            drawPad.green();
        }
    });

    JButton redButton = new JButton("RED");
    redButton.setForeground(new Color(255, 0, 0));

    redButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            drawPad.red();
        }
    });

    JButton blueButton = new JButton("BLUE");
    blueButton.setForeground(Color.BLUE);

    blueButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            drawPad.blue();
        }
    });

    JButton blackButton = new JButton("BLACK");

    blackButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            drawPad.black();
        }
    });
    
    JButton sendButton = new JButton("SEND");

    sendButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	//We can use the JPEG if we want to save user drawings or 
        	//whatever in the future without referring to DB.
        	//drawPad.saveComponentAsJPEG(drawPad,"C:/test.jpg");
        	//Send byte to server for storage.
        	
        	String myBytes = drawPad.saveComponentAsByte(drawPad);
        	
        	//Image is sent to server.
        	drawPad.sendImage(myBytes);
        	
        	//drawPad.ByteToImage(myBytes, "C:/translated.jpg");
        //	JOptionPane.showMessageDialog(null, "The Drawing Sent");
        	HomePage homePage = new HomePage();
			homePage.Home();
			// Close previous screen
			frame.dispose();
        }
    });
    greenButton.setPreferredSize(new Dimension(80, 20));
    redButton.setPreferredSize(new Dimension(80, 20));
    yellowButton.setPreferredSize(new Dimension(80, 20));
    blueButton.setPreferredSize(new Dimension(80, 20));
    blackButton.setPreferredSize(new Dimension(80,20));
    sendButton.setPreferredSize(new Dimension(100,20));


    panel.add(blackButton);
    panel.add(blueButton);
    panel.add(redButton);
    panel.add(greenButton);
    panel.add(yellowButton);
    
    JLabel Countdown = new JLabel("Ready?");
    panel.add(Countdown);
    panel.add(sendButton);
    
    drawPad.addMouseListener(new MouseAdapter() {
    	Timer t;
    	int sec =time;
    	@Override
    	public void mousePressed(MouseEvent e) {
    		t =new Timer(1000,new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Countdown.setForeground(Color.RED);
					if(sec<1)
					{
						JOptionPane.showMessageDialog(null, "Times up");
						t.stop();
						Countdown.setText("Times Over");
						content.remove(drawPad);
						String myBytes = drawPad.saveComponentAsByte(drawPad);
			        	
			        	//Image is sent to server.
			        	drawPad.sendImage(myBytes);

			        	JOptionPane.showMessageDialog(null, "Add your image successfully");
			        	HomePage home = new HomePage();
			        	home.Home();
			        	frame.dispose();
					
						
					}else
					{
						sec--;
						Countdown.setText(" "+sec);
					}										
				}
				
    		});
    		drawPad.removeMouseListener(this);
    		
    		t.start();
    		
    	}
    	
    });



    content.add(panel);

    JRadioButton rdbtnPx = new JRadioButton("3 px");
    panel.add(rdbtnPx);

    JRadioButton rdbtnPx_1 = new JRadioButton("5 px");
    panel.add(rdbtnPx_1);

    JRadioButton rdbtnPx_2 = new JRadioButton("12 px");
    panel.add(rdbtnPx_2);


    ButtonGroup bg = new ButtonGroup();
    bg.add(rdbtnPx);
    bg.add(rdbtnPx_1);
    bg.add(rdbtnPx_2);

    rdbtnPx.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            drawPad.small();
        }
    });
    rdbtnPx_1.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            drawPad.medium();
        }
    });
    rdbtnPx_2.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            drawPad.big();
        }
    });

    JButton clearButton = new JButton("Clear");
    clearButton.setBackground(new Color(255, 255, 255));
    clearButton.setFont(UIManager.getFont("TextArea.font"));

    clearButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            drawPad.clear();
        }
    });
    panel.add(clearButton);

    frame.setSize(677, 636);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setVisible(true);

    q = wordArray();
    // Display it out
    lblTopic.setText("Topic: "+ q[1]);

    


        }

        public DrawingPage(String diffLevel) {
    		getClass() ;
    		getTime(diffLevel);		
    	  		
    		
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


class PadDraw extends JComponent{

private Image image;    
private Graphics2D graphics2D;  
private int currentX , currentY , oldX , oldY ;

public PadDraw(){
    setDoubleBuffered(false);
    addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){
            oldX = e.getX();
            oldY = e.getY();
        }
    });

    addMouseMotionListener(new MouseMotionAdapter(){
        public void mouseDragged(MouseEvent e){
            currentX = e.getX();
            currentY = e.getY();
            if(graphics2D != null)
            graphics2D.drawLine(oldX, oldY, currentX, currentY);
            repaint();
            oldX = currentX;
            oldY = currentY;
        }

    });

}   

public void paintComponent(Graphics g){
    if(image == null){
        image = createImage(getSize().width, getSize().height);
        graphics2D = (Graphics2D)image.getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clear();

    }
    g.drawImage(image, 5, 5, null);
}

public void clear(){
    graphics2D.setPaint(Color.white);
    graphics2D.fillRect(0, 0, getSize().width, getSize().height);
    graphics2D.setPaint(Color.black);
    repaint();
}

public void red(){
    graphics2D.setPaint(Color.red);
    repaint();
}

public void black(){
    graphics2D.setPaint(Color.black);
    repaint();
}

public void yellow(){
    graphics2D.setPaint(Color.yellow);
    repaint();
}

public void blue(){
    graphics2D.setPaint(Color.blue);
    repaint();
}

public void green(){
    graphics2D.setPaint(Color.green);
    repaint();
}
public void small(){
    graphics2D.setStroke(new BasicStroke(1));;
}
public void medium(){
    graphics2D.setStroke(new BasicStroke(5));;
}
public void big(){
    graphics2D.setStroke(new BasicStroke(12));;
}


public void sendImage(String g) {
	
	//connect to server, convert to binary and send.
	String[] userID= Client.getNeededInfor("USER_INFO");
//	String[] wordArray = DrawingPage.wordArray();

//	System.out.println("LOL "+ g+" "+userID[1]+"  "+wordArray[2]+" "+wordArray[1]+" "+HomePage.difficultLevel);
	
	System.out.println("Encoded Length: "+ g.length());
	Client.imagesend(userID[1], DrawingPage.q[2], HomePage.difficultLevel,g);
}


public void saveComponentAsJPEG(Component myComponent, String filename) {
    Dimension size = myComponent.getSize();
    BufferedImage myImage = 
      new BufferedImage(size.width, size.height,
      BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = myImage.createGraphics();
    myComponent.paint(g2);
    try {
      OutputStream out = new FileOutputStream(filename);
      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
      encoder.encode(myImage);
      out.close();
    } catch (Exception e) {
      System.out.println(e); 
    }
  }
public String saveComponentAsByte(Component myComponent) {
    Dimension size = myComponent.getSize();
    BufferedImage myImage = 
      new BufferedImage(size.width, size.height,
      BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = myImage.createGraphics();
    myComponent.paint(g2);
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ImageIO.write(myImage, "jpeg", outputStream);
      String encodedImage = Base64.encode(outputStream.toByteArray());
      System.out.println(encodedImage);
      return encodedImage;
    } catch (Exception e) {
      System.out.println(e); 
    }
	return null; //something failed.
  }
public static void ByteToImage(String bytes, String filename) {
	String data = "data:image/jpeg;base64,"+bytes;
	String base64Image = data.split(",")[1];
	byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
	try {
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
	      OutputStream out = new FileOutputStream(filename);
	      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	      encoder.encode(img);
	      out.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


}