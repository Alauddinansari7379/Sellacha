package com.android.sellacha.api.response.Product;

import com.google.gson.annotations.SerializedName;

public class Preview{

	@SerializedName("media_id")
	private String mediaId;

	@SerializedName("term_id")
	private String termId;

	@SerializedName("media")
	private Media media;

	public String getMediaId(){
		return mediaId;
	}

	public String getTermId(){
		return termId;
	}

	public Media getMedia(){
		return media;
	}
}