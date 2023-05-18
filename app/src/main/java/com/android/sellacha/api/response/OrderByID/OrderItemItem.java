package com.android.sellacha.api.response.OrderByID;

import com.google.gson.annotations.SerializedName;

public class OrderItemItem{

	@SerializedName("amount")
	private String amount;

	@SerializedName("qty")
	private String qty;

	@SerializedName("term")
	private Term term;

	@SerializedName("id")
	private int id;

	@SerializedName("term_id")
	private String termId;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("info")
	private String info;

	public String getAmount(){
		return amount;
	}

	public String getQty(){
		return qty;
	}

	public Term getTerm(){
		return term;
	}

	public int getId(){
		return id;
	}

	public String getTermId(){
		return termId;
	}

	public String getOrderId(){
		return orderId;
	}

	public String getInfo(){
		return info;
	}
}