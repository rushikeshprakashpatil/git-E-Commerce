package com.velocity.Ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {
	private static Connection con;
	public static Connection getConnection() {
	    try {
	    	
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/e_commerce","root", "root");
	    	
		    } 
	    catch (Exception e) {
			e.printStackTrace();
		 }
	
      return con;
      
	}
}
