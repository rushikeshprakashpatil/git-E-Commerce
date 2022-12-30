package com.velocity.Ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

 class UpdateProduct {
	Connection con=null;
	PreparedStatement ps=null;
	 Scanner sc=new Scanner(System.in);
	void updateProductByName() throws SQLException {
		System.out.println("enter the product id to update the name");
		int id=sc.nextInt();
		System.out.println("enter the new name of product to update");
		String name=sc.next();
		con=MyConnection.getConnection();
		ps=con.prepareStatement("update productinfo set P_name=? where p_id=?");
		ps.setString(1, name);
		ps.setInt(2, id);
		ps.executeUpdate();
	}
void updateProductByDescription() throws SQLException {
	System.out.println("enter the product id to update the description");
	int id=sc.nextInt();
	System.out.println("enter the new description of product to update");
	String description=sc.next();
	con=MyConnection.getConnection();
	ps=con.prepareStatement("update productinfo set P_description=? where p_id=?");
	ps.setString(1, description);
	ps.setInt(2, id);
	ps.executeUpdate();
	}
void updateProductByPrice() throws SQLException {
	System.out.println("enter the product id to update the price");
	int id=sc.nextInt();
	System.out.println("enter the new price of product to update");
	int price=sc.nextInt();
	con=MyConnection.getConnection();
	ps=con.prepareStatement("update productinfo set price=? where p_id=?");
	ps.setInt(1, price);
	ps.setInt(2, id);
	ps.executeUpdate();
}
void updateProductByQuantity() throws SQLException {
	System.out.println("enter the product id to update the name");
	int id=sc.nextInt();
	System.out.println("enter the new quantity of product to update");
	int q=sc.nextInt();
	con=MyConnection.getConnection();
	ps=con.prepareStatement("update productinfo set quantity=? where p_id=?");
	ps.setInt(1, q);
	ps.setInt(2, id);
	ps.executeUpdate();
}
	public static void updateInfo() throws SQLException {
		UpdateProduct up=new UpdateProduct();
		Scanner sc=new Scanner(System.in);
		String s;
		do {
		System.out.println("what u want to update choose option");
		System.out.println("1:name/n 2:description/n 3:price/n 4:quantity");
		int ch1=sc.nextInt();
		switch(ch1)
		{
		case 1:up.updateProductByName();
		break;
	    
		case 2:up.updateProductByDescription();
		break;
		case 3:up.updateProductByPrice();
			break;
		case 4:up.updateProductByQuantity();;
			break;
		}
		System.out.println("do u want to update again press yes");
		 s= sc.next();
		}while(s.equalsIgnoreCase("yes"));
		System.out.println("thanku");
		Admin.adminDetails();           
	}
}


public class Admin {
	
	static Scanner sc=new Scanner(System.in);
	static Connection con=null;
	static PreparedStatement ps=null;
	void addNewProduct() throws SQLException {
		
			System.out.println("enter the  name of product to add");
			String name=sc.next();
			System.out.println("enter the description to add");
			String desc=sc.next();
			System.out.println("enter the price to add");
			int price=sc.nextInt();
			System.out.println("enter the quantity to add");
			int quantity=sc.nextInt();
			con=MyConnection.getConnection();
			ps=con.prepareStatement("insert in to productinfo(p_name,p_description,price,quantity) values(?,?,?,?)");
			ps.setString(1,name );
			ps.setString(2,desc);
			ps.setInt(3,price);
			ps.setInt(4,quantity);
			ps.executeUpdate();
			adminDetails();
	}
	void deleteProduct() throws SQLException {
		System.out.println("enter the product id to delete the record");
		int id=sc.nextInt();
		con=MyConnection.getConnection();
		ps=con.prepareStatement("delete from productinfo where p_id=?");
		ps.setInt(1,id);
		ps.executeUpdate();
		adminDetails();
	}
	void orderDetail() throws SQLException {// imp task ahe
		System.out.println("do u want all orders press all otherwise press no");
		String all=sc.next();
		if(all.equalsIgnoreCase("all")) {
			con=MyConnection.getConnection();
			ps=con.prepareStatement("select p_id,p_name,p_description,email,price from buyproduct inner join productinfo on pro_id=p_id ");
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				System.out.println("product id:"+rs.getString("p_id")+" ,");
				System.out.println("product name:"+rs.getString("P_name")+" ,");
				System.out.println("description:"+rs.getString("p_description")+" ,");
				System.out.println("email:"+rs.getString("email")+" ,");
				System.out.println("total bill:"+rs.getString("price")+" ,");
				System.out.println("================***=======================");
			  }		
			}
		else {
			System.out.println("enter email to search perticular order details");
			String email=sc.next();
			con=MyConnection.getConnection();
			ps=con.prepareStatement("select p_id,p_name,p_description,email,price from buyproduct inner join productinfo on buyproduct.pro_id=P_id where email=?");
			 ps.setString(1, email);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				System.out.println("product id:"+rs.getString("p_id")+" ,");
				System.out.println("product name:"+rs.getString("P_name")+" ,");
				System.out.println("description:"+rs.getString("p_description")+" ,");
				System.out.println("email:"+rs.getString("email")+" ,");
				System.out.println("total bill:"+rs.getString("price")+" ,");
				System.out.println("================***=======================");
			}		
		}
	  adminDetails();
	}
	void customerList() throws SQLException {
		System.out.println("user list");
		
		
		con=MyConnection.getConnection();
		ps=con.prepareStatement("select * from customerinfo");
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {

			System.out.print("email:"+rs.getString("email")+" ,");
			System.out.print("password:"+rs.getString("password")+" ,");
			System.out.print("customername:"+rs.getString("customername")+" ,");
			System.out.print("mobilenumber:"+rs.getString("mobilenumber")+" ,");
			System.out.println("history:"+rs.getString("history")+".");
			System.out.println("================***====================");
		}
		adminDetails();
		
	}
	void checkQuentity() throws SQLException {

		con=MyConnection.getConnection();
		ps=con.prepareStatement("select p_name,quantity from productinfo");
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			System.out.println("product name:"+rs.getString("P_name")+" ,");
			System.out.println("product quantity:"+rs.getString("quantity")+" ,");
			}
		adminDetails(); // sarv method mde hi method call kr
	}
	public static void adminDetails() throws SQLException{
		
	System.out.println("1:update product details/n 2:add new product/n 3:delete product/n 4:check order details/n 5:check customer list/n 6:check quentity of products/n 7:for home page ");
	System.out.println("enter the choice to perform the operation");
	int ch=sc.nextInt();
	Admin a=new Admin();
	switch(ch)
	{
	case 1:UpdateProduct.updateInfo();
	break;
    
	case 2:a.addNewProduct();
	break;
	case 3:a.deleteProduct();
		break;
	case 4:a.orderDetail();;
		break;
	case 5:a.customerList();
		break;
	case 6:a.checkQuentity();
		break;
	case 7:Main.main(null);
	     break;
		default:System.out.println("invalid");
	}
  }
}


