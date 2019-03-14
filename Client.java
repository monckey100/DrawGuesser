import java.io.*;
import java.net.*;

class Client
{
	static BufferedReader inFromUser;
	static DatagramSocket clientSocket = null;
	static InetAddress IPAddress = null;
	
    
   public static void main(String args[]) throws Exception
   {
	   // just for testing
	   /*String a = getDatabaseData("funny test","CC");
	   System.out.println(a);
	  String b = getDatabaseData("aa","DD");
	   System.out.println(b);*/
   }
   
   public static String getDatabaseData(String requestType,String userRequest)   {
	   
	   String databasePackage;
	   byte[] receiveData = new byte[1024];
	   inFromUser = new BufferedReader(new InputStreamReader(System.in));

			try {
				clientSocket = new DatagramSocket();
			} catch (SocketException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
		      
			try {
				IPAddress = InetAddress.getByName("localhost");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Concatenate the request
			String request = requestType+","+userRequest;
			// Send the request
			sendData(request);			     
						
		    databasePackage = receiveData(100);
		      		      
		    System.out.println("FROM SERVER:" + databasePackage);
		    clientSocket.close();
	   
	   
	   return databasePackage;
	   
   }
   public static void sendData( String request) {
	  
	      byte[] sendData = new byte[1024];
	      sendData = request.getBytes();
	      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
	      try {
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
  
   public static String receiveData(int waitTime) {
	   byte[] receiveData = new byte[1024];
	   String databasePackage;
	   
	   DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	// Wait for a reply
				for( int i = 0; i < 10 ; i++) {
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
									   
	      
	    try {
			clientSocket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      databasePackage = new String(receivePacket.getData());
	      
	      return databasePackage;
   }
}







/* 
/// Concatenation between request and request type

sendData = request.getBytes();
DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
try {
	clientSocket.send(sendPacket);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}*/
