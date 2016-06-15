package DataBaseTables;

import java.util.Collection;

import Classes.Coupon;
import Classes.Customer;

import exceptionClass.CouponException;

/**
 * it's the interface of the customerDAO and all it's inherit methods
 */
public interface CustomerDAO {
	public void createCustomer (Customer customer) throws CouponException;
	public void removeCustomer (Customer customer) throws CouponException;
	public void updateCustomer (Customer customer) throws CouponException;
	public Customer getCustomer (int ID) throws CouponException;
	public Collection<Customer> getAllCustomer () throws CouponException;
	public Customer login(String custName , String password) throws CouponException;
	public Collection<Coupon> getCoupons(Customer customer) throws CouponException;
	 

}
