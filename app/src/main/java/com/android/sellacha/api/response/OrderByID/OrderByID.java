package com.android.sellacha.api.response.OrderByID;

import com.google.gson.annotations.SerializedName;

public class OrderByID{

	@SerializedName("order_content")
	private OrderContent orderContent;

	@SerializedName("info")
	private Info info;

	public OrderContent getOrderContent(){
		return orderContent;
	}

	public Info getInfo(){
		return info;
	}
}