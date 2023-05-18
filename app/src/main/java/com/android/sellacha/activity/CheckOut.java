package com.android.sellacha.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.sellacha.R;
import com.android.sellacha.databinding.ActivityCheckOutBinding;
import com.android.sellacha.utils.StatusBarUtils;

public class CheckOut extends AppCompatActivity {
    ActivityCheckOutBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_out);
        StatusBarUtils.transparentStatusAndNavigation(this);

        binding.backBtn.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.makeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (TextUtils.isEmpty(customerName.getText().toString())) {
//                    customerName.setError("Name is compulsory");
//                }
//                if (TextUtils.isEmpty(customerPhone.getText().toString())) {
//                    customerPhone.setError("Phone number is compulsory");
//                }
//                if (TextUtils.isEmpty(customerEmail.getText().toString())) {
//                    customerEmail.setError("Email is compulsory");
//                }
//                if (TextUtils.isEmpty(customerType.getText().toString())) {
//                    customerType.setError("Type is compulsory");
//                }
//                if (TextUtils.isEmpty(deliveryType.getText().toString())) {
//                    deliveryType.setError("Type is compulsory");
//                }
//                if (TextUtils.isEmpty(address.getText().toString())) {
//                    address.setError("address is compulsory");
//                }
//                if (TextUtils.isEmpty(location.getText().toString())) {
//                    location.setError("location is compulsory");
//                }
//                if (TextUtils.isEmpty(zip.getText().toString())) {
//                    zip.setError("zip is compulsory");
//                }
//                if (TextUtils.isEmpty(paymentId.getText().toString())) {
//                    paymentId.setError("payment id is compulsory");
//                }
//                if (TextUtils.isEmpty(orderNote.getText().toString())) {
//                    orderNote.setError("order note is compulsory");
//                }
//
            }
        });
    }

    public void orderCreate() {

    }
}