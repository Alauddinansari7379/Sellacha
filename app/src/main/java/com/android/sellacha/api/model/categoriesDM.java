package com.android.sellacha.api.model;

public class categoriesDM {



    String name;
    Boolean isSelected;
    int image;

    public categoriesDM() {
    }

    public categoriesDM(String name, Boolean isSelected, int image) {
        this.name = name;
        this.isSelected = isSelected;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
