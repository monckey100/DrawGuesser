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
					+ " ELSE BEGIN DBCC CHECKIDENT ('_User', RESEED) " 
					+ "END;" ;
			break;
		case "DIFFICULT_LEVEL":
			result = "SELECT DifficultyLevel FROM DifficultyLevel";
			break;
		case "TIME_PERIOD":
			result = "SELECT TimePeriod FROM DifficultyLevel WHERE DifficultyLevel = ?";
			break;
		case "WORD_CATEGORY":
			result = "SELECT CatagoryName FROM Word_Category";
			break;
		case "USER_INFO":
			System.out.println(Client.username+ "  TEST");
			result = "SELECT userName,_Level,_Exp FROM _User WHERE userName= '" + Client.username+"'";
			break;
		}
		
		
		
		return result;
	}
	
	public static PreparedStatement getPreparedStatement(String query,String ... args) {
		PreparedStatement prepStmt1 = null;		

		try {
			if(conn !=null) {
				
				prepStmt1 = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE);
				if( args.length !=0) {
					for(int i = 0 ; i < args.length ; i++) {
						prepStmt1.setString(i+1, args[i]);								
					}
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
		String[] resultArray = null;//new String[inforArray.length + 1];
		// Array list to temporary hold the result
		ArrayList<String> resultList = new ArrayList<>();
		try {
			if (conn != null) {
				
				prepStatement = getPreparedStatement(query,args);
				ResultSet rs = prepStatement.executeQuery();
				
				if ( rs.next() ) {				
						rs.beforeFirst();
						while(rs.next()) {				
							for(int  i =0 ; i < inforArray.length; i++) {
								resultList.add(rs.getString(inforArray[i]));

							}
							
						}
						
					// Set successful request
					resultList.add(0, "Success");
					// Convert arraylist to array
					resultArray = resultList.toArray(new String[resultList.size()])	;
				}
				else
					resultArray = new String[] {"Fail"};
							
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




