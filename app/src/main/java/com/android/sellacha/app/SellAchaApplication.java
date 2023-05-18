package com.android.sellacha.app;

import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDexApplication;

import com.android.sellacha.helper.PreferenceManger;
import com.android.sellacha.utils.CheckInternetConnection;

import org.apache.commons.lang3.StringUtils;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class SellAchaApplication extends MultiDexApplication {
    private static final String TAG = "THIS";
    private static SellAchaApplication instance;
    private static PreferenceManger preferenceManger;

    public static SellAchaApplication getInstance() {
        return instance;
    }

    public static PreferenceManger getPreferenceManger() {
        if (preferenceManger == null && getInstance() != null) {
            preferenceManger = new PreferenceManger(getInstance().getSharedPreferences(PreferenceManger.PREF_KEY, Context.MODE_PRIVATE));
        }
        return preferenceManger;
    }

    public static String getDefaultHeaders() {
        if (preferenceManger != null && !TextUtils.isEmpty(preferenceManger.getAuthToken())) {
            return StringUtils.capitalize(preferenceManger.getAuthToken());
        }
        return "";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setFontAttrId(io.github.inflationx.calligraphy3.R.attr.fontPath)
                                .build()))
                .build());

    }


    public boolean isInternetConnected(Context context) {
        if (new CheckInternetConnection(context).isConnected())
            return true;
        else {
            // ((BaseActivity) context).noInternetDialog();
        }
        return false;
    }


    public boolean isInternet(Context context) {
        if (new CheckInternetConnection(this).isConnected())
            return true;
        return false;
    }
}
