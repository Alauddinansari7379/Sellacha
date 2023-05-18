package com.android.sellacha.api.response.GetAllorder;

import com.google.gson.annotations.SerializedName;

public class OrderItem {

	@SerializedName("order_no")
	private String orderNo;

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("order_items_count")
	private String orderItemsCount;

	@SerializedName("payment_status")
	private String paymentStatus;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("tax")
	private String tax;

	@SerializedName("total")
	private String total;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("shipping")
	private String shipping;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("id")
	private int id;

	@SerializedName("customer_id")
	private String customerId;

	@SerializedName("s_date")
	private String sDate;

	@SerializedName("order_type")
	private String orderType;

	@SerializedName("status")
	private String status;

	@SerializedName("customer")
	private Customer customer;

	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}

	public String getOrderNo(){
		return orderNo;
	}

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setOrderItemsCount(String orderItemsCount){
		this.orderItemsCount = orderItemsCount;
	}

	public String getOrderItemsCount(){
		return orderItemsCount;
	}

	public void setPaymentStatus(String paymentStatus){
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentStatus(){
		return paymentStatus;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setTax(String tax){
		this.tax = tax;
	}

	public String getTax(){
		return tax;
	}

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setShipping(String shipping){
		this.shipping = shipping;
	}

	public String getShipping(){
		return shipping;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCustomerId(String customerId){
		this.customerId = customerId;
	}

	public String getCustomerId(){
		return customerId;
	}

	public void setSDate(String sDate){
		this.sDate = sDate;
	}

	public String getSDate(){
		return sDate;
	}

	public void setOrderType(String orderType){
		this.orderType = orderType;
	}

	public String getOrderType(){
		return orderType;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setCustomer(Customer customer){
		this.customer = customer;
	}

	public Customer getCustomer(){
		return customer;
	}
}