package DataBaseTables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	
	/**
	 * This class was created so that we will create all of the tables needed
	 * @param args , argument
	 * @throws SQLException , if there is sql problem
	 */
	public static void main(String[] args) throws SQLException {

		String driverName = "org.apache.derby.jdbc.ClientDriver";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found");
		}
		Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/MyCouponData");	
		Statement stmt = con.createStatement();
		
//		/**
//		 *  String that creates the COMPANY TABLE
//		 */
//		 String CompanyTable =
//		 "CREATE TABLE CompanyTable (ID int ,CompanyName varchar(20) NOT NULL,Password varchar(15) NOT NULL,Email varchar(50) NOT NULL,PRIMARY KEY (ID))";
//		 stmt.executeUpdate(CompanyTable);
//		/**
//		 *  String that creates the CustomerTable
//		 */
//		 String CustomerTable =
//		 "CREATE TABLE CustomerTable(ID int ,CustomerName varchar(20),Password varchar(15),PRIMARY KEY (ID))";
//		 stmt.executeUpdate(CustomerTable);
//		 /**
//		  * String that creates the CouponTable
//		  */
//		 String CouponTable = "CREATE TABLE CouponTable"+
//		 "(ID int NOT NULL,"+
//		 "Title varchar(20) NOT NULL,"+
//		 "St_Date date NOT NULL,"+
//		 "En_Date date NOT NULL,"+
//		 "Amount integer NOT NULL,"+
//		 "Type varchar(20) NOT NULL ," +
//		 "Messg varchar(20),"+
//		 "price double NOT NULL,"+
//		 "image varchar(20),"+
//		 "PRIMARY KEY (ID))";
//		 stmt.executeUpdate(CouponTable);
//		 
//		/**
//		 * String that creates the join table Customer_Coupon
//		 */
//		String customerCoupon = "CREATE TABLE CustomerCoupon (CustomerTable_ID  int , CouponTable_ID  int ,"
//				+ "PRIMARY KEY (CustomerTable_ID ,CouponTable_ID )" + ")";
//		stmt.executeUpdate(customerCoupon);
//		
//		
//		/**
//		 * String that creates the join table Company_Coupon
//		 */
//		String companyCoupon = "CREATE TABLE companyCoupon (CompanyTable_ID  int , CouponTable_ID  int ,"
//				+ "PRIMARY KEY (CompanyTable_ID ,CouponTable_ID )" + ")";
//		stmt.executeUpdate(companyCoupon);
//		
//		
//		 /**
//		  * Insert of the Coupon Table
//		  */
//		 String insertCompanyCoupon = "INSERT INTO companyCoupon Values(1,3)";
//		 String insertCustomerCoupon = "INSERT INTO CustomerCoupon Values(2,1)";
//		 
//		 
//		 /**
//		  *select from Table
//		  */
//		 String csSelect = "Select ID"
//		 		+ "FROM CustomerTable , CouponTable"
//		 		+ "Where CustomerTable.ID = CouponTable.ID";
//		 
//		 /**
//		  * deletes tables you desire
//		  */
//	String delete = "DROP TABLE companyCoupon";
//	stmt.executeUpdate(delete);

	
}	

}