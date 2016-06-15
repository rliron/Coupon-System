package Facade;

public interface CouponClientFacade {
	/** log in method that you have to execute in order to get the facade you belong to
	 * @param name , given name
	 * @param password , given password
	 * @param clientType , given client type
	 * @return  CouponClientFacade , some facade, depends on the parameters
	 */

	public CouponClientFacade login (String name, String password, String clientType);
	
}
