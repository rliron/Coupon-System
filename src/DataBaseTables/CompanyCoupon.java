package DataBaseTables;

/**
 * the class of the company and coupon join table
 */

public class CompanyCoupon {
	private long COMP_ID;
	private long COUPON_ID;

	public CompanyCoupon(long COMP_ID,long COUPON_ID){
		this.COMP_ID = COMP_ID;
		this.COUPON_ID = COUPON_ID;
	}

	public long getCOMP_ID() {
		return COMP_ID;
	}

	public void setCOMP_ID(long cOMP_ID) {
		COMP_ID = cOMP_ID;
	}

	public long getCOUPON_ID() {
		return COUPON_ID;
	}

	public void setCOUPON_ID(long cOUPON_ID) {
		COUPON_ID = cOUPON_ID;
	}
	
}
