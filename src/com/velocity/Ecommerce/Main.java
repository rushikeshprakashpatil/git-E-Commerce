package com.velocity.Ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class registration{
	private static Connection con;
    private static PreparedStatement ps;
	public static void setData() {
		Scanner sc=new Scanner(System.in);
		System.out.println("enter customer name");
		String name=sc.next();
		System.out.println("enter mobile number");
		String number=sc.next();
		System.out.println("enter email");
		String email=sc.next();
		System.out.println("enter password");
		String password=sc.next();
		
		try {
		 con=MyConnection.getConnection();
  ps=con.prepareStatement("insert into customerinfo(Email,Password,CustomerName,MobileNumber) values(?,?,?,?) ");
			ps.setString(1,email);
			ps.setString(2,password);
			ps.setString(3,name);
			ps.setString(4,number);
			ps.executeUpdate();
			System.out.println("Registration successful");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			Login.checkData();
		}	
	}
}
class Login{
	private static Connection con;
    private static PreparedStatement ps;
public static void checkData()  {
		Scanner sc=new Scanner(System.in);
		System.out.println(" for Login");
		System.out.println("Enter Email Id");
		String email=sc.next();
		System.out.println("enter password");
		String pass=sc.next();
	   try {
			con=MyConnection.getConnection();
			ps=con.prepareStatement("select Email,Password from customerinfo");
			ResultSet rs=ps.executeQuery();
			int count=0;
			while(rs.next()) {
				if(email.equalsIgnoreCase(rs.getString("Email")) && pass.equals(rs.getString("Password"))) {
					count++;
					break;
				}		
		   	}
			if(count>0) {
				System.out.println("Login successful");
			}
			else {
				Login.checkData();
			}
			
		 } 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
				ps.close();
			} 
			catch (Exception e2) {
				e2.printStackTrace();
			}
		 }
	   System.out.println("if you want to buy product from cart then press yes otherwise press no");
		String s=sc.next();
if(s.equalsIgnoreCase("yes")) {
			try {
				BuyProduct.getBuyProduct(email);
			} catch (SQLException e) {
				e.printStackTrace();
			}
  }
else {		
	   try {
		con=MyConnection.getConnection();
		ps=con.prepareStatement("select * from productinfo  Order by Price ");
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		    {
			System.out.print("productid:"+rs.getInt( "P_Id")+" ,");
			System.out.print("productname:"+rs.getString("P_Name")+" ,");
			System.out.print("description:"+rs.getString("P_Description")+" ,");
			System.out.print("price:"+rs.getInt("Price")+" ,");
			System.out.println("quantity:"+rs.getString("Quantity")+".");
			System.out.println("================***====================");
		    }
	     } 
	   catch (SQLException e) {
		e.printStackTrace();
	   }
	   finally {
		     try {
			  con.close();
			  ps.close();
	    	 }
		     catch (Exception e2) {
			e2.printStackTrace();
		    }
	      }
	    System.out.println("For buying select the product which will be added to cart ");
	    Cart.addCart(email);
     }
   }
}
class Cart{
	private static Connection con;
    private static PreparedStatement ps;
	public static void addCart(String email) {
		Scanner sc=new Scanner(System.in);
		System.out.println("select how many products you want to add to cart");
		int products=sc.nextInt();
		for(int i=1;i<=products;i++) {
		 System.out.println("enter product name");
		String productName=sc.next();
		con=MyConnection.getConnection();
		try {
			ps=con.prepareStatement("select P_Id from productinfo where"+" P_Name"+"="+"'"+productName+"'");
			ResultSet rs=ps.executeQuery();                              // space imp aahe ith.
			while(rs.next()) {
				int x=rs.getInt("P_id");
				ps=con.prepareStatement("insert into cart(Pro_id,email) values(?,?)");
				  ps.setInt(1, x);
				  ps.setString(2, email);
			    ps.executeUpdate();
				/*ps1=con.prepareStatement("insert into addtocart(Proid) values(?)");
				ps1.setInt(1,x);
			   int count=ps1.executeUpdate();
			   System.out.println(count+" product added to cart");*/
			}
		 	
		 } catch (SQLException e) {
			e.printStackTrace();
		 }	
	  }
		
    System.out.println("all products added successfully");
    System.out.println("============***=================");	    
	  try {
		ps=con.prepareStatement("select sum(Price) from productinfo inner join Cart on P_id=Pro_id where "+"cart.email"+"="+"'"+email+"'");
		ResultSet rs=ps.executeQuery();
	    while(rs.next()) {
	    	int sum=rs.getInt(1);
	    System.out.println(" total payable amount of added products is "+sum);
	    }
	  } catch (SQLException e) {
		  e.printStackTrace();
	   }
	  finally {
		  try {
			con.close();
			ps.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	  }
	  System.out.println("=================***=====================");
  	System.out.println("For Buying press >> B"); 
    String s=sc.next();
        if(s.equalsIgnoreCase("B"))
	   {
    	try {
			BuyProduct.getBuyProduct(email);
			
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
	   }
        else {
        	System.out.println("thank you for adding products to cart");
        }
    
	}
}
public class Main {
    
	public static void main(String[] args) throws SQLException  {
		Scanner sc=new Scanner(System.in);
		System.out.println("for user press 1 & for admin press 2");
		int i=sc.nextInt();
	if(i==1)
	{
		System.out.println("for New account press yes & for Old Account press no ");
		String st=sc.next();
		if(st.equalsIgnoreCase("yes")) {
			registration.setData();
		}
		if(st.equalsIgnoreCase("no")) {
			Login.checkData();
		}
	}
	else if(i==2)
	   {
			System.out.println("enter admin id");
			int id=sc.nextInt();
			if(id==10) {
				Admin.adminDetails();
			   }
	        else {  
				System.out.println("invalid input");
				Main.main(null);
		      }	
		}
	}
    
}
