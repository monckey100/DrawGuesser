import java.io.*;
import java.net.*;

class Client
{
   public static void main(String args[]) throws Exception
   {
	   // just for testing
	   /*String a = getDatabaseData("funny test","CC");
	   System.out.println(a.isEmpty());
	  String b = getDatabaseData("aa","DD");
	   System.out.println(b.trim().isEmpty());*/
	   //getDatabaseData("Test","test2");
   }
   
   public static String getDatabaseData(String userRequest,String requestType)   {
	   
	   String databasePackage;
	   BufferedReader inFromUser =
		         new BufferedReader(new InputStreamReader(System.in));
		      DatagramSocket clientSocket = null;
			try {
				clientSocket = new DatagramSocket();
			} catch (SocketException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		      InetAddress IPAddress = null;
			try {
				IPAddress = InetAddress.getByName("localhost");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		      byte[] sendData = new byte[1024];
		      byte[] receiveData = new byte[1024];
		      /// Concatenation between request and request type
		      String request = requestType+","+userRequest;
		      sendData = request.getBytes();
		      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		      try {
				clientSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		      try {
				clientSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      databasePackage = new String(receivePacket.getData());
		      System.out.println("FROM SERVER:" + databasePackage);
		      clientSocket.close();
	   
	   
	   return databasePackage;
	   
   }
}
