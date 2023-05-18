package com.android.sellacha.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.android.sellacha.LogIn.LoginActivity;
import com.android.sellacha.R;
import com.android.sellacha.databinding.ActivityRegisterBinding;
import com.android.sellacha.utils.StatusBarUtils;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

public class RegisterActivity extends BaseActivity {
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        StatusBarUtils.transparentStatusAndNavigation(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        openLink("Login", binding.alreadyAccTv);
        binding.SignInBtn.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, HomeDashBoard.class));
            finishAffinity();
        });
    }

    public void openLink(String str, TextView text) {
        Link link = new Link(str)
                .setTextColor(Color.parseColor("#0191B5"))
                .setHighlightAlpha(.4f)
                .setUnderlined(false)
                .setBold(true)
                .setOnClickListener(clickedText -> {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finishAffinity();
                });
        LinkBuilder.on(text)
                .addLink(link)
                .build();
    }
}