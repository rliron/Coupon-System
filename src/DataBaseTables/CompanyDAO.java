package DataBaseTables;

import java.util.Collection;

import Classes.Company;
import Classes.Coupon;

import exceptionClass.CouponException;
/**
*the interface for the companyDAO and the methods that company inherit
*/
public interface CompanyDAO {
	
	public void createCompany (Company company) throws CouponException;
	public void removeCompany (Company company) throws CouponException;
	public void updateCompany (Company company) throws CouponException;
	public Company getCompany (int id) throws CouponException;
	public Collection<Company> getAllCompanies () throws CouponException;
	public Collection<Coupon> getCoupons(Company company) throws CouponException;
	public Company login(String name, String password) throws CouponException;
	

}
