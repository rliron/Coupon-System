package DataBaseTables;

import java.util.Collection;

import Classes.Coupon;
import exceptionClass.CouponException;

/**
 * the interface of couponDAO and all it's inherited methods
 */
public interface CouponDAO {

	public void createCoupon (Coupon coupon) throws CouponException;
	public void removeCoupon (Coupon coupon) throws CouponException;
	public void updateCoupon (Coupon coupon) throws CouponException;
	public Coupon getCoupon (int ID) throws CouponException;
	public Collection<Coupon> getAllCoupon () throws CouponException;
	public Collection<Coupon> getCouponByType ( CouponType type) throws CouponException;
	
}
