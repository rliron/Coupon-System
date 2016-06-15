package DataBaseTables;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



import Classes.Coupon;
import exceptionClass.CouponException;
/**
 * this class is responsible for the daily coupon update- removing all the coupons that are out-dated.
*/ 
public class DailyCouponExpirationTask implements Runnable {
	public static final long HOURS_24 = 86400000;
	private boolean quit = false; 
	CompanyDBDAO comp = new CompanyDBDAO();
	CouponDBDAO coupon = new CouponDBDAO();
	CustomerDBDAO cust = new CustomerDBDAO();
	
	public DailyCouponExpirationTask () throws CouponException{
	}
	 /**
	  * the run() method that executes the daily coupon update.
	  */
	@Override
	public void run() {
		List<Coupon> list = new ArrayList<>();
		Date date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar.setTime(date);
		 Long dateNum =calendar.getTimeInMillis();
		 while (quit == false) {
			 
			try {
				list = (List<Coupon>) coupon.getAllCoupon();
				for (int i = 0; i < list.size(); i++) {
					calendar2.setTime(list.get(i).getEndDate());
					Long EndDate = calendar2.getTimeInMillis();
					if (EndDate < dateNum) {
						coupon.removeCoupon(list.get(i));
						System.out.println("Outdated coupons have been removed");
					}
				}
				try {
					Thread.sleep(HOURS_24);
				} catch (InterruptedException e) {
					new CouponException("problem");
				}

			} catch (CouponException e) {
				new CouponException("problem");
			}
		}
	}
	/**
	 * this method stops the thread from deleting coupons
	 */
	public void stopTask() {
		quit = true;
	}
}
