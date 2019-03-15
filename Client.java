import java.io.*;
import java.net.*;
import java.util.Arrays;

class Client
{
   public static boolean login(String username,String password)   {
		  String[] result = send("LOGIN",new String[]{username, password}); 
		  if(result[0] == "Success") {
			return true;  
		  }
		  return false;
   }
   public static boolean signup(String firstname, String lastname, String email, String username, String password) {
	  String[] result = send("SIGNUP",new String[]{firstname, lastname, email, username, password}); 
	  if(result[0] == "Success") {
		return true;  
	  }
	  return false;
   }
   public static String[] send(String type, String[] Data) {
  		clientConnection myConn = new clientConnection("localhost");
		// Send the request
		myConn.sendData(type,Data);			     
		myConn.receiveData();
		if(myConn.getType() == type) {
			System.out.println("FROM SERVER:" + myConn.getData()[0]); //prints success/fail 
		} else {
			System.out.println("ERROR FROM SERVER: " + myConn.getData()[0]);
		}
	    myConn.closeConnection(); 
	   return myConn.getData();
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
	public void sendData(String Type,String[] data) {
		sendrawData(Type + "|" + Arrays.toString(data));
	}
	public void sendrawData( String request) {
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
		String[] f = rawData.trim().split("\\|");
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


