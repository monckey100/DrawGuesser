
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
	   DatagramSocket serverSocket = new DatagramSocket(9876);
            while(true)
              {
            	
                byte[] receiveData = new byte[1024];
                byte[] sendData = new byte[1024];
            	
            	///
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);
                  String sentence ="";
                  
                  sentence = new String( receivePacket.getData());
                  
                  System.out.println("RECEIVED: " + sentence);
                  InetAddress IPAddress = receivePacket.getAddress();
                  int port = receivePacket.getPort();
                  /////
                  // Split the request and the type of it
                  String[] requestArray = sentence.split(",");
                  // Call a method to get the database result
                  String dbResult = getDatabaseResult(requestArray[1],requestArray[0]);
                  
                  
                                   
                  sendData = dbResult.getBytes();
                  DatagramPacket sendPacket =
                  new DatagramPacket(sendData, sendData.length, IPAddress, port);
                  serverSocket.send(sendPacket);
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
