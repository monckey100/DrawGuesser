import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC2 {

	static String dbURL = "jdbc:sqlserver://localhost;"+ "instanceName=SQLEXPRESS;databaseName=DrawGuesser;user=sa;password=csis3300";
	static Connection conn = null;
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
	
	public static PreparedStatement getUserNameAndPassword(String query,String userName, String password) {
		PreparedStatement prepStmt1 = null;		

		try {
			if(conn !=null) {
				prepStmt1 = conn.prepareStatement(query);
				prepStmt1.setString(1, userName);
				prepStmt1.setString(2, password);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prepStmt1;
	}
	
	public static String getResult(String typeOfRequest, String request) {
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
		
	}
	
	public static boolean checkLogin(String userName, String password) {
		boolean check =false;
		
		openConnection();
		String query="SELECT userName FROM _USER WHERE userName = ? and _Password =? ";

		try {
			if (conn != null) {
				PreparedStatement prepStatement = getUserNameAndPassword(query,userName,password);
				ResultSet rs = prepStatement.executeQuery();
				
				if ( rs.next() ) {
					check =true;
				}
							
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		closeConnection();
		return check;
	}

}
