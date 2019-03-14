
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
            	

                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);
                  String sentence ="";
                  
                  sentence = new String( receivePacket.getData());
                  
                  System.out.println("RECEIVED: " + sentence);
                  InetAddress IPAddress = receivePacket.getAddress();
                  int port = receivePacket.getPort();

                  // Split the request and the type of it
                  String[] requestArray = sentence.split(",");
                  // Call a method from jdbc class to get the database result
                  JDBC2 jdbc = new JDBC2();

                  String dbResult = jdbc.getResult(requestArray[0],requestArray[1]);
                  
                  
                                   
                  sendData = dbResult.getBytes();
                  DatagramPacket sendPacket =
                  new DatagramPacket(sendData, sendData.length, IPAddress, port);
                  serverSocket.send(sendPacket);
               }
      }
   

}
