package Classes;

import java.util.ArrayList;
import java.util.List;

/** the class of Company and all it's information
 */
public class Company {
	private long ID;
	private String compName;
	private String password;
	private String email;
	static List <Coupon>coupons = new ArrayList<Coupon>();


	public Company(){
		super();
	}


	public long getID() {
		return ID;
	}


	public void setID(long iD) {
		ID = iD;
	}


	public String getCompName() {
		return compName;
	}


	public void setCompName(String compName) {
		this.compName = compName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<Coupon> getCoupons() {
		return coupons;
	}


	public void setCoupons(List list) {
		this.coupons = list;
	}
	@Override
	public String toString() {
		return "Company [ID=" + ID + ", compName=" + compName + ", password="
				+ password + ", email=" + email + ", coupons=" + coupons.size() + "]" + "\n";
	}
}

