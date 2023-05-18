package com.android.sellacha.api.response.OrderByID;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Info{

	@SerializedName("order_no")
	private String orderNo;

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("shipping_info")
	private ShippingInfo shippingInfo;

	@SerializedName("payment_status")
	private String paymentStatus;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("tax")
	private String tax;

	@SerializedName("getway")
	private Getway getway;

	@SerializedName("total")
	private String total;

	@SerializedName("order_content")
	private OrderContent orderContent;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("shipping")
	private String shipping;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("order_item")
	private List<OrderItemItem> orderItem;

	@SerializedName("id")
	private int id;

	@SerializedName("customer_id")
	private String customerId;

	@SerializedName("s_date")
	private Object sDate;

	@SerializedName("order_type")
	private String orderType;

	@SerializedName("status")
	private String status;

	@SerializedName("customer")
	private Customer customer;

	public String getOrderNo(){
		return orderNo;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public ShippingInfo getShippingInfo(){
		return shippingInfo;
	}

	public String getPaymentStatus(){
		return paymentStatus;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getTax(){
		return tax;
	}

	public Getway getGetway(){
		return getway;
	}

	public String getTotal(){
		return total;
	}

	public OrderContent getOrderContent(){
		return orderContent;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public String getShipping(){
		return shipping;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getUserId(){
		return userId;
	}

	public List<OrderItemItem> getOrderItem(){
		return orderItem;
	}

	public int getId(){
		return id;
	}

	public String getCustomerId(){
		return customerId;
	}

	public Object getSDate(){
		return sDate;
	}

	public String getOrderType(){
		return orderType;
	}

	public String getStatus(){
		return status;
	}

	public Customer getCustomer(){
		return customer;
	}
}