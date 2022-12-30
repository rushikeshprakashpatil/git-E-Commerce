package com.velocity.Ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuantityUpdate {
   private static Connection con;
   private static PreparedStatement ps;
	public static void quantityupdate(int id) throws SQLException {
		con=MyConnection.getConnection();
		ps=con.prepareStatement("select Quantity from productinfo where P_Id=?");
		ps.setInt(1, id);
		ResultSet rs=ps.executeQuery();
		int qty;
		while(rs.next()) {
			qty=rs.getInt(1);
			int newQty=(qty-1);
			ps=con.prepareStatement("update productinfo set Quantity=? where P_Id=?");
			ps.setInt(1, newQty);
			ps.setInt(2, id);
			ps.executeUpdate();	
		}
		con.close();
		ps.close();

	}

}
