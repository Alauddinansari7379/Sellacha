package com.android.sellacha.api.model;


public class reviewRatingDM {

    String Ratings;
    String Names;
    Boolean radioButton;

    public String getRatings() {
        return Ratings;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String names) {
        Names = names;
    }

    public Boolean getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(Boolean radioButton) {
        this.radioButton = radioButton;
    }

    public reviewRatingDM() {
    }

    public reviewRatingDM(String ratings, String names, Boolean radioButton) {
        Ratings = ratings;
        Names = names;
        this.radioButton = radioButton;
    }
}
