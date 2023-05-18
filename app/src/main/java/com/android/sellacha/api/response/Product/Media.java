package com.android.sellacha.api.response.Product;

import com.google.gson.annotations.SerializedName;

public class Media{

	@SerializedName("id")
	private int id;

	@SerializedName("url")
	private String url;

	public int getId(){
		return id;
	}

	public String getUrl(){
		return url;
	}
}