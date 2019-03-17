import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBC2 {

	static String dbURL = "jdbc:sqlserver://localhost;"+ "instanceName=SQLEXPRESS;databaseName=DrawGuesser;user=sa;password=csis3300";
	static Connection conn = null;
	public static boolean check = false;
	// Keep the username when it is valid
	static String validUserName;

	public static void openConnection() {
		
		try { 			
			conn = DriverManager.getConnection(dbURL);	
			
			}		
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void closeConnection() {		
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		
	}
	
	public static String getQuery(String requestType) {
		String result = "";
		switch(requestType) {
		case "LOGIN":
			result = "SELECT userName,_XP,_LEVEL FROM _USER WHERE ? = ?";
			break;
		// Will continue on later
		}
		
		
		
		return result;
	}
	
	public static PreparedStatement getPreparedStatement(String query,String ... args) {
		PreparedStatement prepStmt1 = null;		

		try {
			if(conn !=null) {
				prepStmt1 = conn.prepareStatement(query);
				
				for(int i = 0 ; i < args.length ; i++) {
					prepStmt1.setString(i+1, args[i]);
					
				
				}
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prepStmt1;
	}
	

	
	public static ArrayList getData(String requestType,String ... args) {
		// Open the connection
		openConnection();

		// Get query base on type
		String query= getQuery(requestType);
		// Initialize prepared statement
		PreparedStatement prepStatement =null;
		// Initialize array list
		ArrayList<String> resultList = new ArrayList<>();
		try {
			if (conn != null) {
				
				prepStatement = getPreparedStatement(query,args);
				ResultSet rs = prepStatement.executeQuery();
				
				if ( rs.next() ) {
					check =true;
					validUserName = rs.getString("userName");
				}
				for( int i = 0 ; i < args.length ; i++) {
					// Items's index will be the same as the columns in select query
					resultList.add(rs.getString(args[i]));
				}
							
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		closeConnection();
		return resultList;
	}

}













/*	public static String getResult(String typeOfRequest, String request) {
String result="";
openConnection();
String query="SELECT * FROM _USER WHERE ? = ?";

try {
	if (conn != null) {
		PreparedStatement prepStatement = getUserNameAndPassword(query,typeOfRequest,request);
		ResultSet rs = prepStatement.executeQuery();
		
		while ( rs.next() ) {
			result = rs.getString("ID");
		}
					
	}
}
catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

closeConnection();
return result;

}*/