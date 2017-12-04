package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLHelper {
	
		String url = "jdbc:mysql://127.0.0.1:3306/chatDB";
		String username = "root";
		String password = "admin";

   public List<List<String>> getUserData() throws SQLException {
	   
	   ResultSet resultSet = null;
	   System.out.println("Connecting database...");
	   try {
			Connection connection = DriverManager.getConnection(url, username, password);
		    System.out.println("Database connected!");
		    
		    String query = "SELECT * FROM `chatDB`.`Users`;";
		    
		    resultSet = connection.prepareStatement(query).executeQuery();
		    
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	   
	   List<List<String>>Data = new ArrayList<List<String>>();
	   int i = 0;
        while (resultSet.next()) {
            String user = resultSet.getString("userID");
            String password = resultSet.getString("password");

            i++;
            
            Data.add(Arrays.asList(user,password));
            
            System.out.println("User: " + user);
            System.out.println("Password: " + password);
        }
		return Data;
    }
}