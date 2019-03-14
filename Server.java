
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
	   // Open connection to the database
	   JDBC2 jdbc = new JDBC2();
            while(true)
              {                       
                 String packet = myConn.getPackets();
                 if(packet != null) {
                	 System.out.println("RECEIVED: " + packet); 
                	 myConn.parsePacket(packet);
                	 //String dbResult = jdbc.getResult(requestArray[0],requestArray[1]);
                	 if(myConn.getType() == "LOGIN") {
                		 //We assume getData will be {username,password}
	                	 myConn.setType("LOGIN");
	                	 String username = myConn.getData()[0];
	                	 String password = myConn.getData()[1];
	                	 String[] sendInfo = null;
	                	 /*
	                	 if(jdbc.checkLogin(username,password)) {
	                		 sendInfo[0] = "Success";
	                		 sendInfo[1] = jdbc.getEXP(username);
	                		 sendInfo[2] = jdbc.getLevel(username);
	                	 } else {
	                		 sendInfo[0] = "Failed";
	                	 }*/
	                	 //myConn.setData(new String[]{"My","Data"});
	                	 myConn.setData(sendInfo);
                	 } else {
                		 myConn.setType("ERROR");
                		 myConn.setData(new String[] {"Error: Could not find Datatype!"});
                	 }
                	 //Sends raw data how we want it to.
                	 //myConn.sendPacket(dbResult);
                	 
                	 //Sends setType + setData to server.
                	 myConn.sendPacket(myConn.packageData());
                  }

               }
      }
   

}
class connectionHandler {
	DatagramSocket serverSocket;
	InetAddress IPAddress;
	int port;
	String[] requestArray;
	String datatype;
	String[] datastuff;
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
	public void parsePacket(String mPacket) {
		 requestArray = mPacket.split(",");
	}
	public String getType() {
		return requestArray[0];
	}
	public String[] getData() {
		//remove first character [
		String m = requestArray[1].substring(1); 
		//remove last character ]
		m = m.substring(0, m.length() - 1);
		return m.split(","); 
	}
	//Sending to client
	public void setType(String type) {
		datatype = type;
	}
	public void setData(String[] data) {
		datastuff = data;
	}
	public String packageData() {
		return datatype + "," + datastuff.toString();
	}
}
