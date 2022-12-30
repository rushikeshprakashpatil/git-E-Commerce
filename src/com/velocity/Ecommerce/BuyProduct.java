package com.velocity.Ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BuyProduct
 {

	private static Connection con=null;
	private static PreparedStatement ps=null;
    
	public static void getBuyProduct(String email) throws SQLException
   {
		   Scanner sc=new Scanner(System.in);
		     con=MyConnection.getConnection();
	   try
	   {
		   
		   ps= con.prepareStatement("select pro_Id from Cart where email=?");
		   ps.setString(1, email);
		   ResultSet rs= ps.executeQuery();
		   System.out.println("The list of Product Id  you added in to Cart >> ");
		   while(rs.next())
		   {
			  
			   System.out.println("Id :-"+rs.getInt(1));   
		   }   
		}
	   catch(Exception e)
	    {
		 e.printStackTrace();
        }
		   System.out.println("How many Products do you want to Buy");
		   int no=sc.nextInt();
		   int pro_id=0;
		   int sum=0;
		   int buyid=0;
		   for(int i=1;i<=no;i++)
			   {
				   System.out.println("Select Id of Product from Cart that you want to Buy!!");
				   pro_id=sc.nextInt();
				   QuantityUpdate.quantityupdate(pro_id);
				   ps= con.prepareStatement("insert into BuyProduct(pro_Id,email) values(?,?)");
				   ps.setInt(1, pro_id);
				   ps.setString(2, email);
				   ps.executeUpdate();
				   ps=con.prepareStatement("select BuyProId from BuyProduct where pro_Id=? ");
				   ps.setInt(1,  pro_id);
				   ResultSet rst=ps.executeQuery();
				       
			       while(rst.next())
			   	         {
			   		        buyid=rst.getInt(1);  
			   	         }
			        ps=con.prepareStatement("select Price from BuyProduct inner join productinfo on BuyProduct.pro_Id=productinfo.p_Id where email=? and buyproid=?");
					     ps.setString(1,email);
					     ps.setInt(2, buyid);
					 ResultSet rs=ps.executeQuery();
				     
				     while(rs.next())
				   	      {
				   		   sum=sum+rs.getInt(1);
				   	      }
			      }
		   try {
			   System.out.println("Your total Payble Amount is >>> "+sum);
			   System.out.println("your payment is successful!!");
			   System.out.println("thank you...");
		       } 
		   finally 
		   {
			ps.close();
			con.close();
		   }
		   System.out.println("for continue press 1 for close application press 2");
		   int i=sc.nextInt();
		           
		   if(i==1) {
			   Main.main(null);
		   }
		   else {
			   System.exit(0);
		   }
		
	    
     }
}
