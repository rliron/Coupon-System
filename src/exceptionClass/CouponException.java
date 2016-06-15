package exceptionClass;


public class CouponException extends Exception  {
	private static final long serialVersionUID = -6315769991287756281L;
	/**
	 * this class creates the CouponException. It contains constructor that will
	 * have specific message to the exception we would want to catch
	 * @param message we want to put 
	 */
	public CouponException(String message){
		super(message);
	}
}
