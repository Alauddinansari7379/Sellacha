package com.android.sellacha.fragment.sell_register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.sellacha.R;
import com.android.sellacha.databinding.FragmentSellerAddProduct2Binding;


public class SellerAddProduct2Fragment extends Fragment {

    FragmentSellerAddProduct2Binding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_seller_add_product2, container, false);
        binding.saveBtn.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.googleAnalyticsFragment);
        });
        return binding.getRoot();
    }
}