
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Server
{
   public static void main(String args[]) throws Exception
      {
	   connectionHandler myConn = new connectionHandler();
	   // OPen connection to the database
	   JDBC2 jdbc = new JDBC2();
            while(true)
              {                       
                 String sentence = myConn.getPackets();
                 if(sentence != null) {
                	 System.out.println("RECEIVED: " + sentence); 
                	 // Get request and request type
                	 String[] requestArray = sentence.split(",");
                	 
                	 String dbResult = jdbc.getResult(requestArray[0],requestArray[1]);
                	 myConn.sendPacket(dbResult);
                  }

               }
      }
   

}
class connectionHandler {
	DatagramSocket serverSocket;
	InetAddress IPAddress;
	int port;
	public connectionHandler() {
		 try {
			serverSocket = new DatagramSocket(9876);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getPackets() {
		//INIT Variables
		String myPacket = null;
		byte[] receiveData = new byte[1024];
		
		//Packet received
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			serverSocket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Convert Bytes into String
		myPacket = new String( receivePacket.getData());
		
		//Set Return IP Address & Port
		IPAddress = receivePacket.getAddress();
		port = receivePacket.getPort();
		return myPacket;
	}
	public boolean sendPacket(String myPacket) {
		byte[] sendData = new byte[1024];
		
		//Convert to bytes
		sendData = myPacket.getBytes();
		
        DatagramPacket sendPacket =
        new DatagramPacket(sendData, sendData.length, IPAddress, port);
        try {
			serverSocket.send(sendPacket);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
