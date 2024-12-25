package com.gowtham.util;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DBManager {

    private static DBManager dbManager;
    private Connection connection;

    public static DBManager getInstance() {
       if (dbManager == null) {
    	   dbManager = new DBManager();
       }
       dbManager.connect();
       return dbManager;
    }

    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense", "root", "Gowtham@217");
            System.out.println("DB connected successfully");
        } catch (Exception e) {
            throw new RuntimeException("Database connection failed: " + e.getMessage());
        }
    }

    public boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username ='"+username+"' AND password ='"+password+"';";
        try {
        	Statement st=connection.createStatement();
        	ResultSet rs=st.executeQuery(query);
        	while(rs.next())
        		return true;
        } catch (SQLException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
        return false;
    }
    
    public boolean answerCheck(String answer, String retypePassword, String email) {
        String updateQuery = "UPDATE users SET password = '" + retypePassword + 
                             "' WHERE email = '" + email + "' AND security_answer = '" + answer + "';";

        try {
            Statement st = connection.createStatement();
            int rowsUpdated = st.executeUpdate(updateQuery);
            return rowsUpdated > 0; 
        } catch (SQLException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }
    
    public boolean checkUsernameandEmail(String username,String email) {
    	String query = "SELECT * FROM users WHERE username = '"+username+"' OR email = '"+email+"';";
    	
        try {
        	Statement st=connection.createStatement();
        	ResultSet rs=st.executeQuery(query);
        	while(rs.next())
        		return true;
        } catch (SQLException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
        return false;
    }
    
    public boolean fillUser(String username, String fullName, String email, String password, String phone, String answer) {
    	
    	String updateQuery = "INSERT INTO users(username, full_name, email, phone, password, security_answer) " +
                "VALUES ('" + username + "', '" + fullName + "', '" + email + "', '" + phone + "', '" + password + "', '" + answer + "')";
		try {
			Statement stmt = connection.createStatement();
			int rowsUpdated = stmt.executeUpdate(updateQuery);
			return rowsUpdated > 0;
		} catch (SQLException e) {
			throw new RuntimeException("Authentication failed: " + e.getMessage());
		}
    }

	public boolean passwordCheck(String password) {
    	String query = "SELECT * FROM users WHERE password = '"+password+"';";
    	
        try {
        	Statement st=connection.createStatement();
        	ResultSet rs=st.executeQuery(query);
        	while(rs.next())
        		return true;
        } catch (SQLException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
        return false;
	}

}
