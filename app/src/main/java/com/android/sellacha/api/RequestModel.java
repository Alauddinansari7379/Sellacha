package com.android.sellacha.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestModel implements Serializable {

    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}