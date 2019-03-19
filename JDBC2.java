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
	
	public static String getQuery(String requestType,String ...args ) {
		String result = "";
		switch(requestType) {
		case "LOGIN":
			result = "SELECT _Exp,_Level FROM _User WHERE userName = ? AND _Password = ?";
			break;

		case "SIGNUP":
			result =  "IF NOT EXISTS ( SELECT * FROM _User WHERE Email = '" +args[0]+"' OR userName = '"+ args[1]+"' ) "
					+ " BEGIN "
					+ " INSERT INTO _USER (userName,_Password,Fname,Lname,Email,_Level,_Exp) "
					+ " VALUES(?,?,?,?,?,1,0) END "
					+ " ELSE BEGIN DBCC CHECKIDENT ('_User', RESEED) " // problem
					+ "END;" ;
			break;
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
	

	
	public static String[] getData(String requestType,String[] inforArray,String ... args) {
		// Open the connection
		openConnection();

		// Get query base on type, userNAme and email must be unique
		String query= getQuery(requestType);
		// Initialize prepared statement
		PreparedStatement prepStatement =null;
		// Initialize array 
		// array length + 1 since we need the first index to indicate success or fail request
		
		String[] resultArray = new String[inforArray.length + 1];
		try {
			if (conn != null) {
				
				prepStatement = getPreparedStatement(query,args);
				ResultSet rs = prepStatement.executeQuery();
				
				if ( rs.next() ) {
					check =true;
					validUserName = args[0];
				}
				if(check) {						
					resultArray [0] ="Success";
					for( int i = 1 ; i < resultArray.length ; i++) {
						// Items's index will be the same as the columns in select query
						resultArray[i] = rs.getString(inforArray[i-1]);
					}
				}
				else
					resultArray[0] = "Fail";
							
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		closeConnection();
		return resultArray;
	}
	
	// this method can either be used for the insert or update
	public static String[] modifyData(String requestType,String ... args) {
		// Open the connection
		openConnection();

		String[] result =null;
		// Check if query succesfully execute
		int status = 0 ;
		// Get query base on type
		String query= getQuery(requestType,args[0],args[4]);
		// Initialize prepared statement
		PreparedStatement prepStatement =null;

		try {
			if (conn != null) {				
				prepStatement = getPreparedStatement(query,args);
				status = prepStatement.executeUpdate();										
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result = new String[] {"fail"};
		}
	
		closeConnection();

		if ( status > 0 )
			result = new String[] {"Success"};
		return result;
	}

}




