package com.android.sellacha.api.response.GetAllorder;

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

	public void setRequest(Request request){
		this.request = request;
	}

	public Request getRequest(){
		return request;
	}

	public void setServer(Server server){
		this.server = server;
	}

	public Server getServer(){
		return server;
	}

	public void setHeaders(Headers headers){
		this.headers = headers;
	}

	public Headers getHeaders(){
		return headers;
	}

	public void setQuery(Query query){
		this.query = query;
	}

	public Query getQuery(){
		return query;
	}

	public void setFiles(Files files){
		this.files = files;
	}

	public Files getFiles(){
		return files;
	}

	public void setAttributes(Attributes attributes){
		this.attributes = attributes;
	}

	public Attributes getAttributes(){
		return attributes;
	}

	public void setCookies(Cookies cookies){
		this.cookies = cookies;
	}

	public Cookies getCookies(){
		return cookies;
	}
}