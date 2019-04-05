import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

class Client {
	// Set username once authenticated so we can reuse it.
	public static String username ;

	// The result array first element always return an indicator whether the request 
	// is successfully reply with a result or not
	
	// Login request from user
	public static boolean login(String userName, String password) {
		String[] result = send("LOGIN", new String[] { userName, password });

		if (result[0].equals("Success")) {
			
			return true;
		}
		return false;
	}

	// Sign up request from user
	public static boolean signup(String username, String email, String password, String firstname, String lastname) {
		String[] result = send("SIGNUP", new String[] { username, email,password, firstname, lastname });
		if (result[0].equals("Success")) {
	
			return true;
		}
		return false;
	}
	
	// Request that get the time period for every single difficult level
	public static boolean time_period(String difficulty)
	{
		String[] result = send("TIME_PERIOD", new String[] { difficulty});
		if (result[0].equals("Success")) {
	
			return true;
		}
		return false;		
	}
	// Request that send images to the server
	public static boolean imagesend(String userID,String word_id,String difficulty,String image_data)
	{

		String[] result = send("IMAGESEND", new String[] { userID, word_id, difficulty,image_data});
		
		if (result[0].equals("Success")) {
	
			return true;
		}
		return false;		
	}
	
	// Request that insert correct guess to the database

	
	public static boolean insertGuess(String ... args)
	{
		String[] result = send("INSERT_GUESS", args);
		if (result[0].equals("Success")) {
	
			return true;
		}
		return false;		
	}
	// Request that update points for correct guess
	public static boolean updatePoint(String userID, String diffLevel,String drawerID, String drawingID) {
		String[] result = send("UPDATE_POINT", new String[] {userID,diffLevel,drawerID,drawingID});
		if (result[0].equals("Success")) {
			
			return true;
		}
		return false;
	}
	// Request that insert guesses no matter it right or not
	public static boolean insertCorrectGuess(String userID, String drawingID) {
		String[] result = send("INSERT_CORRECT_GUESS", new String[] {userID,drawingID});
		if (result[0].equals("Success")) {
			
			return true;
		}
		return false;
	}
	
	// Request that get general information from the users
	public static  String [] getNeededInfor(String info,String... args) {

		String[] result =send(info, args);
		
		return result;
	}

	 
	/*public static boolean sendImage(String bytes) {
		if (Client.username != null) {
			String[] result = send("IMAGESEND", new String[] { Client.username, bytes });
			if (result[0].equals("Success")) {
				return true;
			}
			return false;
		}
		return false;
	}*/

	// Method that in charge of sending user request package to the server for processing
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
// Class that handle connection
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
	// Method concatenate request type and the data array
	public void sendData(String Type, String[] data) {
		sendrawData(Type + "|" + Arrays.toString(data));
	}
	
	// Method that send the package to the server
	public void sendrawData(String request) {
		byte[] sendData = new byte[100000];
		sendData = request.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		try {
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Method that handle receive the package from the server
	public String receiveData() {
		byte[] receiveData = new byte[100000];
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
	// Type getter
	public String getType() {
		return mType;
	}
	// data setter
	public String[] getData() {
		return mData;
	}
	// split the package that include request type and data itself
	public void parseData() {
		String[] f = rawData.trim().split("\\|");
		mType = f[0];
		mData = pData(f[1]);
	}

	// Process data
	public String[] pData(String x) {
		// remove first character [
		String m = x.substring(1);
		// remove last character ]
		m = m.substring(0, m.length() - 1);
		return m.split(", ");
	}
	// Close connection
	public void closeConnection() {
		clientSocket.close();
	}
}
