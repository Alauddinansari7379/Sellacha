package com.android.sellacha.api.response.Product;

import com.google.gson.annotations.SerializedName;

public class LinksItem{

	@SerializedName("active")
	private boolean active;

	@SerializedName("label")
	private String label;

	@SerializedName("url")
	private Object url;

	public boolean isActive(){
		return active;
	}

	public String getLabel(){
		return label;
	}

	public Object getUrl(){
		return url;
	}
}