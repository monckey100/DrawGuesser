import java.util.*;  
import java.sql.*;  

public class JDBC {
	  public static Connection getConnection(){  
	        Connection con=null;  
	        try{  
	        	String dbURL = "jdbc:sqlserver://localhost;"
						+ "instanceName=SQLEXPRESS;database=DrawGuesser;user=sa;password=;";

				con = DriverManager.getConnection(dbURL); 
	        }catch(Exception e){System.out.println(e);}  
	        return con;  
	    }  
	  
	  
	  
	  public static int AddUser(User e){  
	        int status=0;  
	        try{  
	            Connection con=JDBC.getConnection();  
	            PreparedStatement ps=con.prepareStatement(  
	                         "insert into _User(Fname,Lname,_Password,Email) values (?,?,?,?)");  
	            ps.setString(1,e.getFname()); 
	            ps.setString(2,e.getLname());
	            ps.setString(3,e.getPassword());  
	            ps.setString(4,e.getEmail());  
          
	            status=ps.executeUpdate();  
	              
	            con.close();  
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        	}  
	          
	        return status;  
	    }  
	  
	  public static int update(User e){  
	        int status=0;  
	        try{  
	            Connection con=JDBC.getConnection();  
	            PreparedStatement ps=con.prepareStatement(  
	                         "update _User set Fname=?,Lname=?,_Password=?,Email=? where UserID=?");  
	            ps.setString(1,e.getFname()); 
	            ps.setString(2,e.getLname());
	            ps.setString(3,e.getPassword());  
	            ps.setString(4,e.getEmail());   
	            ps.setInt(5,e.getId());  
	              
	            status=ps.executeUpdate();              
	            con.close();  
	        }catch(Exception ex){ex.printStackTrace();}  
	          
	        return status;  
	    }  
	  
	  
	  public static int delete(int id){  
	        int status=0;  
	        try{  
	            Connection con=JDBC.getConnection();  
	            PreparedStatement ps=con.prepareStatement("delete from user where UserID=?");  
	            ps.setInt(1,id);  
	            status=ps.executeUpdate();  
	              
	            con.close();  
	        }catch(Exception e){e.printStackTrace();}  
	          
	        return status;  
	    }  
	  
	  
	  
	  public static User getEmployeeById(int id){  
	        User e=new User();  
	          
	        try{  
	            Connection con=JDBC.getConnection();  
	            PreparedStatement ps=con.prepareStatement("select * from _User where UserID=?");  
	            ps.setInt(1,id);  
	            ResultSet rs=ps.executeQuery();  
	            if(rs.next()){  
	                e.setId(rs.getInt(1));  
	                e.setFname(rs.getString(2)); 
	                e.setLname(rs.getString(3));
	                e.setPassword(rs.getString(4));  
	                e.setEmail(rs.getString(5));  
	                  
	            }  
	            con.close();  
	        }catch(Exception ex){ex.printStackTrace();}  
	          
	        return e;  
	    }  
	  
	  public static List<User> getAllEmployees(){  
	        List<User> list=new ArrayList<User>();  
	          
	        try{  
	            Connection con=JDBC.getConnection();  
	            PreparedStatement ps=con.prepareStatement("select * from _User");  
	            ResultSet rs=ps.executeQuery();  
	            while(rs.next()){  
	            	User e=new User();  
	                e.setId(rs.getInt(1));  
	                e.setFname(rs.getString(2));  
	                e.setLname(rs.getString(3));  
	                e.setPassword(rs.getString(4));  
	                e.setEmail(rs.getString(5));  
	                list.add(e);  
	            }  
	            con.close();  
	        }catch(Exception e){e.printStackTrace();}  
	          
	        return list;  
	    }  




}
