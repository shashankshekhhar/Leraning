package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.SqlConnection;

public class LoginModel {
	Connection connection;
	public LoginModel() {
		connection = SqlConnection.connector();
		
		if(connection== null)
			System.exit(1);
		
	
		
	}
	
	public boolean isDbConnected() {
		
		try {
			return !connection.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		}
	}
	public boolean isLogin(String user, String pass) throws SQLException {
		
	java.sql.PreparedStatement stmt = null ;
	ResultSet result= null ;
	String query="select * from student where username= ? and password=?";
	
	try {
		stmt = connection.prepareStatement(query);
		stmt.setString(1, user);
		stmt.setString(2, pass);
		result = stmt.executeQuery();
		if(result.next())
		{
			return true;
		}
		else
			return false;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	finally {
		stmt.close();
		result.close();
	}
	

	
	}

}
