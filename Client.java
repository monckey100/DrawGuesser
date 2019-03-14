import java.io.*;
import java.net.*;

class Client
{
	static BufferedReader inFromUser;
 
   public static void main(String args[]) throws Exception
   {
	   	// just for testing
	   	/*String a = getDatabaseData("funny test","CC");
	   	System.out.println(a);
	  	String b = getDatabaseData("aa","DD");
	    */
   }
   
   public static String getDatabaseData(String requestType,String userRequest)   {
	   inFromUser = new BufferedReader(new InputStreamReader(System.in));
	   clientConnection myConn = new clientConnection("localhost");
		      
			// Concatenate the request
			String request = requestType+","+userRequest;
			// Send the request
			myConn.sendData(request);			     

			myConn.receiveData();
			if(myConn.getType() == "LOGIN") {
				//Move to next screen and do stuff with 
				//myConn.getData();
				System.out.println("FROM SERVER:" + myConn.getData()[0]); //prints success/fail 
			}
		      		      
		    myConn.closeConnection();
	   return myConn.getData()[0];
	   
   }


}
class clientConnection {
	InetAddress IPAddress = null;
	DatagramSocket clientSocket = null;
	String rawData = null;
	String mType = null;
	String[] mData = null;
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
	public String receiveData() {
		   byte[] receiveData = new byte[1024];
		   String databasePackage;
		   
		   DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				clientSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      databasePackage = new String(receivePacket.getData());
			  rawData = databasePackage;
			  parseData();
		      return databasePackage;
	}
	public String getType() {
		return mType;
	}
	public String[] getData() {
		return mData;
	}
	public void parseData() {
		String[] f = rawData.split(",");
		mType = f[0];
		mData = pData(f[1]);
	}
	public String[] pData(String x) {
		//remove first character [
		String m = x.substring(1); 
		//remove last character ]
		m = m.substring(0, m.length() - 1);
		return m.split(","); 
	}
	public void closeConnection() {
		clientSocket.close();
	}
}


