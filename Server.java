
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

class Server {
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
				// Array of needed information
				String[] neededInfo = null;
				System.out.println("TYPE: " + myConn.getType() + " DATA: " + Arrays.toString(myConn.getData()));
				switch (myConn.getType()) {
				case "LOGIN":
					// We assume getData will be {username,password}
					
					neededInfo = new String[] {"_Exp","_Level"};
					try {
						myConn.setType("LOGIN");
						String username = myConn.getData()[0];
						String password = myConn.getData()[1];
						System.out.println("Username: '" + username + "' Password: '" + password + "'");
						
						sendInfo = jdbc.getData("LOGIN",neededInfo,username,password);

						//sendInfo = new String[] { "Success", "1337", "1" };
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					break;
				case "SIGNUP":
					myConn.setType("SIGNUP");
					sendInfo = jdbc.modifyData("SIGNUP", myConn.getData());
				//	sendInfo = new String[] { "Failed" };
					break;
				case "DIFFICULT_LEVEL":
					neededInfo = new String[] {"DifficultyLevel"};
					myConn.setType("DIFFICULT_LEVEL");
					sendInfo = jdbc.getData("DIFFICULT_LEVEL", neededInfo);
					break;
				case "TIME_PERIOD":
					neededInfo = new String[] {"TimePeriod"};
					myConn.setType("TIME_PERIOD");
					sendInfo = jdbc.getData("TIME_PERIOD", neededInfo, myConn.getData());
					break;
				case "WORD_CATEGORY":
					neededInfo = new String[] {"CatagoryName"};
					myConn.setType("WORD_CATEGORY");
					sendInfo = jdbc.getData("WORD_CATEGORY",neededInfo);
					break;
				case "USER_INFO":
					neededInfo = new String[] {"userName","_Level","_Exp"};
					myConn.setType("USER_INFO");
					sendInfo = jdbc.getData("USER_INFO", neededInfo);
					break;
				case "IMAGESEND":
					myConn.setType("IMAGESEND");
					sendInfo = new String[] { "Failed" };
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
		byte[] receiveData = new byte[1024];

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
		byte[] sendData = new byte[1024];

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
