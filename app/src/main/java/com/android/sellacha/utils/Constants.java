package com.android.sellacha.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class Constants {
    public static final String GOOD_API_DEFAULT = "default";
    public static int getDeviceWidth(Context context) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            return displayMetrics.widthPixels;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
