package Classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import DataBaseTables.CouponType;

public class Coupon {
	private long ID;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	public  CouponType Type;
	private String message;
	private Double price;
	private String image;
	

	// makes date to be seen in a specific order that i wrote
	SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");

	public Coupon(){
		super();

	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(String calendar) {
		Date date = new Date();//creating date instance
		try {
			date = ft.parse(calendar);//turning date format to string
		} catch (ParseException e) {
			System.out.println("Cannot convert into to date");
		}
		this.startDate = date;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(String calendar) {
		Date date = new Date();//creating date instance
		try {
			date = ft.parse(calendar);//turning date format to string
		} catch (ParseException e) {
			System.out.println("Cannot convert into to date");
		}
		this.endDate = date;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getType() {
		String type = String.valueOf(this.Type); // making Type equal to string 
		return type;
	}

	public void setType(CouponType type) {
		if(type==null){
			type=CouponType.OTHER;
		}
		this.Type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public SimpleDateFormat getFt() {
		return ft;
	}

	public void setFt(SimpleDateFormat ft) {
		this.ft = ft;
	}

	@Override
	public String toString() {
		return "Coupon [ID=" + ID + ", title=" + title + ", startDate="
				+ startDate + ", endDate=" + endDate + ", amount=" + amount
				+ ", Type=" + Type + ", message=" + message + ", price="
				+ price + ", image=" + image + "]";
	}

}