package com.android.sellacha.fragment.sell_register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.sellacha.R;
import com.android.sellacha.databinding.FragmentSellerFacebookBinding;


public class SellerFacebookFragment extends Fragment {
    FragmentSellerFacebookBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_seller_facebook, container, false);
        binding.saveBtn.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.whatsappApiFragment);
        });
        return binding.getRoot();
    }
}