package com.android.sellacha.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.sellacha.R;
import com.android.sellacha.databinding.ActivityForgotPasswordBinding;
import com.android.sellacha.utils.StatusBarUtils;
import com.android.sellacha.utils.TextUtils;
import com.android.sellacha.utils.Validations;

public class ForgotPassword extends BaseActivity {
    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_forgot_password);
        StatusBarUtils.statusBarColor(this, R.color.primary_bg);
        binding.submit.setOnClickListener(v -> {
            if (checkValidation()) {
                startActivity(new Intent(this, OtpActivity.class));
                overridePendingTransition(R.anim.bottom_anim, R.anim.bottom_out_anim);
            }
        });
    }

    public boolean checkValidation() {
        if (TextUtils.isEmpty(binding.oldAddresLb.getText().toString())) {
            errorSnackBar(binding.getRoot(), "Please Enter Your Email");
            return false;
        }
        if (!Validations.validateEmail(binding.oldAddresLb.getText().toString())) {
            errorSnackBar(binding.getRoot(), "Please Enter Valid Email");
            return false;
        }
        return true;
    }
}