package com.android.sellacha.api.response.OrderByID;

import com.google.gson.annotations.SerializedName;

public class Term{

	@SerializedName("featured")
	private String featured;

	@SerializedName("service_location")
	private Object serviceLocation;

	@SerializedName("question3")
	private Object question3;

	@SerializedName("answer3")
	private Object answer3;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("question2")
	private Object question2;

	@SerializedName("answer2")
	private Object answer2;

	@SerializedName("question5")
	private Object question5;

	@SerializedName("answer5")
	private Object answer5;

	@SerializedName("question4")
	private Object question4;

	@SerializedName("answer4")
	private Object answer4;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private String type;

	@SerializedName("question1")
	private Object question1;

	@SerializedName("answer1")
	private Object answer1;

	@SerializedName("is_admin")
	private String isAdmin;

	@SerializedName("youtube_link")
	private Object youtubeLink;

	@SerializedName("service_type")
	private Object serviceType;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("id")
	private int id;

	@SerializedName("slug")
	private String slug;

	@SerializedName("status")
	private String status;

	public String getFeatured(){
		return featured;
	}

	public Object getServiceLocation(){
		return serviceLocation;
	}

	public Object getQuestion3(){
		return question3;
	}

	public Object getAnswer3(){
		return answer3;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public Object getQuestion2(){
		return question2;
	}

	public Object getAnswer2(){
		return answer2;
	}

	public Object getQuestion5(){
		return question5;
	}

	public Object getAnswer5(){
		return answer5;
	}

	public Object getQuestion4(){
		return question4;
	}

	public Object getAnswer4(){
		return answer4;
	}

	public String getTitle(){
		return title;
	}

	public String getType(){
		return type;
	}

	public Object getQuestion1(){
		return question1;
	}

	public Object getAnswer1(){
		return answer1;
	}

	public String getIsAdmin(){
		return isAdmin;
	}

	public Object getYoutubeLink(){
		return youtubeLink;
	}

	public Object getServiceType(){
		return serviceType;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getUserId(){
		return userId;
	}

	public int getId(){
		return id;
	}

	public String getSlug(){
		return slug;
	}

	public String getStatus(){
		return status;
	}
}