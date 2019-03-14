import java.io.*;
import java.net.*;

class Client
{
	static BufferedReader inFromUser;
	static DatagramSocket clientSocket = null;
 
   public static void main(String args[]) throws Exception
   {
	   	// just for testing
	   	/*String a = getDatabaseData("funny test","CC");
	   	System.out.println(a);
	  	String b = getDatabaseData("aa","DD");
	    */
   }
   
   public static String getDatabaseData(String requestType,String userRequest)   {
	   
	   String databasePackage;
	   byte[] receiveData = new byte[1024];
	   inFromUser = new BufferedReader(new InputStreamReader(System.in));
	   clientConnection myConn = new clientConnection("localhost");
		      
			// Concatenate the request
			String request = requestType+","+userRequest;
			// Send the request
			myConn.sendData(request);			     
						
		    databasePackage = myConn.receiveData(100);
		      		      
		    System.out.println("FROM SERVER:" + databasePackage);
		    clientSocket.close();
	   
	   /*
	  BufferedReader inFromUser =
      new BufferedReader(new InputStreamReader(System.in));
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("localhost");
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      String sentence = inFromUser.readLine();
      sendData = sentence.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
      clientSocket.send(sendPacket);
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      String modifiedSentence = new String(receivePacket.getData());
      System.out.println("FROM SERVER:" + modifiedSentence);
      clientSocket.close();
	    */
	   return databasePackage;
	   
   }


}
class clientConnection {
	InetAddress IPAddress = null;
	DatagramSocket clientSocket = null;
	public clientConnection(String Name) {
		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			IPAddress = InetAddress.getByName(Name);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendData( String request) {
			  
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
	public String receiveData(int waitTime) {
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


