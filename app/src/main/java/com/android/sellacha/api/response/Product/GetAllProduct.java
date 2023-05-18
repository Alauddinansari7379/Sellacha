package com.android.sellacha.api.response.Product;

import com.google.gson.annotations.SerializedName;

public class GetAllProduct{

	@SerializedName("request")
	private Request request;

	@SerializedName("incomplete")
	private int incomplete;

	@SerializedName("src")
	private String src;

	@SerializedName("drafts")
	private int drafts;

	@SerializedName("type")
	private int type;

	@SerializedName("posts")
	private Posts posts;

	@SerializedName("actives")
	private int actives;

	@SerializedName("trash")
	private int trash;

	public Request getRequest(){
		return request;
	}

	public int getIncomplete(){
		return incomplete;
	}

	public String getSrc(){
		return src;
	}

	public int getDrafts(){
		return drafts;
	}

	public int getType(){
		return type;
	}

	public Posts getPosts(){
		return posts;
	}

	public int getActives(){
		return actives;
	}

	public int getTrash(){
		return trash;
	}
}