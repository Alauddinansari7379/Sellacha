package com.android.sellacha.api.model;

public class attributeDM {


    Boolean isSelected;
    String name;
    String varaitions;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVaraitions() {
        return varaitions;
    }

    public void setVaraitions(String varaitions) {
        this.varaitions = varaitions;
    }

    public attributeDM() {
    }


    public attributeDM(Boolean isSelected, String name, String varaitions) {
        this.isSelected = isSelected;
        this.name = name;
        this.varaitions = varaitions;
    }

}
