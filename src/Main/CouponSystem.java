package Main;

import java.sql.SQLException;
import DataBaseTables.ConnectionPool;
import DataBaseTables.DailyCouponExpirationTask;
import Facade.AdminFacade;
import Facade.CompanyFacade;
import Facade.CouponClientFacade;
import Facade.CustomerFacade;
import exceptionClass.CouponException;

/**this class connect to the other parts of the coupons system, by the login
 */
public class CouponSystem implements CouponClientFacade {

	private static CouponSystem instance = new CouponSystem();
	
	private CouponSystem () {
		try {
			DailyCouponExpirationTask task = new DailyCouponExpirationTask();
			Thread thread = new Thread(task,"thread");
			thread.start();
		} catch (CouponException e) {
			new CouponException("task problem");
		}
	}
	
	public static CouponSystem getInstance() {
		if (instance == null) {
			instance = new CouponSystem();
		}
		return instance;
	}
	/**login method that enables you to get in to to facade and create or change things 
	 */
	public CouponClientFacade login (String name, String password, String type) {
		try {
			if(name.equals("Customer") && password.equals("1234") && type.equals("Customer")) {
				CustomerFacade custFacade = new CustomerFacade();
				custFacade.login(name, password, type);
				return custFacade;
			}else if (name.equals("Company") && password.equals("1234") && type.equals("Company")) {
				CompanyFacade compFacade = new CompanyFacade();
				compFacade.login(name, password, type);
				return compFacade;
			}else if (name.equals("Admin") && password.equals("1234") && type.equals("Admin")) {
				AdminFacade admin = new AdminFacade();
				return admin;
			}
			
		} catch (CouponException e) {
			new CouponException("problem");
		}
		return null;
	}
	
	/**this method will close the connection pool and shut down the daily task
	 */
	public void shutDown () {
		ConnectionPool pool = ConnectionPool.getInstance();
		DailyCouponExpirationTask task;
		try {
			task = new DailyCouponExpirationTask();
			task.stopTask();
			pool.closeAllConnection();
		} catch (CouponException |SQLException e) {
			new CouponException("problem");
		}
	}
}
