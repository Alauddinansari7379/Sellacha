package com.android.sellacha.api.response.OrderByID;

import com.google.gson.annotations.SerializedName;

public class Getway{

	@SerializedName("is_admin")
	private String isAdmin;

	@SerializedName("featured")
	private String featured;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("menu_status")
	private String menuStatus;

	@SerializedName("id")
	private int id;

	@SerializedName("type")
	private String type;

	@SerializedName("slug")
	private String slug;

	@SerializedName("p_id")
	private Object pId;

	public String getIsAdmin(){
		return isAdmin;
	}

	public String getFeatured(){
		return featured;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getUserId(){
		return userId;
	}

	public String getName(){
		return name;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getMenuStatus(){
		return menuStatus;
	}

	public int getId(){
		return id;
	}

	public String getType(){
		return type;
	}

	public String getSlug(){
		return slug;
	}

	public Object getPId(){
		return pId;
	}
}