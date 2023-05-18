package com.android.sellacha.api.response.Product;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("preview")
	private Preview preview;

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

	@SerializedName("updated_at")
	private String updatedAt;


	@SerializedName("formate_date")
	private String formate_date;


	@SerializedName("id")
	private int id;

	@SerializedName("slug")
	private String slug;

	@SerializedName("order_count")
	private String orderCount;

	@SerializedName("service_type")
	private Object serviceType;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("status")
	private String status;

	public Preview getPreview(){
		return preview;
	}

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

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getId(){
		return id;
	}

	public String getSlug(){
		return slug;
	}

	public String getOrderCount(){
		return orderCount;
	}

	public Object getServiceType(){
		return serviceType;
	}

	public String getUserId(){
		return userId;
	}


	public void setPreview(Preview preview) {
		this.preview = preview;
	}

	public void setFeatured(String featured) {
		this.featured = featured;
	}

	public void setServiceLocation(Object serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public void setQuestion3(Object question3) {
		this.question3 = question3;
	}

	public void setAnswer3(Object answer3) {
		this.answer3 = answer3;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setQuestion2(Object question2) {
		this.question2 = question2;
	}

	public void setAnswer2(Object answer2) {
		this.answer2 = answer2;
	}

	public void setQuestion5(Object question5) {
		this.question5 = question5;
	}

	public void setAnswer5(Object answer5) {
		this.answer5 = answer5;
	}

	public void setQuestion4(Object question4) {
		this.question4 = question4;
	}

	public void setAnswer4(Object answer4) {
		this.answer4 = answer4;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setQuestion1(Object question1) {
		this.question1 = question1;
	}

	public void setAnswer1(Object answer1) {
		this.answer1 = answer1;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setYoutubeLink(Object youtubeLink) {
		this.youtubeLink = youtubeLink;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getFormate_date() {
		return formate_date;
	}

	public void setFormate_date(String formate_date) {
		this.formate_date = formate_date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}

	public void setServiceType(Object serviceType) {
		this.serviceType = serviceType;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}