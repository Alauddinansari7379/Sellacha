package com.android.sellacha.api.response.GetAllorder;

import com.google.gson.annotations.SerializedName;

public class Customer{

	@SerializedName("domain_id")
	private String domainId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("mobile")
	private Object mobile;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("created_by")
	private String createdBy;

	@SerializedName("email")
	private String email;

	public void setDomainId(String domainId){
		this.domainId = domainId;
	}

	public String getDomainId(){
		return domainId;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMobile(Object mobile){
		this.mobile = mobile;
	}

	public Object getMobile(){
		return mobile;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}