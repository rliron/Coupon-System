package DataBaseTables;


import java.sql.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.sql.PreparedStatement;

import exceptionClass.CouponException;
import Classes.Coupon;
import Classes.Customer;
import DataBaseTables.CouponDAO;


public class CouponDBDAO implements CouponDAO {
	ConnectionPool pool = ConnectionPool.getInstance();
	PreparedStatement statement;
	ResultSet rs;



public CouponDBDAO() throws CouponException{
	super();

}
/**
* Creates coupons in  Database
*/
public void createCoupon(Coupon coupon) throws CouponException {
	Connection con = pool.getConnection();
		String createCoupon = "INSERT INTO CouponTable Values(?,?,?,?,?,?,?,?,?)";
		try {
			statement = con.prepareStatement(createCoupon);
			statement.setLong(1, coupon.getID());
			statement.setString(2, coupon.getTitle());
			java.sql.Date StartDate = new java.sql.Date(coupon.getStartDate().getTime());// turning java.util.date to sql date so the table can accept it
			statement.setDate(3, StartDate);
			java.sql.Date EndDate = new java.sql.Date(coupon.getEndDate().getTime());// turning java.util.date to sql date so the table can accept it
			statement.setDate(4,  EndDate);
			statement.setInt(5, coupon.getAmount());
			statement.setString(6, coupon.getType());
			statement.setString(7, coupon.getMessage());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new CouponException("Problem creating a coupon , make sure that ID doesnt exists already");
		} catch (NullPointerException e) {
			throw new CouponException("Check if every parameter is filled");
		} finally {
			pool.returnConnection(con);
		    try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement.");};
		}
}

	/**Method that inserts customer and the coupon he bought into
	 *  a Join table CustomerCoupon
	 * @param customer we want to put in a join table
	 * @param coupon we want to put in a join table
	 * @throws CouponException if there is any problem
	 */

	public void JoinCouponTables(Customer customer, Coupon coupon) throws CouponException {
		Connection con = pool.getConnection();
		String createCoupon = "INSERT INTO CustomerCoupon Values(?,?)";
		try{
			statement = con.prepareStatement(createCoupon);
			statement.setLong(1, customer.getID());
			statement.setLong(2, coupon.getID());
			statement.executeUpdate();
			}catch(Exception e){
				new CouponException("problem");
			}finally {
				pool.returnConnection(con);
			    try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement.");};
			}
	}

	
	/**
	 * This function removes the coupon from the database by its ID
	 * from the CustomerCoupon table 
	 * companyCoupon table 
	 * and the CouponTable table
	 */
public void removeCoupon(Coupon coupon) throws CouponException{
	Connection con = pool.getConnection();
	
		String removeCouponJoinTableCustomer = "DELETE FROM CustomerCoupon WHERE CouponTable_ID ="+ coupon.getID();
		String removeCouponJoinTableCompany = "DELETE FROM companyCoupon WHERE CouponTable_ID ="+ coupon.getID();
		String removeCoupon = "DELETE FROM CouponTable WHERE ID ="+ coupon.getID();
		Statement statement1 = null;
		Statement statement2 = null;
		Statement statement3 = null;
		try {
			con.setAutoCommit(false);
			statement1 = con.createStatement();
			statement2 = con.createStatement();
			statement3 = con.createStatement();
			statement1.execute(removeCouponJoinTableCustomer);
			statement2.execute(removeCouponJoinTableCompany);
			statement3.execute(removeCoupon);
			con.commit();
		} catch (SQLException e) {
			try {con.rollback();} catch (SQLException e1) {throw new CouponException("problem try again");}

		}
		finally{
			pool.returnConnection(con);
			try {	con.setAutoCommit(true);} catch (SQLException e){throw new CouponException("problem");}
			try { if (statement1 != null) statement1.close(); } catch (Exception e) {throw new CouponException("problem closing statement");};
			try { if (statement2 != null) statement2.close(); } catch (Exception e) {throw new CouponException("problem closing statement");};
			try { if (statement3 != null) statement3.close(); } catch (Exception e) {throw new CouponException("problem closing statement");};
		}
	}

	/**
	 * This function updates all of the coupon details by its ID 
	 */
@Override
public void updateCoupon(Coupon coupon) throws CouponException {
	Connection con = pool.getConnection();
	String updateCoupon = "UPDATE CouponTable SET En_Date=?,price=? WHERE ID=" + coupon.getID();
		try {
			con.setAutoCommit(false);
			statement = con.prepareStatement(updateCoupon);
			java.sql.Date EndDate = new java.sql.Date(coupon.getEndDate().getTime());
			statement.setDate(1, EndDate);
			statement.setDouble(2, coupon.getPrice());
			statement.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {con.rollback();} catch (SQLException e1) {throw new CouponException("problem try again");}
		}
		finally{
			pool.returnConnection(con);
			try {con.setAutoCommit(true);} catch (SQLException e){throw new CouponException("problem");}
			try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement");};
		}
}

	/**
	 * The function gets a coupon by its id
	 * and returns a coupon object
	 */
@Override
public Coupon getCoupon(int ID) throws CouponException {
	Coupon coupon = new Coupon();
	Connection con = pool.getConnection();
	String getCoupon= "SELECT   Title,St_Date,En_Date,Amount,image,Type,Messg,price  FROM CouponTable WHERE ID=" + ID;
	try {
		statement = con.prepareStatement(getCoupon);
		 rs = statement.executeQuery();
		while(rs.next()){
			coupon.setID(ID);
			coupon.setTitle(rs.getString(1));
			Date date = (rs.getDate(2)); // making it equal to date
			String calendar = coupon.getFt().format(date);// converting date to string
			coupon.setStartDate(calendar);// setting StartDate value with string as it asks
			Date date2 = (rs.getDate(3));
			String calendar2 = coupon.getFt().format(date2);
			coupon.setEndDate(calendar2);
			coupon.setAmount(rs.getInt(4));
			coupon.setImage(rs.getString(5));
			String typeString =(rs.getString(6));//making it equal to string
			CouponType type = CouponType.valueOf(typeString);//converting string to Type object
			coupon.setType(type);
			coupon.setMessage(rs.getString(7));
			coupon.setPrice(rs.getDouble(8));
		}
		} catch (SQLException e) {
			throw new CouponException("problem");
		}finally{
			pool.returnConnection(con);
			try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement");};
			try { if (rs != null) rs.close(); } catch (Exception e) {throw new CouponException("problem closing resultset");};
			}
	return  coupon;	
}

	/**
	 * The function a collection of all coupons there is
	 * the database
	 */
public Collection<Coupon> getAllCoupon() throws CouponException {
	List<Coupon> couponList = new ArrayList<Coupon>();
	Connection con = pool.getConnection();
	String getAllCoupon= "SELECT   Title,St_Date,En_Date,Amount,image,Type,Messg,price ,ID FROM CouponTable";
	try {
		statement = con.prepareStatement(getAllCoupon);
		 rs = statement.executeQuery();
		while(rs.next()){
			Coupon coupon = new Coupon();
			coupon.setID(rs.getLong(9));
			coupon.setTitle(rs.getString(1));
			Date date = (rs.getDate(2)); // making it equal to date
			String calendar = coupon.getFt().format(date);// converting date to string
			coupon.setStartDate(calendar);// setting StartDate value with string as it asks
			Date date2 = (rs.getDate(3));
			String calendar2 = coupon.getFt().format(date2);
			coupon.setEndDate(calendar2);
			coupon.setAmount(rs.getInt(4));
			coupon.setImage(rs.getString(5));
			String typeString =(rs.getString(6));//making it equal to string
			CouponType type = CouponType.valueOf(typeString);//converting string to Type object
			coupon.setType(type);
			coupon.setMessage(rs.getString(7));
			coupon.setPrice(rs.getDouble(8));
			couponList.add(coupon);
		}
		} catch (SQLException e) {
			throw new CouponException("problem");
		}finally{
			pool.returnConnection(con);
			try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement");};
			try { if (rs != null) rs.close(); } catch (Exception e) {throw new CouponException("problem closing resultset");};
			}
	
	return couponList;
}


	/**
	 * The function a collection of all coupons there is the database
	 * by its type
	 */
@Override
public Collection<Coupon> getCouponByType(CouponType type) throws CouponException {
	List<Coupon> couponTypeList = new ArrayList<Coupon>();
	Connection con = pool.getConnection();
	String getAllCouponType= "SELECT   Title,St_Date,En_Date,Amount,image,ID,Messg,price   FROM CouponTable WHERE Type='"+type.toString()+"'";
	try {
		statement = con.prepareStatement(getAllCouponType);
		 rs = statement.executeQuery();
		while(rs.next()){
			Coupon coupon = new Coupon();
			coupon.setID(rs.getLong(6));
			coupon.setTitle(rs.getString(1));
			Date date = (rs.getDate(2)); // making it equal to date
			String calendar = coupon.getFt().format(date);// converting date to string
			coupon.setStartDate(calendar);// setting StartDate value with string as it asks
			Date date2 = (rs.getDate(3));
			String calendar2 = coupon.getFt().format(date2);
			coupon.setEndDate(calendar2);
			coupon.setAmount(rs.getInt(4));
			coupon.setImage(rs.getString(5));
			String typeString =(type.toString());//making it equal to string
			CouponType cptype = CouponType.valueOf(typeString);//converting string to Type object
			coupon.setType(cptype);
			coupon.setMessage(rs.getString(7));
			coupon.setPrice(rs.getDouble(8));
			couponTypeList.add(coupon);
		}
	} catch (SQLException e) {
		throw new CouponException("problem");
	}finally{
		pool.returnConnection(con);
		try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement");};
		try { if (rs != null) rs.close(); } catch (Exception e) {throw new CouponException("problem closing resultset");};
		}
	return couponTypeList;
}	
}


