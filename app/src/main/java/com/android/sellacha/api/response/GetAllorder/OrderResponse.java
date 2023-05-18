package com.android.sellacha.api.response.GetAllorder;

import com.google.gson.annotations.SerializedName;

public class OrderResponse{


	@SerializedName("archived")
	private int archived;

	@SerializedName("canceled")
	private int canceled;

	@SerializedName("request")
	private Request request;

	@SerializedName("processing")
	private int processing;

	@SerializedName("pickup")
	private int pickup;

	@SerializedName("orders")
	private Orders orders;

	@SerializedName("completed")
	private int completed;

	@SerializedName("pendings")
	private int pendings;

	@SerializedName("type")
	private String type;

	public void setArchived(int archived){
		this.archived = archived;
	}

	public int getArchived(){
		return archived;
	}

	public void setCanceled(int canceled){
		this.canceled = canceled;
	}

	public int getCanceled(){
		return canceled;
	}

	public void setRequest(Request request){
		this.request = request;
	}

	public Request getRequest(){
		return request;
	}

	public void setProcessing(int processing){
		this.processing = processing;
	}

	public int getProcessing(){
		return processing;
	}

	public void setPickup(int pickup){
		this.pickup = pickup;
	}

	public int getPickup(){
		return pickup;
	}

	public void setOrders(Orders orders){
		this.orders = orders;
	}

	public Orders getOrders(){
		return orders;
	}

	public void setCompleted(int completed){
		this.completed = completed;
	}

	public int getCompleted(){
		return completed;
	}

	public void setPendings(int pendings){
		this.pendings = pendings;
	}

	public int getPendings(){
		return pendings;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}
}