package DataBaseTables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Classes.Coupon;
import Classes.Customer;
import exceptionClass.CouponException;
/**
 * The Class that implements CustomerDAO and is responsible for retrieving the data from the Derby Database:
 *
 */
public class CustomerDBDAO implements CustomerDAO{
	ConnectionPool pool = ConnectionPool.getInstance();
	PreparedStatement statement;
	ResultSet rs;
	
	/**
	 * Constructor 
	 * @throws CouponException if there is any problem
	 */
	public CustomerDBDAO() throws CouponException{
		super();
	}

	/**
	 * creating a customer and entering it
	 *	CustomerTable with his ID, name, password.
	 * in the end closing  connections and statements
	 */
	@Override
	public void createCustomer(Customer customer) throws CouponException {
		Connection con = pool.getConnection();
		String createCoupon = "INSERT INTO CustomerTable Values(?,?,?)";
		try {
			statement = con.prepareStatement(createCoupon);
			statement.setLong(1, customer.getID());
			statement.setString(2, customer.getCustName());
			statement.setString(3, customer.getPassword());
			statement.executeUpdate(); 
		} catch (SQLException e) {
			throw new CouponException("Problem connecting to the database.");
		} catch (NullPointerException e) {
			throw new CouponException("Check if every parameter is filled");
		}
		finally{
		pool.returnConnection(con);
		 try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement.");};
		}
	}
	
	/**
	*Removing the customer from CustomerCoupon Join table 
	* removing him from the CustomerTable
	* in the end closing connections and statements
	* roll back method for if something goes wrong, and if so 
	* it will make no changes.
	*/
	
	@Override
	public void removeCustomer(Customer customer) throws CouponException {
		Connection con = pool.getConnection();
		Statement statement1 = null;
		Statement statement2 = null;
		String removeCustomerCoupons = "DELETE from CustomerCoupon where CustomerTable_ID =" +customer.getID();
		String removeCustomer = "DELETE FROM CustomerTable WHERE ID ="+ customer.getID();
		try {
			statement1 = con.createStatement();
			statement2 = con.createStatement();
			con.setAutoCommit(false);
			statement1.execute(removeCustomerCoupons);
			statement2.execute(removeCustomer);
			con.commit();
		} catch (SQLException e) {
			try {con.rollback();} catch (SQLException e1) {throw new CouponException("Problem try again.");}
		}
		finally{
			pool.returnConnection(con);
			try {con.setAutoCommit(true);}catch (SQLException e) {throw new CouponException("problem try again.");}
			try {if (statement1 != null) statement1.close();}catch (SQLException e) {throw new CouponException("problem closing statement.");}			
			try {if (statement2 != null) statement2.close();}catch (SQLException e) {throw new CouponException("problem closing statement.");}			
		}
	}



	/**	updating the customer coupons.
	*here we check if all the coupon got updated, if not we don't change it
	*with roll back method
	*/	
	@Override
	public void updateCustomer(Customer customer) throws CouponException {
		Connection con = pool.getConnection();
		try {
			con.setAutoCommit(false);
			String updateCustomer = "UPDATE CustomerTable set ID=?, CustomerName = ?, Password = ? Where ID = " + customer.getID();	
			statement = con.prepareStatement(updateCustomer);
			statement.setLong(1, customer.getID());
			statement.setString(2, customer.getCustName());
			statement.setString(3, customer.getPassword());
			statement.executeUpdate();
			con.commit();
			} catch (SQLException e) {
				try {con.rollback();} catch (SQLException e1) {throw new CouponException("problem try again.");}
			}
			finally{
				pool.returnConnection(con);
				try {con.setAutoCommit(true);} catch (SQLException e) {throw new CouponException("problem try again.");}
				try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement.");};
			}
		}

	
	
	/**	With the ID given it return the customer
	 * with all of his details.
	 * in the end closing connections, statements and result set
	 */ 
	@Override
	public Customer getCustomer(int ID) throws CouponException {
		Customer customer = new Customer();
		Connection con = pool.getConnection();
		String getCustomer = "SELECT  CustomerName , Password FROM CustomerTable where ID ="+ ID;
		try {
		statement = con.prepareStatement(getCustomer);
		rs= statement.executeQuery();
		while(rs.next()){
			customer.setID(ID);
			customer.setCustName(rs.getString(1));
			customer.setPassword(rs.getString(2));
		}
		} catch (SQLException e) {
			throw new CouponException("problem try again.");
		}
		finally{
			pool.returnConnection(con);
			 try { if (rs != null) rs.close(); } catch (Exception e) {throw new CouponException("problem closing resultset.");};
			 try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement.");};
		}
		return customer; 
	}
	
	

	/**Getting all of the customers from the company list
	 * and in the end closing connections, statements and result set
	 */
	@Override
	public Collection<Customer> getAllCustomer() throws CouponException {
		Connection con = pool.getConnection();
		List<Customer> customers = new ArrayList<Customer>();
		String getAllCustomer = "SELECT ID , CustomerName , Password FROM CustomerTable";
		try {
			statement = con.prepareStatement(getAllCustomer);
			 rs = statement.executeQuery();
			while(rs.next()){
				Customer customer = new Customer();
				customer.setID(rs.getLong(1));
				customer.setCustName(rs.getString(2));
				customer.setPassword(rs.getString(3));
				customers.add(customer);
			}
		} catch (SQLException e) {
			throw new CouponException("problem try again.");
		}
		finally{
			pool.returnConnection(con);
			 try { if (rs != null) rs.close(); } catch (Exception e) {throw new CouponException("problem closing resultset.");};
			 try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement.");};
		}
		return customers;
	}

	/**getCoupons returns a collection of  coupons the customer has
	 * and in the end closing connections, statements and result set
	 */
	@Override
	public Collection<Coupon> getCoupons(Customer customer) throws CouponException {
		List<Coupon> customerCouponList = new ArrayList<Coupon> ();
		CouponDBDAO couponMain = new CouponDBDAO();
		Connection con = pool.getConnection();
		String getCoupons = "SELECT CouponTable_ID FROM CustomerCoupon where CustomerTable_ID =" + customer.getID();
		try {
			statement = con.prepareStatement(getCoupons);
			rs = statement.executeQuery();
			while(rs.next()){
				customerCouponList.add(couponMain.getCoupon(rs.getInt(1)));
			}
		} catch (SQLException e) {
			throw new CouponException("can't get the Customers coupons");
		}
		finally{
			pool.returnConnection(con);
			 try { if (rs != null) rs.close(); } catch (Exception e) {throw new CouponException("problem closing resultset.");};
			 try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement.");};
		}
		return customerCouponList;
	}

	/**checking if the name and password of the customer exist
	 * in database , if so then it will return Customer object
	 * if not , it will pop a message to register and then log in
	 * @param custName , customers name 
	 * @param password , customers password
	 * @throws CouponException if there is any problem logging in
	 * @return customer object
	 */
	public Customer login(String custName , String password) throws CouponException {
		Customer cust = new Customer();
		Connection con = pool.getConnection();
		List<Customer> customers = new ArrayList<Customer>();
		String getAllCustomer = "SELECT ID , CustomerName , Password FROM CustomerTable";
		try {
			statement = con.prepareStatement(getAllCustomer);
			 rs = statement.executeQuery();
			while(rs.next()){
				Customer customer = new Customer();
				customer.setID(rs.getLong(1));
				customer.setCustName(rs.getString(2));
				customer.setPassword(rs.getString(3));
				customers.add(customer);
			}
		} catch (SQLException e) {
			throw new CouponException("problem try again.");
		}
		finally{
			pool.returnConnection(con);
			 try { if (rs != null) rs.close(); } catch (Exception e) {throw new CouponException("problem closing resultset.");};
			 try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement.");};
		}
		for(int i = 0; i<customers.size(); i++){
			if(customers.get(i).getCustName().equals(custName) && customers.get(i).getPassword().equals(password)){
				cust = customers.get(i);
				return cust;
			}
		}
		return cust;
		
		
	}


}
