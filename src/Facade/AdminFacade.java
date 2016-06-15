package Facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import exceptionClass.CouponException;
import Classes.Company;
import Classes.Coupon;
import Classes.Customer;
import DataBaseTables.CompanyDBDAO;
import DataBaseTables.CouponDBDAO;
import DataBaseTables.CustomerDBDAO;
/**
 * Admin class , class with all of the methods admin is capable of
 */
public class AdminFacade implements CouponClientFacade{

	CompanyDBDAO comp = new CompanyDBDAO();
	CouponDBDAO coupon = new CouponDBDAO();
	CustomerDBDAO cust = new CustomerDBDAO();
	Message message ;
	public AdminFacade() throws CouponException {
		new CouponDBDAO();
		new CompanyDBDAO();
		new CustomerDBDAO();
	}

	/**
	 * login Method , returns a facade that enables you to log in in to
	 * the system 
	 */
	@Override
	public CouponClientFacade login(String compName, String password, String clientType) {
		AdminFacade admin = null;
		try {
			admin = new AdminFacade();
		} catch (CouponException e) {
			new CouponException("problem");
		}
		return admin;    
	}
	public Message getMessage(){
		return message;
	}
	/**check if the company name is existing, if not it create the company
	 * @param company  object
	 * @throws CouponException for problems
	 */
	  
	public String createCompany (Company company)  {
		List<Company> list = new ArrayList<Company>();
		try {
			list = (List<Company>) comp.getAllCompanies();
		} catch (CouponException e) {
			new CouponException("unable to get all of the companies");
		}
		Iterator<Company>  it = list.iterator();
		boolean hascomp = false;
		System.out.println("1");
		while (it.hasNext()) {
			if (it.next().getCompName().equals(company.getCompName()) ) {
				System.out.println("can't add company with the same name");
				hascomp = true;
				return "can't add company with the same name";
			}
		}
		System.out.println("2");
		if (hascomp == false) {
			try {
				comp.createCompany(company);
				
			} catch (CouponException e) {
				new CouponException("Couldnt create company");
			}
		}
		return  "Company created";
		
	}
	
	/**first remove all the company's coupon (from all the tables) and after remove the company itself
	 * @param company you want to remove
	 * @throws CouponException for problems
	 */
	public void removeCompany (Company company) throws CouponException {
		comp.removeCompany(company);
	}
	
	/**updating the company's details
	 * @param company you want to update
	 * @throws CouponException for problems
	 */
	public void updateComapny (Company company) throws CouponException {
		comp.updateCompany(company);
	}

	/**get the company by the ID
	 * @param id of the company you want
	 * @return company you wanted
	 * @throws CouponException for problems
	 */
	
	public Company getCompany (int id) throws CouponException {
		List<Coupon> coupons = new ArrayList<Coupon>();
		Company company = comp.getCompany(id);
		coupons = (List<Coupon>) comp.getCoupons(company);
		company.setCoupons(coupons);
		System.out.println(company.toString());
		return company;
	}
	
	/**get all the companies from the table 
	 * @return list of companies
	 * @throws CouponException for problem
	 */
	public Collection<Company> getAllCompanies () throws CouponException {
		List <Company>list = new ArrayList<Company>();
		list = (List<Company>) comp.getAllCompanies();
		for (int i = 0; i < list.size(); i++) {
			List<Coupon>coupons = new ArrayList<Coupon>();
			coupons = (List<Coupon>) comp.getCoupons(list.get(i));
			list.get(i).setCoupons(coupons);
			System.out.println(list.get(i).toString());
		}
		return list;
	}
	
	/**check if the customer name is existing, if not it create the customer
	 * @param customer object
	 * @throws CouponException for problems
	 */

	public void createCustomer (Customer customer) throws CouponException {
		List <Customer>list = new ArrayList<Customer>();
		list = (List<Customer>) cust.getAllCustomer();
		Iterator<Customer> it = list.iterator();
		boolean hascust = false;
		while (it.hasNext()) {
			if (it.next() == customer) {
				System.out.println("can't add customer with the same name");
				hascust = true;
			}
		}
		if (hascust == false) {
			cust.createCustomer(customer);
		}
	}
	
	/**remove the customer and all his coupon
	 * @param customer you want to remove
	 * @throws CouponException for problems
	 */
	public void removeCustomer (Customer customer) throws CouponException {
			cust.removeCustomer(customer);
	}

	
	/**updating the customers details
	 * @param customer you want to update
	 * @throws CouponException for problems
	 */
	
	public void updateCustomer(Customer customer) throws CouponException {
		cust.updateCustomer(customer);
	}

	/**get the customer by ID
	 * @param id of the customer you need
	 * @return customer you wanted
	 * @throws CouponException for problems
	 */
	
	public Customer getCust (int id) throws CouponException {
		List<Coupon> coupons = new ArrayList<Coupon>();
		Customer customer = cust.getCustomer(id);
		coupons = (List<Coupon>) cust.getCoupons(customer);
		customer.setCoupons(coupons);
		System.out.println(customer.toString());
		return customer;
	}
	
	/**get all the customers
	 * @return list of customers
	 * @throws CouponException for problems
	 */
	 
	public Collection<Customer> getAllCustomers () throws CouponException {
		List<Customer> list = new ArrayList<Customer>();
		list = (List<Customer>) cust.getAllCustomer();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
		return list;
	}
	
}
