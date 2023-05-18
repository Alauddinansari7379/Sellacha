package com.android.sellacha.api.model;

public class filterItemsDM {
    public filterItemsDM(String filterType, int filterTypeQty) {
        this.filterType = filterType;
        this.filterTypeQty = filterTypeQty;
    }

    String filterType;
    int filterTypeQty;

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public int getFilterTypeQty() {
        return filterTypeQty;
    }

    public void setFilterTypeQty(int filterTypeQty) {
        this.filterTypeQty = filterTypeQty;
    }

    public filterItemsDM() {
    }
}
