
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

class Server {
	public static String userID;
	public static void main(String args[]) throws Exception {
		connectionHandler myConn = new connectionHandler();
		// Open connection to the database
		JDBC2 jdbc = new JDBC2();
		while (true) {
			String packet = myConn.getPackets();
			
			if (packet != null) {
				myConn.parsePacket(packet);
				// Hold packet to send
				String[] sendInfo = null;	
				// Array of needed information - table's columns name
				String[] neededInfo = null;
				System.out.println("TYPE: " + myConn.getType() + " DATA: " + Arrays.toString(myConn.getData()));
				switch (myConn.getType()) {

				// Login request type
				case "LOGIN":
				// Array of columns name that needed for return user information
					neededInfo = new String[] {"UserID"};
					try {
						myConn.setType("LOGIN");
						// Get user's username and password
						String username = myConn.getData()[0];
						String password = myConn.getData()[1];
						System.out.println("Username: '" + username + "' Password: '" + password + "'");
						
						// Call jdbc class to execute query and pass the result back
						sendInfo = jdbc.getData("LOGIN",neededInfo,username,password);
	
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					break;
				case "SIGNUP":
					myConn.setType("SIGNUP");
					// Call jdbc class to execute query and pass the result back
					sendInfo = jdbc.modifyData("SIGNUP", myConn.getData());
					break;
				/*
				 	Client picks Difficulty / Word Category.
				 	Difficulty level + Word Category is asked so client
				 	can populate choices.
				 	
				 	client picks both difficulty level + category then asks
				 	for the time period.
				 	
				 	This is used for both Drawing + Guessing.
				 */
				case "DIFFICULT_LEVEL":

					neededInfo = new String[] {"DifficultyLevel"};
					myConn.setType("DIFFICULT_LEVEL");
					sendInfo = jdbc.getData("DIFFICULT_LEVEL", neededInfo);
					break;
				case "WORD_CATEGORY":
					//Client: {userID,difficulty}
					//Server: {[Category ID 1, Category Name 1, [Category ID 2,Category Name 2].....}
					neededInfo = new String[] {"CatagoryName"};
					myConn.setType("WORD_CATEGORY");
					sendInfo = jdbc.getData("WORD_CATEGORY",neededInfo);
					break;		
				/*
				  This is used to get Time for both drawing + guessing	
				 */
				case "TIME_PERIOD":
					//Send back time_period AND word, depend on client receiving WORD_CATEGORY
					//Client: {userID,difficulty,Category_ID}
					//Server: {time_period,[word_id,word]}
					neededInfo = new String[] {"TimePeriod"};
					myConn.setType("TIME_PERIOD");
					sendInfo = jdbc.getData("TIME_PERIOD", neededInfo, myConn.getData());
					break;
				/*
				 	Client ran out of time or sent the image, we store the image,
				 	who made the image and return success/fail.
				 	
				 	Here the image is stored into Drawing table, for word we will
				 	need to get word by ID based on word.
				*/
				case "GET_WORD":
				neededInfo = new String[] {"WordName","WordID"};
				myConn.setType("GET_WORD");
				sendInfo = jdbc.getData("GET_WORD", neededInfo, myConn.getData());
				break;
				case "IMAGESEND":
					//Client: {userID,word_id,difficulty,image_data}
					//Server: {Successful}
					myConn.setType("IMAGESEND");
				
					sendInfo = jdbc.modifyData("IMAGESEND", myConn.getData());
				break;
				case "GET_IMAGE":
					//Client: {userID,word_id,difficulty}
					//Server: {wordID,drawingID,drawingData}
					neededInfo = new String[] {"WordName","DrawingData","DrawingID","UserID"};
					myConn.setType("GET_IMAGE");
					sendInfo = jdbc.getData("GET_IMAGE", neededInfo, myConn.getData());
					
					break;

				case "USER_INFO":
					/*
					  Client will ask for USER_INFO after every game finished.
					 */
					//Client: {userID}
					//Server: {level, EXP}
					neededInfo = new String[] {"userID","userName","_Level","_Exp"};
					myConn.setType("USER_INFO");
					sendInfo = jdbc.getData("USER_INFO", neededInfo);
					
					break;
				case "INSERT_GUESS":
					//Client: {userID,word_id,correct}
					//Server: {wordID,drawingID,drawingData}
					//neededInfo = new String[] {"WordName","DrawingData","Correct_Guess"};
					myConn.setType("INSERT_GUESS");
					sendInfo = jdbc.modifyData("INSERT_GUESS", myConn.getData());
					
					break;	
				case "UPDATE_POINT":
					myConn.setType("UPDATE_POINT");
					sendInfo = jdbc.modifyData("UPDATE_POINT", myConn.getData());
					
					break;
				case "INSERT_CORRECT_GUESS":
					myConn.setType("INSERT_CORRECT_GUESS");
					sendInfo = jdbc.modifyData("INSERT_CORRECT_GUESS", myConn.getData());
					
					break;
				case "FINAL_RESULT":
					neededInfo = new String[] {"succeed","total"};
					myConn.setType("FINAL_RESULT");

					sendInfo = jdbc.getData("FINAL_RESULT", neededInfo,myConn.getData());
					
					break;
				
					

				default:
					myConn.setType("ERROR");
					myConn.setData(new String[] { "Error: Could not find Datatype!" });
				}
				// Sends setType + setData to server.
				myConn.setData(sendInfo);
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
		// INIT Variables
		String myPacket = null;
		byte[] receiveData = new byte[100000];

		// Packet received
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			serverSocket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Convert Bytes into String
		myPacket = new String(receivePacket.getData());

		// Set Return IP Address & Port
		IPAddress = receivePacket.getAddress();
		port = receivePacket.getPort();
		return myPacket;
	}

	public boolean sendPacket(String myPacket) {
	
		byte[] sendData = new byte[100000];

		// Convert to bytes
		sendData = myPacket.getBytes();
	
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
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
		
		requestArray = mPacket.trim().split("\\|");
	}

	public String getType() {
		return requestArray[0];
	}

	public String[] getData() {
		// remove first character [

		String m = requestArray[1].substring(1);
		
		// remove last character ]
		m = m.substring(0, m.length() - 1);
		return m.split(", ");
	}

	// Sending to client
	public void setType(String type) {
		datatype = type;
	}

	public void setData(String[] data) {
		datastuff = data;
	}

	public String packageData() {
		
		return datatype + "|" + Arrays.toString(datastuff);
	}
}
