import java.sql.*;
import java.util.Scanner;
import java.math.BigInteger;
import java.util.InputMismatchException;
public class Assignment5 {

	public static void main(String[] argv) throws Exception {
		 Scanner sc = new Scanner(System.in);
		 String Country,Email,date;
		 BigInteger contact;
		 try
		 {
			 while(true)
			 {
				 System.out.println("Enter your choice");
				 System.out.println("1.List All Customers\n2. Search Customer based on Email"
				 		+ "\n3.Delete Customer Table\n4.Update Customer Table \n"
		 		+ "5.Insert Customer Details\n6.Business Goal 1\n7.Business Goal 3\n8.Business Goal 5\n9.Business Goal 7\n"
		 		+ "10.Business Goal 11");			 
				 int choice = sc.nextInt();
				 
				 switch(choice)
				 {
				 	case 1:ListCustomers();
				 		break;
				 	case 2: System.out.println("Enter Email of Customer to search");
				 		String searchkey=sc.next();
				 		SearchCustomer(searchkey);
				 		break;
				 	case 3: System.out.println("Enter Customer ID to be deleted");		 		
			 		 		String deletekey=sc.next();
			 		 		DeleteCustomer(deletekey);
			 		 		break;
				 	case 4: System.out.println("Enter Customer ID to be updated");
				 			String customerId=sc.next();
				 			System.out.println("Enter the following details to update the record");
				 			System.out.println("Enter Contact Number");				 			
				 			contact=new BigInteger(sc.next());
				 			System.out.println("Enter country of Customer");
				 			Country=sc.next();
//				 			System.out.println("Enter email of Customer");
//				 			Email=sc.next();
				 			System.out.println("Enter DOB of Customer");
				 			date=sc.next().toString(); 
				 			UpdateCustomer(contact,customerId,Country,date);
				 			break;
				 	case 5: System.out.println("Enter below details of Customer to be inserted");
				 			System.out.println("");
				 			System.out.println("Enter Contact Number");
				 			contact=new BigInteger(sc.next());
				 			System.out.println("Enter Country of Customer");
				 			Country=sc.next(); 
				 			System.out.println("Enter email of Customer");
				 			Email=sc.next();
				 			System.out.println("Enter DOB of Customer");
				 			date=sc.next().toString();
				 			InsertCustomer(contact,Country,Email,date);
				 			break;
				 	case 6: BusinessGoal1();
				 			break;
				 	case 7: BusinessGoal3();
		 					break;
					case 8: BusinessGoal5();
 					break;
					case 9: BusinessGoal7();
						break;
					case 10: BusinessGoal11();
						break;
				 	default: return;			 	
				 }
			 }
		 }
		 catch(NumberFormatException nx)
		 {
			 System.out.println("Wrong input entered");
		 }
		 catch(InputMismatchException im)
		 {
			 System.out.println("Wrong input entered");
		 }
		 catch(Exception ex)
		 {
			 throw ex;
		 }
		 finally {
			 	sc.close();
		 }
	}
	public static Connection getConnection()
	{
	System.out.println("-------- Oracle JDBC Connection Testing ------");
	 
	try {

		Class.forName("oracle.jdbc.driver.OracleDriver");

	} catch (ClassNotFoundException e) {

		System.out.println("Where is your Oracle JDBC Driver?");
		e.printStackTrace();
		return null;

	}

	System.out.println("Oracle JDBC Driver Registered!");

	Connection connection = null;

	try {

//below include your login and your password
        connection = DriverManager.getConnection("jdbc:oracle:thin:@acaddbprod-2.uta.edu:1523/pcse1p.data.uta.edu", "sxb4167", "Sai10111994Him");
	} catch (SQLException e) {
		System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return null;

	}
	return connection;
	}
	public static void  ListCustomers()
	{

		Connection connection=getConnection();
		if (connection != null) {
            System.out.println("Printing Customer Details from table  F21_S003_4_CUSTOMERS stored on omega");
		} else {
			System.out.println("Failed to make connection!");
		}
        try {
        	Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "select * from F21_S003_4_CUSTOMERS order by CUSTOMERID asc";           
	       ResultSet rs = stmt.executeQuery(query);
	       System.out.println("CustomerId  ContactNumber  Country  Email  DateOfBirth");
	       System.out.println("------------------------------------------------------------");
	       while (rs.next())
	       {
	         System.out.println(rs.getString("CustomerId")+"    "+rs.getString("ContactNumber")+"    "+rs.getString("Country")+"    "+rs.getString("Email")+"    "+rs.getString("DateOfBirth"));
	       }
	       System.out.println("--------------------------------------------------------");
	       rs.close();
	       stmt.close();
            connection.close();
        }
        catch (SQLException e) {
 
			System.out.println("erro in accessing the relation");
			e.printStackTrace();
			return;
 
		}
	}
	public static void SearchCustomer(String searchkey)
	{
		Connection connection=getConnection();
		if (connection != null) {
            System.out.println("Searching the customer");
		} else {
			System.out.println("Failed to make connection!");
		}
		try {
        	Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "select * from F21_S003_4_CUSTOMERS where EMAIL="+"'"+searchkey+"'";           
	       ResultSet rs = stmt.executeQuery(query);
	       System.out.println("CustomerId  ContactNumber  Country  Email  DateOfBirth");
	       System.out.println("------------------------------------------------------------");
	       int flag=0;
	       while (rs.next())
	       {
	    	   flag=1;
	         System.out.println(rs.getString("CustomerId")+"    "+rs.getString("ContactNumber")+"    "+rs.getString("Country")+"    "+rs.getString("Email")+"    "+rs.getString("DateOfBirth"));
	       }
	       if(flag==0)
	    	   System.out.println("Record not found");
	       System.out.println("--------------------------------------------------------");
	       rs.close();
	       stmt.close();
            connection.close();
        }
        catch (SQLException e) {
 
			System.out.println("erro in accessing the relation");
			e.printStackTrace();
			return;
 
		}
	}
	
 
		public static void DeleteCustomer(String deletekey)
		{
		
			try(Connection connection=getConnection()) {
	        	Statement stmt = connection.createStatement();
	            String query = "delete from F21_S003_4_CUSTOMERS where CUSTOMERID="+"'"+deletekey+"'";           
	            int rowsEffected= stmt.executeUpdate(query);
	            if(rowsEffected>0)
	            {
	            	System.out.println("Record Deleted Successfully");
	            }
	            else
	            {
	            	System.out.println("Entered Customer ID is not valid");
	            }
	            System.out.println();
	            System.out.println("-----------------------------------------------------------");
		        stmt.close();
	            connection.close();
	        }
	        catch (SQLException e) {
	 
				System.out.println("erro in accessing the relation");
				e.printStackTrace();
				return;
	 
			}
		}
	//Update the record
	public static void UpdateCustomer(BigInteger contact,String customer,String country,String date)
	{
	
		try(Connection connection=getConnection()) {
        	Statement stmt = connection.createStatement();
            String query = "update F21_S003_4_CUSTOMERS set CONTACTNUMBER="+contact+""
            		+ ",COUNTRY='"+country+"',DATEOFBIRTH='"+date+"' where CUSTOMERID="+"'"+customer+"'";           
            int rowsEffected= stmt.executeUpdate(query);
            if(rowsEffected>0)
            {
            	System.out.println("Record Updated Successfully");
            }
            else
            {
            	System.out.println("Please enter valid Customer ID");
            }
            System.out.println();
            System.out.println("-----------------------------------------------------------");
	        stmt.close();
            connection.close();
        }
        catch (SQLException e) {
 
			System.out.println("erro in accessing the relation");
			e.printStackTrace();
			return;
 
		}
	}
	
	public static void InsertCustomer(BigInteger contact,String country,String Email,String date)
	{
		String lastid=getLastCustomerId();
		try(Connection connection=getConnection()) {
        	Statement stmt = connection.createStatement();
            String query = "insert into F21_S003_4_CUSTOMERS(CUSTOMERID,CONTACTNUMBER,COUNTRY,EMAIL,DATEOFBIRTH) values('"+lastid+"',"
            		 +contact+",'"+country+"','"+Email+"',"+"'"+date+"')";
            int rowsEffected=stmt.executeUpdate(query);
            if(rowsEffected>0)
            {
            	System.out.println("Record Inserted Successfully");
            }
            System.out.println("-----------------------------------------------------------");
	        stmt.close();
            connection.close();
        }
        catch (SQLException e) {
 
			System.out.println("erro in accessing the relation");
			e.printStackTrace();
			return;
 
		}
		
	}
	public static String getLastCustomerId()
	{
		Connection connection=getConnection();
		String Customer=null;
		if (connection == null) {
			System.out.println("Failed to make connection!");
		}
        try {
        	Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "select CUSTOMERID from F21_S003_4_CUSTOMERS "
            		+ "order by CUSTOMERID desc fetch first row only";           
	       ResultSet rs = stmt.executeQuery(query);
	       while (rs.next())
	       {
	    	   Customer= rs.getString("CustomerId");
	       }
	      Customer=Customer.replace("CUST","");
	      Integer cust=Integer.parseInt(Customer);
	      cust++;
	      Customer="CUST"+String.format("%05d", cust);
	       rs.close();
	       stmt.close();
           connection.close();
        }
        catch (SQLException e) {
 
			System.out.println("erro in accessing the relation");
			e.printStackTrace();
			return null;
 
		}
        return Customer;
	}
	public static void BusinessGoal1()
	{
		Connection connection=getConnection();
		if (connection == null) {
			System.out.println("Failed to make connection!");
		}
        try {
        	Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "		select CON.GENRE as Genre,lan.LANGUAGE, COUNT(st.STREAMID) as ViewershipRate"
            		+ " from F21_S003_4_CONTENT CON"
            		+ " inner join"
            		+ " F21_S003_4_CONTENT_LANGUAGE lan"
            		+ " on lan.CONTENT_ID=CON.CONTENTID"
            		+ " inner join F21_S003_4_STREAM st"
            		+ " on st.CONTENTID=con.CONTENTID"
            		+ " group by CON.GENRE,lan.LANGUAGE"
            		+ " having  COUNT(st.STREAMID)>=10"
            		+ " order  by VIEWERSHIPRATE DESC";           
	       ResultSet rs = stmt.executeQuery(query);
	       System.out.println("Genre  LANGUAGE  ViewershipRate");
	       System.out.println("------------------------------------------------------------");
	       while (rs.next())
	       {
	         System.out.println(rs.getString("Genre")+"    "+rs.getString("LANGUAGE")+"    "+rs.getString("ViewershipRate"));
	       }
	       System.out.println("--------------------------------------------------------");
	       rs.close();
	       stmt.close();
            connection.close();
        }
        catch (SQLException e) {
 
			System.out.println("erro in accessing the relation");
			e.printStackTrace();
			return;
 
		}
	}
	public static void BusinessGoal3()
	{
		Connection connection=getConnection();
		if (connection == null) {
			System.out.println("Failed to make connection!");
		}
        try {
        	Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "select Country,count(sub.CustomerId) as InactiveRenewal from "
            		+ "F21_S003_4_SUBSCRIPTION sub"
            		+ " inner join \r\n"
            		+ " F21_S003_4_CUSTOMERS cus"
            		+ " on sub.CustomerId=cus.customerId"
            		+ " where"
            		+ " sysDate-sub.SubscriptionStartDate>365"
            		+ " group by rollup(cus.Country) order  by count(sub.CustomerId) desc";           
	       ResultSet rs = stmt.executeQuery(query);
	       System.out.println("Country  InactiveRenewal");
	       System.out.println("------------------------------------------------------------");
	       while (rs.next())
	       {
	    	   if(rs.getString("Country")!=null)
	         System.out.println(rs.getString("Country")+"    "+rs.getString("InactiveRenewal"));
	    	   if(rs.getString("Country")==null)
	  	         System.out.println("         "+rs.getString("InactiveRenewal"));
	       }
	       System.out.println("--------------------------------------------------------");
	       rs.close();
	       stmt.close();
            connection.close();
        }
        catch (SQLException e) {
 
			System.out.println("erro in accessing the relation");
			e.printStackTrace();
			return;
 
		}
	}
	public static void 	BusinessGoal5()
	{
		Connection connection=getConnection();
		if (connection == null) {
			System.out.println("Failed to make connection!");
		}
        try {
        	Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "with cte as("
            		+ "select  StreamCount,Genre,Title, rank() OVER (PARTITION BY genre ORDER BY StreamCount desc)as  Rank from("
            		+ "select "
            		+ "Distinct count(st.StreamId) over (partition by con.genre,con.Title)as StreamCount,"
            		+ "con.Genre as Genre,con.Title"
            		+ " from F21_S003_4_CONTENT con"
            		+ " inner join F21_S003_4_STREAM st"
            		+ " on st.ContentId=con.ContentId))\r\n"
            		+ "select Genre,Title,StreamCount from cte where rank=1 order by StreamCount desc ";           
	       ResultSet rs = stmt.executeQuery(query);
	       System.out.println("Genre   Title    StreamCount ");
	       System.out.println("------------------------------------------------------------");
	       while (rs.next())
	       {
	         System.out.println(rs.getString("Genre")+"    "+rs.getString("Title")+"    "+rs.getString("StreamCount"));
	       }
	       System.out.println("--------------------------------------------------------");
	       rs.close();
	       stmt.close();
            connection.close();
        }
        catch (SQLException e) {
 
			System.out.println("erro in accessing the relation");
			e.printStackTrace();
			return;
 
		}
	}
	public static void 	BusinessGoal7()
	{
		Connection connection=getConnection();
		if (connection == null) {
			System.out.println("Failed to make connection!");
		}
        try {
        	Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "select DeviceType,AgeGroup,count(DeviceType) as DeviceCount from \r\n"
            		+ "(select  dev.DeviceType\r\n"
            		+ ",case when  trunc(months_between(sysdate,cus.DateofBirth)/12) <=18\r\n"
            		+ "then \r\n"
            		+ "'Teen Age'\r\n"
            		+ "when  trunc(months_between(sysdate,cus.DateofBirth)/12) <=45 and trunc(months_between(sysdate,cus.DateofBirth)/12) >18 then\r\n"
            		+ "'Adults'\r\n"
            		+ "when trunc(months_between(sysdate,cus.DateofBirth)/12) >45 then\r\n"
            		+ "'Senior Adults'   end as AgeGroup\r\n"
            		+ "from\r\n"
            		+ "F21_S003_4_DEVICES dev \r\n"
            		+ "inner join\r\n"
            		+ "F21_S003_4_CUSTOMERS cus\r\n"
            		+ "on\r\n"
            		+ "dev.CustomerId=cus.CustomerId)\r\n"
            		+ "group by DeviceType,AgeGroup\r\n"
            		+ "order by DeviceCount desc";           
	       ResultSet rs = stmt.executeQuery(query);
	       System.out.println("DeviceType   AgeGroup    DeviceCount ");
	       System.out.println("------------------------------------------------------------");
	       while (rs.next())
	       {
	         System.out.println(rs.getString("DeviceType")+"    "+rs.getString("AgeGroup")+"    "+rs.getString("DeviceCount"));
	       }
	       System.out.println("--------------------------------------------------------");
	       rs.close();
	       stmt.close();
            connection.close();
        }
        catch (SQLException e) {
 
			System.out.println("erro in accessing the relation");
			e.printStackTrace();
			return;
 
		}
	}
	public static void  BusinessGoal11()
	{
		Connection connection=getConnection();
		if (connection == null) {
			System.out.println("Failed to make connection!");
		}
        try {
        	Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "select con.Genre as Genre,lan.Language, Count(st.StreamId) as ViewershipRate\r\n"
            		+ "from F21_S003_4_CONTENT con\r\n"
            		+ "inner join\r\n"
            		+ "F21_S003_4_CONTENT_LANGUAGE lan\r\n"
            		+ "on lan.Content_Id=con.ContentId\r\n"
            		+ "inner join F21_S003_4_STREAM st\r\n"
            		+ "on st.ContentId=con.ContentId\r\n"
            		+ "group by cube(con.genre,lan.language)";          
	       ResultSet rs = stmt.executeQuery(query);
	       System.out.println("Genre   Language    ViewershipRate ");
	       System.out.println("------------------------------------------------------------");
	       while (rs.next())
	       {	    	   
	         System.out.println((rs.getString("Genre")==null?"   ":rs.getString("Genre"))+"    "+(rs.getString("Language")==null?"    " :rs.getString("Language") )+"    "+rs.getString("ViewershipRate"));
	       }
	       System.out.println("--------------------------------------------------------");
	       rs.close();
	       stmt.close();
            connection.close();
        }
        catch (SQLException e) {
 
			System.out.println("erro in accessing the relation");
			e.printStackTrace();
			return;
 
		}
	}
}

