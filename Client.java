import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

class Client {
	// Set username once authenticated so we can reuse it.
	public static String username = "aaa";

	public static boolean login(String username, String password) {
		String[] result = send("LOGIN", new String[] { username, password });
		if (result[0].equals("Success")) {
			// set clients uesrname.
			Client.username = username;
			return true;
		}
		return false;
	}

	public static boolean signup(String username, String password, String firstname, String lastname, String email) {
		String[] result = send("SIGNUP", new String[] { username, password, firstname, lastname, email });
		if (result[0].equals("Success")) {
	
			return true;
		}
		return false;
	}
	
	public static  String [] getNeededInfor(String info,String... args) {

		String[] result =send(info, args);
		return result;
	}

	// This needs difficulty + what image and whatnot.
	public static boolean sendImage(String bytes) {
		if (Client.username != null) {
			String[] result = send("IMAGESEND", new String[] { Client.username, bytes });
			if (result[0].equals("Success")) {
				return true;
			}
			return false;
		}
		return false;
	}

	public static String[] send(String type, String[] Data) {
		clientConnection myConn = new clientConnection("localhost");
		// Send the request
		myConn.sendData(type, Data);
		myConn.receiveData();
		if (myConn.getType().equals(type)) {
			System.out.println("FROM SERVER:" + myConn.getData()[0]); // prints success/fail
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

	public void sendData(String Type, String[] data) {
		sendrawData(Type + "|" + Arrays.toString(data));
	}

	public void sendrawData(String request) {
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
		// remove first character [
		String m = x.substring(1);
		// remove last character ]
		m = m.substring(0, m.length() - 1);
		return m.split(", ");
	}

	public void closeConnection() {
		clientSocket.close();
	}
}
