package com.android.sellacha.api.response.OrderByID;

import com.google.gson.annotations.SerializedName;

public class ShippingInfo{

	@SerializedName("shipping_id")
	private String shippingId;

	@SerializedName("city")
	private City city;

	@SerializedName("shipping_method")
	private ShippingMethod shippingMethod;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("location_id")
	private String locationId;

	public String getShippingId(){
		return shippingId;
	}

	public City getCity(){
		return city;
	}

	public ShippingMethod getShippingMethod(){
		return shippingMethod;
	}

	public String getOrderId(){
		return orderId;
	}

	public String getLocationId(){
		return locationId;
	}
}