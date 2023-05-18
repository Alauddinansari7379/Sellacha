package com.android.sellacha.api.model;

public class locationDM {
    public locationDM(String locationNames, Boolean isSelected) {
        this.locationNames = locationNames;
        this.isSelected = isSelected;
    }

    public locationDM() {
    }

    String locationNames;
    Boolean isSelected;

    public String getLocationNames() {
        return locationNames;
    }

    public void setLocationNames(String locationNames) {
        this.locationNames = locationNames;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
