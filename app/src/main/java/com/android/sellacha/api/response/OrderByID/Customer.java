package com.android.sellacha.api.response.OrderByID;

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

	public String getDomainId(){
		return domainId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getName(){
		return name;
	}

	public Object getMobile(){
		return mobile;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public String getEmail(){
		return email;
	}
}