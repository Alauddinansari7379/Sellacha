package com.android.sellacha.fragment.sell_register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import com.android.sellacha.R;
import com.android.sellacha.app.AppDelegate;
import com.android.sellacha.databinding.FragmentStoreInformationBinding;
import com.android.sellacha.fragment.BaseFragment;
import com.android.sellacha.utils.TextUtils;
import com.android.sellacha.utils.Validations;


public class StoreInformation extends BaseFragment {
    FragmentStoreInformationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_information, container, false);
        binding.saveBtn.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.themeFragment);
        });
        return binding.getRoot();
    }

    public void setData() {
        AppDelegate.instance.registerModel.setBusiness_name(binding.txtBusinessName.getText().toString());
        AppDelegate.instance.registerModel.setMob(binding.txtMobNo.getText().toString());
        AppDelegate.instance.registerModel.setPassword(binding.txtPassword.getText().toString());
        AppDelegate.instance.registerModel.setEmail(binding.txtEmail.getText().toString());
        AppDelegate.instance.registerModel.setType(binding.txtproductTxt.getText().toString());
        AppDelegate.instance.registerModel.setShop_type(binding.txtselectshoptp.getText().toString());
        AppDelegate.instance.registerModel.setBusiness_name(binding.txtReferralNo.getText().toString());
    }

    public boolean checkValidation() {
        if (TextUtils.isEmpty(binding.txtBusinessName.getText().toString())) {
            errorSnackBar(binding.getRoot(), "Please Enter Buissness Name");
            return false;
        }
        if (!Validations.validateEmail(binding.txtMobNo.getText().toString())) {
            errorSnackBar(binding.getRoot(), "Please Enter Mobile Number");
            return false;
        }
        if (TextUtils.isEmpty(binding.txtPassword.getText().toString())) {
            errorSnackBar(binding.getRoot(), "Please Enter Your Password");
            return false;
        }
        if (TextUtils.isEmpty(binding.txtEmail.getText().toString())) {
            errorSnackBar(binding.getRoot(), "Please Enter Email");
            return false;
        }
        if (TextUtils.isEmpty(binding.txtproductTxt.getText().toString())) {
            errorSnackBar(binding.getRoot(), "Please Select Product Type");
            return false;
        }
        if (TextUtils.isEmpty(binding.txtPasswordCon.getText().toString())) {
            errorSnackBar(binding.getRoot(), "Please Enter Confirm Password");
            return false;
        }
        if (TextUtils.isEmpty(binding.txtselectshoptp.getText().toString())) {
            errorSnackBar(binding.getRoot(), "Please Select Shop Type");
            return false;
        }
        return true;
    }
}