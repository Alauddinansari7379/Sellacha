package com.android.sellacha.api.model;

public class oderItemsDM {
    public oderItemsDM() {
    }

    Boolean isRadioSelected;
    String Order;
    String Date;
    String Type;

    public Boolean getRadioSelected() {
        return isRadioSelected;
    }

    public void setRadioSelected(Boolean radioSelected) {
        isRadioSelected = radioSelected;
    }

    public String getOrder() {
        return Order;
    }

    public void setOrder(String order) {
        Order = order;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public oderItemsDM(Boolean isRadioSelected, String order, String date, String type) {
        this.isRadioSelected = isRadioSelected;
        Order = order;
        Date = date;
        Type = type;
    }
}
