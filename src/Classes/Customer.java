package Classes;

import java.util.ArrayList;
import java.util.List;

/** the class of Customer and all it's information
 */
public class Customer {
	private long ID;
	private String custName;
	private String password;
	public List <Coupon>coupons = new ArrayList<Coupon>();

	public Customer() {
		super();
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Customer [ID=" + ID + ", custName=" + custName + ", password="
				+ password + ", coupons=" + coupons + "]";
	}

	}


