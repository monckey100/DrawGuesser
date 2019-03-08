import java.util.*;  
import java.sql.*;  

public class JDBC {
	  public static Connection getConnection(){  
	        Connection conn=null;  
	        try{  
	        	String dbURL = "jdbc:sqlserver://localhost;"
						+ "instanceName=SQLEXPRESS;database=xxxxxx;user=sa;password=;";

				conn = DriverManager.getConnection(dbURL); 
	        }catch(Exception e){System.out.println(e);}  
	        return conn;  
	    }  
	  
	  
	  
	  public static int save(User e){  
	        int status=0;  
	        try{  
	            Connection con=JDBC.getConnection();  
	            PreparedStatement ps=con.prepareStatement(  
	                         "insert into user905(Fname,Lname,password,email) values (?,?,?,?)");  
	            ps.setString(1,e.getFname()); 
	            ps.setString(2,e.getLname());
	            ps.setString(3,e.getPassword());  
	            ps.setString(4,e.getEmail());  
    
	              
	            status=ps.executeUpdate();  
	              
	            con.close();  
	        }catch(Exception ex){ex.printStackTrace();}  
	          
	        return status;  
	    }  

}
