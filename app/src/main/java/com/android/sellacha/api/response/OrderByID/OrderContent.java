package com.android.sellacha.api.response.OrderByID;

import com.google.gson.annotations.SerializedName;

public class OrderContent{

	@SerializedName("id")
	private int id;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("value")
	private String value;

	@SerializedName("key")
	private String key;

	@SerializedName("address")
	private String address;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("sub_totalfdf")
	private String subTotal;

	@SerializedName("comment")
	private String comment;

	@SerializedName("email")
	private String email;

	@SerializedName("zip_code")
	private String zipCode;

	@SerializedName("coupon_discount")
	private String couponDiscount;

	public int getId(){
		return id;
	}

	public String getOrderId(){
		return orderId;
	}

	public String getValue(){
		return value;
	}

	public String getKey(){
		return key;
	}

	public String getAddress(){
		return address;
	}

	public String getPhone(){
		return phone;
	}

	public String getName(){
		return name;
	}

	public String getSubTotal(){
		return subTotal;
	}

	public String getComment(){
		return comment;
	}

	public String getEmail(){
		return email;
	}

	public String getZipCode(){
		return zipCode;
	}

	public String getCouponDiscount(){
		return couponDiscount;
	}
}