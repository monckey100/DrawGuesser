import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

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
			// Simple query to check username and password
			result = "SELECT UserID FROM _User WHERE userName = ? AND _Password = ?";
			// Keep a record of the username
			Client.username =args[0];
			
			break;

		case "SIGNUP":
			// Sign up proceed if email and username doesn't exist in the database
			result =  "IF NOT EXISTS ( SELECT * FROM _User WHERE Email = '" +args[1]+"' OR userName = '"+ args[0]+"' ) "
					+ " BEGIN "
					+ " INSERT INTO _USER (userName,Email,_Password,Fname,Lname,_Level,_Exp) "
					+ " VALUES(?,?,?,?,?,1,0) END "
					+ " ELSE BEGIN DBCC CHECKIDENT ('_User', RESEED) " 
					+ "END;" ;
			break;
		case "DIFFICULT_LEVEL":
			// Get difficulty level
			result = "SELECT DifficultyLevel FROM DifficultyLevel";
			break;
		case "TIME_PERIOD":
			// Get time period
			result = "SELECT TimePeriod FROM DifficultyLevel WHERE DifficultyLevel = ?";
			break;
		case "WORD_CATEGORY":
			// Get word category for menu gui
			result = "SELECT CatagoryName FROM Word_Category";
			break;
		case "USER_INFO":
			// Get user general information
			result = "SELECT UserID,userName,_Level,_Exp FROM _User WHERE userName= '" + Client.username+"'";
			break;
			
			// Get a word for the guessing mode, get randomly from db
		case "GET_WORD":

			result = "SELECT TOP 1 WordName, WordID "
					+ "FROM Words join Word_Category on Words.CategoryID = Word_Category.CategoryID "
					+ "WHERE CatagoryName = ? "
					+ "ORDER BY NEWID()";
					
			break;
			// Send image to drawing table and convert string data type to nvarchar(max)
		case "IMAGESEND":

			result =  " INSERT INTO Drawing(UserID,WordID,DifficultyLevel,DrawingData) "
						+ " VALUES (?,?,?,cast(? as nvarchar(max)) ); ";
									
			break;
			
			
			
			//Get a random image from drawing with 2 conditions:
			// First: not from the same user who draw it
			// Second: not display the same image that this user made a right guess before
			
		case "GET_IMAGE":
			result = " SELECT TOP 1 WordName,DrawingData,DrawingID,UserID "
					+" FROM Drawing join Words on Drawing.WordID = Words.WordID "
					+" JOIN Word_Category on Words.CategoryID = Word_Category.CategoryID "
					
				
					+" WHERE UserID <> ? AND CatagoryName = ? AND DrawingID NOT IN "
					+"		( SELECT Drawing.DrawingID FROM DRAWING JOIN Correct_Guess on Drawing.DrawingID  = Correct_Guess.DrawingID) "
					+" ORDER BY NEWID()";
			break;
			// Insert user guess to Guess table
		case "INSERT_GUESS":
			result =  " INSERT INTO Guess(DifficultyLevel,DrawingID,UserID,SucceedTimes,TotalTime) "
					+ " VALUES (?,?,?,?,?)";
			break;
		case "UPDATE_POINT":
			// Common part of update statement for drawer and guesser
			String format =" UPDATE _User "
					+ " SET _Exp = _Exp + CASE "
					+ " WHEN DifficultyLevel = 'Easy' THEN 10 " 
					+ "		 WHEN DifficultyLevel = 'Intermediate' THEN 20 "
					+ " 	 WHEN DifficultyLevel = 'Hard' THEN 30 "
					+ "		 ELSE 0 "
					+ "		 END ";
			// Update point for guesser
			result =format
					+ " FROM _User JOIN Guess on _User.UserID = Guess.UserID "
					+ "	WHERE  _User.UserID = ? AND DifficultyLevel = ?  AND SucceedTimes =1 ; "
					+ " "
					//Update point for drawer
					+format
					+ " FROM _User JOIN Drawing on _User.UserID = Drawing.UserID "
					+ "	WHERE  _User.UserID = ? AND DifficultyLevel = "
					+ "	(SELECT DifficultyLevel FROM Drawing WHERE DrawingID = ? );"
					+ " "
					
					// update level for both guesser and drawer
					+ " UPDATE _User "
					+ "	SET _Level = (_Level +1)  WHERE _Level = ( _Exp / 100 ) ";
					
			
			break;
			
			// Insert correct guess to seperate table to ensure that the correct guess for 
			// specific user won't show up for that user again
		case "INSERT_CORRECT_GUESS":
			result = " INSERT INTO Correct_Guess VALUES (?,?)";
			break;
			
			// Simple query to get result per image
		case "FINAL_RESULT":
			
			result = "  SELECT sum(SucceedTimes) as succeed , sum(TotalTime) as total " 
					+ " from Guess " 
					+ " where DrawingID = ? ";

			break;
		

		}
		
		
		
		
		return result;
	}
	//Method that will prepare multiple	statement with passed parameters
	public static PreparedStatement getPreparedStatement(String query,String ... args) {
		PreparedStatement prepStmt1 = null;		

		try {
			if(conn !=null) {
				// Set a default setting for prepared statement
				prepStmt1 = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE);
				// Only prepare the statement with parameters
				
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
		String query= getQuery(requestType,args);
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
					// Set the cursor to beginning
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
		int status = 0;
		// Get query base on type
		
		String query= getQuery(requestType,args);
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

		if ( status >0  )
			result = new String[] {"Success"};
		return result;
	}
	
	


}




