package Main;

import Classes.Company;
import Classes.Coupon;
import Classes.Customer;
import DataBaseTables.ConnectionPool;
import DataBaseTables.CouponType;
import Facade.AdminFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;
import exceptionClass.CouponException;

public class TestMain {

	public static void main(String[] args) throws CouponException {

		ConnectionPool pool = ConnectionPool.getInstance();
		CouponSystem system = CouponSystem.getInstance();
		
		//coupon constructor
		Coupon coupon = new Coupon();
		coupon.setID(1);
		coupon.setTitle("UpdatedCoupon");
		coupon.setStartDate("15/11/2001");
		coupon.setEndDate("23/01/2025");
		coupon.setAmount(30);
		coupon.setType(CouponType.FOOD);
		coupon.setMessage(null);
		coupon.setPrice(200.00);
		coupon.setImage("IMAGE");
		
		//customer construction
		Customer customer = new Customer();
		customer.setCustName("Customer");
		customer.setID(1);
		customer.setPassword("1234");
		
		
		//company constructor
		Company company = new Company();
		company.setID(56);
		company.setEmail("15kain@gmail.com");
		company.setPassword("asdasd");
		company.setCompName("admin");
		
		
		
			CompanyFacade companyFacade = new CompanyFacade();
			AdminFacade adminFacade = new AdminFacade();
			CustomerFacade customerFacade = new CustomerFacade();
		
		
		adminFacade = (AdminFacade) system.login("Admin", "1234", "Admin");
		adminFacade.createCompany(company);  //creating company
//		adminFacade.updateComapny(company);  //updating company info
//		adminFacade.getCompany(1); //get company by id
//		adminFacade.getAllCompanies();  //get all the companies
//		adminFacade.removeCompany(company); //remove company
		
//		adminFacade.createCustomer(customer);  //creating customer
//		adminFacade.updateCustomer(customer);  //updating Customer info
//		adminFacade.getCust(1);  //get Customer by id
//		adminFacade.getAllCustomers();  //get all the Customer
//		adminFacade.removeCustomer(customer); //remove Customer
		
//		customerFacade = (CustomerFacade) system.login("Customer", "1234", "Customer");
//		customerFacade.purchaseCoupon(coupon);  //customer can buy coupon
//		customerFacade.getAllPurchasedCoupons(); //get all the customer purchased
//		customerFacade.getAllPurchasedCouponsByType(CouponType.ENTERTAINMENT); //get coupons that the customer bought by the type
//		customerFacade.getAllPurchasedCouponsByPrice(180.00);  //get coupons that the customer bought by the price
		
//		companyFacade = (CompanyFacade) system.login("Company", "1234", "Company");
//		companyFacade.createCoupon(coupon);  //creating coupon
//		companyFacade.getCoupon(13);  //get all the coupons the company have
//		companyFacade.getCouponByType(CouponType.ENTERTAINMENT); //get coupons the company have by type
//		companyFacade.updateCoupon(coupon); //updating the coupon info
//		companyFacade.removeCoupon(coupon); //remove coupon from company
//		companyFacade.getAllCoupon();  //get the company's coupons
	}

}
