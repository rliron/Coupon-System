package DataBaseTables;

/**
 * the class of the customer and coupon join table
 */
public class CustomerCoupon {
private long CUST_ID;
private long COUPON_ID;

public CustomerCoupon(long CUST_ID,long COUPON_ID){
	this.CUST_ID = CUST_ID;
	this.COUPON_ID = COUPON_ID;
}

public long getCUST_ID() {
	return CUST_ID;
}

public void setCUST_ID(long cUST_ID) {
	CUST_ID = cUST_ID;
}

public long getCOUPON_ID() {
	return COUPON_ID;
}

public void setCOUPON_ID(long cOUPON_ID) {
	COUPON_ID = cOUPON_ID;
}

}
