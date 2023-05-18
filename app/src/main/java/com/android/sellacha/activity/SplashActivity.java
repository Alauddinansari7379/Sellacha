package com.android.sellacha.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.android.sellacha.LogIn.LoginActivity;
import com.android.sellacha.R;
import com.android.sellacha.app.SellAchaApplication;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (SellAchaApplication.getPreferenceManger().getUserDetails() != null) {
                    startActivity(new Intent(SplashActivity.this, HomeDashBoard.class));
                    overridePendingTransition(R.anim.bottom_anim, R.anim.bottom_out_anim);
                    finishAffinity();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    overridePendingTransition(R.anim.bottom_anim, R.anim.bottom_out_anim);
                    finishAffinity();
                }
            }
        }, 2000);
    }
}