
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
            while(true)
              {
            	
                
                myConn.getPackets();
            
                  String sentence = myConn.getPackets();
                  if(sentence != null) {
                	  System.out.println("RECEIVED: " + sentence);
                	  String[] requestArray = sentence.split(",");
                	  String dbResult = getDatabaseResult(requestArray[1],requestArray[0]);
                	  myConn.sendPacket(dbResult);
                  }
               }
      }
   
   public static String getDatabaseResult(String request,String typeOfRequest) {
	   Connection conn = null;
	   String result="";
		try { 
			// Change to DrawGuesser database later
			String dbURL = "jdbc:sqlserver://localhost;"+ "instanceName=SQLEXPRESS;databaseName=university;user=sa;password=csis3300";
			conn = DriverManager.getConnection(dbURL);
			if (conn != null) {
				// prepared statements that have parameters, useful for your project
				PreparedStatement prepStmt1 = null;		
				// Change _User later on
			//	String selectSQL = "SELECT UserID FROM _USER WHERE ? = ?";
				/// TEsting
				String selectSQL = "SELECT ID FROM teaches where ID = 'ASDASD' ";
				prepStmt1 = conn.prepareStatement(selectSQL);
			//	prepStmt1.setString(1, typeOfRequest);
			//	prepStmt1.setString(2, request);
				ResultSet rs = prepStmt1.executeQuery();
				
				while ( rs.next() ) {
					result =rs.getString("ID");
				}
			
				
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
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
