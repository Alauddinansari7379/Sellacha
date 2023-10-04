package com.android.sellacha.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.sellacha.R;
import com.android.sellacha.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {
    FragmentSettingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
//        binding.genrelConstrint.setOnClickListener(view -> {
//            Navigation.findNavController(binding.getRoot()).navigate(R.id.generalSettingFragment);
//        });

        binding.locationTv1.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.locationFragment);
        });
        return binding.getRoot();
    }
}