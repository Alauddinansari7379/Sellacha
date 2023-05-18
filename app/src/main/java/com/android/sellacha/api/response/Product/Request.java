package com.android.sellacha.api.response.Product;

import com.google.gson.annotations.SerializedName;

public class Request{

	@SerializedName("request")
	private Request request;

	@SerializedName("server")
	private Server server;

	@SerializedName("headers")
	private Headers headers;

	@SerializedName("query")
	private Query query;

	@SerializedName("files")
	private Files files;

	@SerializedName("attributes")
	private Attributes attributes;

	@SerializedName("cookies")
	private Cookies cookies;

	public Request getRequest(){
		return request;
	}

	public Server getServer(){
		return server;
	}

	public Headers getHeaders(){
		return headers;
	}

	public Query getQuery(){
		return query;
	}

	public Files getFiles(){
		return files;
	}

	public Attributes getAttributes(){
		return attributes;
	}

	public Cookies getCookies(){
		return cookies;
	}
}