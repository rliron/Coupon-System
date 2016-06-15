package Facade;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import exceptionClass.CouponException;
import Classes.Company;
import Classes.Coupon;
import DataBaseTables.CompanyDAO;
import DataBaseTables.CompanyDBDAO;
import DataBaseTables.CouponDAO;
import DataBaseTables.CouponDBDAO;
import DataBaseTables.CouponType;
import DataBaseTables.CustomerDBDAO;
/**
 * Class that has all of the methods company can do when she logs in
 */
public class CompanyFacade implements CouponClientFacade {
	public String name;
	public String password;

	private CouponDAO couponDB;
	private CompanyDAO companyDB;
	CompanyDBDAO comp = new CompanyDBDAO();
	CouponDBDAO coupon = new CouponDBDAO();
	CustomerDBDAO cust = new CustomerDBDAO();

	public CompanyFacade() throws CouponException {
		couponDB = new CouponDBDAO();
		companyDB = new CompanyDBDAO();
		new CustomerDBDAO();
	}
	/**login method that enables you to get in to to facade and create or change things 
	 */
	@Override
	public CouponClientFacade login(String name, String password, String clientType) {
		Company company = new Company();
		try {
			company = comp.login(name, password);
			if (company != null) {
				this.name = name;
				this.password = password;
			}
		} catch (CouponException e) {
			new CouponException("Problem");
		}
		CompanyFacade facade = null;
		try {
			facade = new CompanyFacade();
		} catch (CouponException e) {
			new CouponException("problem");
		}
		return facade;
	}

	/** creating a coupon and checking if his name is already exist.
	 * @param coupon object
	 * @throws CouponException for problems
	 */
	 
	public String createCoupon(Coupon coupon) throws CouponException {
		List<Coupon> listCompanyCoupon = new ArrayList<Coupon>();
		listCompanyCoupon = (List<Coupon>) companyDB.getCoupons(comp.login(name, password));
		Date date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar.setTime(date);
		Long dateNum = calendar.getTimeInMillis();
		calendar2.setTime(coupon.getEndDate());
		Long EndDate = calendar2.getTimeInMillis();
		String value;
		if (EndDate < dateNum) {
			value="Date has expired , enter new expiration date";
			return value;
		}
		else if(listCompanyCoupon.size() == 0) {
			couponDB.createCoupon(coupon);
			comp.insertJoinTable(comp.login(name, password), coupon);
			
		} else {
			Iterator<Coupon> it = listCompanyCoupon.iterator();
			boolean hastitle = false;
			while (it.hasNext()) {
				if (it.next().getTitle().equals(coupon.getTitle())) {
					hastitle = true;
					return value="can't add coupon with the same name";
				}
			}
			if (hastitle == false) {
				couponDB.createCoupon(coupon);
				comp.insertJoinTable(comp.login(name, password), coupon);
				comp.login(name, password).setCoupons(listCompanyCoupon);
				comp.login(name, password).getCoupons().add(coupon);

			}
		}
		return "Coupon created";
	}

	/** remove the coupon from the company and from all the customers who had it. 
	 * @param coupon you want to remove
	 * @throws CouponException for problems
	 */
	 
	public void removeCoupon(Coupon coupon) throws CouponException {
		couponDB.removeCoupon(coupon);
	}

	/** updating the coupon's end date and price
	 * @param coupon you want to update
	 * @throws CouponException for problems
	 */
	 
	public String updateCoupon(Coupon coupon) throws CouponException {
		Date date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(coupon.getEndDate());//Making Date a Long number so i can compare it and check what number is bigger
		
		Calendar calendar1 = Calendar.getInstance();
		 calendar1.setTime(date);
		 
		 Long EndDate =calendar.getTimeInMillis();
		 Long CurrDate = calendar1.getTimeInMillis();
		 if (EndDate < CurrDate) {
			 return "Date has expired, enter new date";
		 }else {
			 couponDB.updateCoupon(coupon);
		 }
		return "Coupon updated";
	}

	/** gets the coupon by ID
	 * @param id of the coupon you need
	 * @return coupon you wanted
	 * @throws CouponException for problem
	 */
	 
	public Coupon getCoupon(int id) throws CouponException {
		Coupon coupon = couponDB.getCoupon(id);
		if(coupon.getID()==0){
			System.out.println("No such coupon");
		}else
		System.out.println(coupon.toString());
		return coupon;
	}
	
	/** returns all of the coupons there is
	 * @return list of coupons
	 * @throws CouponException for problem
	 */
	 
	public Collection<Coupon> getAllCoupon() throws CouponException {
		List<Coupon> list = new ArrayList<Coupon>();
		list = (List<Coupon>) companyDB.getCoupons(comp.login(name, password));
		return list;
	}
	/** returns all of the coupons by certain type
	 * @param type of the coupon
	 * @return couponCompanyListByType of the coupons by type
	 * @throws CouponException for problem
	 */
	 
	public Collection<Coupon> getCouponByType(CouponType type) throws CouponException {
		List<Coupon> couponTypeList = new ArrayList<Coupon>();
		List<Coupon> couponCompanyList = new ArrayList<Coupon>();
		List<Coupon> couponCompanyListByType = new ArrayList<Coupon>();
		couponTypeList = (List<Coupon>) couponDB.getCouponByType(type);
		couponCompanyList = (List<Coupon>) comp.getCoupons(comp.login(name, password));
		for (int i = 0; i < couponTypeList.size(); i++) {
			for (int j = 0; j < couponCompanyList.size(); j++) {
				if (couponTypeList.get(i).getID() == couponCompanyList.get(j).getID()) {
					couponCompanyListByType.add(couponTypeList.get(j));
				}
			}
		}
		System.out.println(couponCompanyListByType.size());
		return couponCompanyListByType;
	}
}
