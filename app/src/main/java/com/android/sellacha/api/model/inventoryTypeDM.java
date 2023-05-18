package com.android.sellacha.api.model;

public class inventoryTypeDM {



    String typeName;
    int count;

    public inventoryTypeDM() {
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public inventoryTypeDM(String typeName, int count) {
        this.typeName = typeName;
        this.count = count;
    }

}
