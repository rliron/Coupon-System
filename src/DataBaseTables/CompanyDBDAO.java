package DataBaseTables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Classes.Company;
import Classes.Coupon;
import exceptionClass.CouponException;

public class CompanyDBDAO implements CompanyDAO {
	ConnectionPool pool = ConnectionPool.getInstance();
	PreparedStatement statement;
	ResultSet rs;
	
	
	public CompanyDBDAO() throws CouponException{
		super();
	}
	
	/**creating company with  values ID, name, password, Email
	 */
	
	@Override
	public void createCompany(Company company) throws CouponException  {
		Connection con = pool.getConnection();
		String createCoupon = "INSERT INTO CompanyTable Values(?,?,?,?)";
		try {
			statement = con.prepareStatement(createCoupon);
			statement.setLong(1, company.getID());
			statement.setString(2, company.getCompName());
			statement.setString(3, company.getPassword());
			statement.setString(4, company.getEmail());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new CouponException("can't create the company ");
		} catch (NullPointerException e) {
			throw new CouponException("Check if every parameter is filled");
		} finally {
			pool.returnConnection(con);
		    try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement.");};
		}
	}

	/**
	 *	First getting all of the coupons that belong to the company
	 *	and then deleting them from CouponTable
	 * Second thing is removing the company from CompanyTable
	 * and the third is removing id's of both company and coupon from the Join table
	 */
	@Override
	public void removeCompany(Company company) throws CouponException {
		Connection con = pool.getConnection();
		CouponDBDAO main = new CouponDBDAO();
		String  getIdCoupon = "SELECT CouponTable_ID FROM companyCoupon where CompanyTable_ID =" + company.getID();
		String removeCompany = "DELETE FROM CompanyTable WHERE ID ="+ company.getID();
		String removeCompanyJoin = "DELETE FROM companyCoupon WHERE CompanyTable_ID ="+ company.getID();
		PreparedStatement statement1;
		PreparedStatement statement2;
		try {
			con.setAutoCommit(false);
			statement1 = con.prepareStatement(removeCompany);
			statement1.executeUpdate();
			statement1.close();
			statement2 = con.prepareStatement(getIdCoupon);
			 rs = statement2.executeQuery();
			while(rs.next()){
			Coupon coupon = new Coupon();
				coupon.setID(rs.getLong(1));
				main.removeCoupon(coupon);
				}
			statement2.close();
			statement = con.prepareStatement(removeCompanyJoin);
			statement.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {con.rollback();} catch (SQLException e1) {throw new CouponException("problem try again.");
		
		} finally {
			pool.returnConnection(con);
			try { if (statement != null) statement.close(); } catch (Exception e1) {throw new CouponException("problem closing statement");};
			try { if (rs != null) rs.close(); } catch (Exception e1) {throw new CouponException("problem closing resultset");};
		}
		}
		
	}


	/**
	 * updating the company's coupons if failed to update the coupons in the
	 * table roll back returns everything to his first state
	 */
	@Override
	public void updateCompany(Company company) throws CouponException {
		Connection con = pool.getConnection();
			String updateCompany = "UPDATE CompanyTable SET Password=? ,Email=? WHERE ID= " + company.getID();
			PreparedStatement statement = null;
			try {
				con.setAutoCommit(false);
				statement = con.prepareStatement(updateCompany);
				statement.setString(1, company.getPassword());
				statement.setString(2, company.getEmail());
				statement.executeUpdate();
				con.commit();
			} catch (SQLException e) {
				try {con.rollback();} catch (SQLException e1) {throw new CouponException("problem try again");}
				
			} finally {
				pool.returnConnection(con);
				try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement");};
			}
		}
	
	/**
	 * Method that inserts company's and coupon's id's into Their join table
	*@param company , company
	*@param coupon , coupon
	*@throws CouponException throws coupon exception
	 */
	public void insertJoinTable(Company company,Coupon coupon) throws CouponException{
		Connection con = pool.getConnection();
		String insert = "INSERT INTO companyCoupon VALUES(?,?)";
		try {
			statement = con.prepareStatement(insert);
			statement.setLong(1, company.getID());
			statement.setLong(2, coupon.getID());
			statement.executeUpdate();
			pool.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponException("problem try again.");
			
		} finally {
			pool.returnConnection(con);
			try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement");
		}
		}
		}

	
	/**
	 *Gets Company by its ID number from the Company table in the database: 
	*@param ID , get and id parameter
	*@throws CouponException throws coupon exception
	*@return company you want
	 */
	@Override
	public Company getCompany(int ID) throws CouponException {
		Connection con = pool.getConnection();
		Company company = new Company();
		PreparedStatement statement = null;
		ResultSet rs = null;
		String getCompany= "SELECT   CompanyName,Password,Email  FROM CompanyTable WHERE ID=" + ID;
		try {
			statement = con.prepareStatement(getCompany);
			 rs = statement.executeQuery();
			while(rs.next()){
				company.setID(ID);
				company.setCompName(rs.getString(1));
				company.setPassword(rs.getString(2));
				company.setEmail(rs.getString(3));
			}
		} catch (SQLException e) {
			throw new CouponException("Try Again");
		}
		finally {
		pool.returnConnection(con);
		try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement");};
		try { if (rs != null) rs.close(); } catch (Exception e) {throw new CouponException("problem closing resultset");};
		}
		return company;
	}

	/**
	 *Creating a list of all the companies there are 
	 */
	@Override
	public Collection<Company> getAllCompanies() throws CouponException {
		Connection con = pool.getConnection();
		List<Company> companyList = new ArrayList<Company>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		String getAllCompanies = "SELECT   CompanyName,Password,Email ,ID FROM CompanyTable";
		try {
			statement = con.prepareStatement(getAllCompanies);
			 rs = statement.executeQuery();
			while(rs.next()){
				Company company = new Company();
				company.setID(rs.getLong(4));
				company.setCompName(rs.getString(1));
				company.setPassword(rs.getString(2));
				company.setEmail(rs.getString(3));
				companyList.add(company);
			}
		} catch (SQLException e) {
			throw new CouponException("can't get the Companies list");
		}finally {
		pool.returnConnection(con);
		try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement");};
		try { if (rs != null) rs.close(); } catch (Exception e) {throw new CouponException("problem closing resultset");};
		}
		return companyList;
	}
		

	/**
	 * Getting a collection of the coupons that belong 
	 * to the given company 
	 */
	@Override
	public Collection<Coupon> getCoupons(Company company) throws CouponException {
		List<Coupon> companyCouponList = new ArrayList<Coupon>();
		CouponDBDAO couponMain = new CouponDBDAO();
		PreparedStatement statement = null;
		Connection con = pool.getConnection();
		ResultSet rs = null;
		String getAllCoupon = "SELECT   CouponTable_ID FROM companyCoupon WHERE CompanyTable_ID ="+ company.getID();		
		try {
			statement = con.prepareStatement(getAllCoupon);
			 rs = statement.executeQuery();
			while (rs.next()) {
				companyCouponList.add(couponMain.getCoupon(rs.getInt(1)));
			}
		} catch (SQLException e) {
			throw new CouponException("problem with connecting to the database");
		} finally {
			pool.returnConnection(con);
			try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement");};
			try { if (rs != null) rs.close(); } catch (Exception e) {throw new CouponException("problem closing resultset");};
		}
		return companyCouponList;
	}

	 /**
	 * this method checks database for company with given name and password
	 * if it finds company that matches it will return it
	 * if it doesn't it will return a message that you will have to 
	 * register first and then log in
	 */
	public Company login(String name, String password) throws CouponException {
		Connection con = pool.getConnection();
		List<Company> companies = new ArrayList<Company>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		String getAllCompanies = "SELECT ID , CompanyName , Password , Email FROM CompanyTable";
		try {
			statement = con.prepareStatement(getAllCompanies);
			 rs = statement.executeQuery();
			while(rs.next()){
				Company company = new Company();
				company.setID(rs.getLong(1));
				company.setCompName(rs.getString(2));
				company.setPassword(rs.getString(3));
				company.setEmail(rs.getString(4));
				companies.add(company);
			}
		} catch (SQLException e) {
			throw new CouponException("problem try again.");
		}
		finally{//closes connections, statements and result set
			pool.returnConnection(con);
			 try { if (rs != null) rs.close(); } catch (Exception e) {throw new CouponException("problem closing resultset.");};
			 try { if (statement != null) statement.close(); } catch (Exception e) {throw new CouponException("problem closing statement.");};
		}
		for(int i = 0; i<companies.size(); i++){
			if(companies.get(i).getCompName().equals(name) && companies.get(i).getPassword().equals(password)){
				Company company = new Company();
				company = companies.get(i);
				return company;
			}
		}

				System.out.println("There is no such company, Register and then log in");
		return null;
	}

}
