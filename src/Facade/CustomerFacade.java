package Facade;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import exceptionClass.CouponException;
import Classes.Coupon;
import DataBaseTables.CompanyDBDAO;
import DataBaseTables.CouponDBDAO;
import DataBaseTables.CouponType;
import DataBaseTables.CustomerDBDAO;
/**
 * Class that has all of the methods customer can do when he logs in
 */
public class CustomerFacade implements CouponClientFacade{

	public String name;
	public String password;
	CompanyDBDAO comp = new CompanyDBDAO();
	CouponDBDAO coupon = new CouponDBDAO();
	CustomerDBDAO cust = new CustomerDBDAO();
	
	public CustomerFacade() throws CouponException {
		new CouponDBDAO();
		new CompanyDBDAO();
		new CustomerDBDAO();

}
	/**
	 *  makes date to be seen in a specific order that i wrote
	 */
	SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
	
	
	/**Purchasing coupon and checking some conditions like if there's any left 
	 * @param coup you want to purchase
	 * @throws CouponException for problems
	 */
	 
	public String purchaseCoupon(Coupon coup) throws CouponException  {
		List<Coupon> CustomerCouponList = new ArrayList<Coupon>();
		List<Coupon> CouponList = new ArrayList<Coupon>();
		CouponList = (List<Coupon>) coupon.getAllCoupon();
		boolean has = false;
		for (int a = 0; a < CouponList.size(); a++) {
			if (CouponList.get(a).getID() == coup.getID()) {
				has = true;
			}
		}
			if (has = false) {
				return "No such Coupon";
			} else {
				Date date = new Date(System.currentTimeMillis());
				Calendar calendar = Calendar.getInstance();		
				calendar.setTime(coupon.getCoupon((int) coup.getID())
						.getEndDate());

				Calendar calendar1 = Calendar.getInstance();
				calendar1.setTime(date);
				Long EndDate = calendar.getTimeInMillis();
				Long CurrDate = calendar1.getTimeInMillis();

				CustomerCouponList = (List<Coupon>) cust.getCoupons(cust.login(
						name, password));// Getting all of the coupons this
											// customer has

				if (CustomerCouponList.size() == 0) {
					if (EndDate <= CurrDate) {// Checking if Coupon hasn't
												// expired already
						return "Coupon expired";
					} else
						CustomerCouponList.add(coup);
					coupon.JoinCouponTables(cust.login(name, password), coup);
				} else {
					for (int i = 0; i < CustomerCouponList.size(); i++) {
						if (CustomerCouponList.get(i).getID() == coup.getID()) {
							return "You already have this kind of coupon";

						} else if (EndDate <= CurrDate) {// Checking if Coupon
															// hasn't
															// expired already
							return "Coupon expired";
						}
					}
					// adding the coupon to the join table of customer and
					// coupon
					coupon.JoinCouponTables(cust.login(name, password), coup);
				}
			}
			return "coupon purchased";
		}

	
	
	/**shows all of the coupons customer purchased
	 * @throws CouponException for problems
	 */
	 
	public Collection<Coupon> getAllPurchasedCoupons() throws CouponException{
		List <Coupon>CustomerCouponList = new ArrayList<Coupon>();
		CustomerCouponList = (List<Coupon>) cust.getCoupons(cust.login(name,password));//Getting all of the coupons this customer has
		for (int i = 0; i < CustomerCouponList.size(); i++) {
		}
		return CustomerCouponList;
	}
	/**shows all of the coupons customer purchased by a certain type
	 * @param type of the purchased coupons
	 * @throws CouponException for problems
	 */
	
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws CouponException{
		List <Coupon>CustomerCouponList = new ArrayList<Coupon>();
		List <Coupon>CouponList = new ArrayList<Coupon>();
		List <Coupon>CouponListByType = new ArrayList<Coupon>();
		CouponList = (List<Coupon>) coupon.getCouponByType(type);//Getting all of the coupons by a unique type
		if (CouponList.size() == 0) {
			System.out.println("There is  no coupons of that type");
		} else {
		CustomerCouponList = (List<Coupon>) cust.getCoupons(cust.login(name,password));//Getting all of the coupons this customer has
			for (int i = 0; i < CustomerCouponList.size(); i++) {
				for (int j = 0; j < CouponList.size(); j++) {
				if(CustomerCouponList.get(i).getID() == CouponList.get(j).getID()){//checking if there is any coupons of unique type that the customer have to
					CouponListByType.add(CouponList.get(j));
					}
				}
			}
		}
		return CouponListByType;
		}
	
	/**shows all of the coupons customer purchased by a certain price
	 * @param price you want to search for
	 * @throws CouponException for problems
	 */
	
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws CouponException{
		List <Coupon>CustomerCouponList = new ArrayList<Coupon>();
		List <Coupon>CouponList = new ArrayList<Coupon>();
		List <Coupon>CouponListByPrice = new ArrayList<Coupon>();
		List <Coupon> priceList= new ArrayList<Coupon>();
		CustomerCouponList = (List<Coupon>) cust.getCoupons(cust.login(name,password));//Getting all of the coupons this customer has
		CouponList = (List<Coupon>) coupon.getAllCoupon();//Getting all of the coupons 
		for (int i = 0; i < CouponList.size(); i++) {
			if((double)CouponList.get(i).getPrice() <= price ){//creating a list of coupons that are equal to the price i have entered
				priceList.add(CouponList.get(i));
			}
		} if(priceList.size()==0){
			System.out.println("There is no such coupon with that price");
		}
		for (int i = 0; i < CustomerCouponList.size(); i++) {
			for (int j = 0; j < priceList.size(); j++) {
			if(CustomerCouponList.get(i).getID() == priceList.get(j).getID()){//checking if there is any coupons of unique price that the customer have to
				CouponListByPrice.add(CouponList.get(j));
				}

			}
		}
		System.out.println(CouponListByPrice.size());
		return CouponListByPrice;
	}
	

	/**login method that enables you to get in to to facade and create or change things 
	 * 
	 */
	
	@Override
	public CouponClientFacade login(String name, String password,
			String clientType) {
			this.name = name;
			this.password = password;
		 CustomerFacade facade = null;
		try {
			facade = new CustomerFacade();
		} catch (CouponException e) {
			e.printStackTrace();
		}
		 return facade;
	}
}
