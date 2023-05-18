package com.android.sellacha.app;


import com.android.sellacha.RegisterModel;

import java.util.ArrayList;

public class AppDelegate {

    public static AppDelegate instance;

    String getApp;
    ArrayList<String> getFulfillment = new ArrayList<>();
   public RegisterModel registerModel = new RegisterModel();


    public static void setInstance(AppDelegate instance) {
        AppDelegate.instance = instance;
    }

    public String getGetApp() {
        return getApp;
    }

    public void setGetApp(String getApp) {
        this.getApp = getApp;
    }

    public static AppDelegate getInstance() {
        if (instance == null) {
            instance = new AppDelegate();
        }
        return instance;
    }

    public ArrayList<String> getGetFulfillment() {
        getFulfillment.add("Awaiting processing");
        getFulfillment.add("processing");
        getFulfillment.add("ready-for-pickup");
        getFulfillment.add("completed");
        getFulfillment.add("archived");
        getFulfillment.add("canceled");
        return getFulfillment;
    }
}