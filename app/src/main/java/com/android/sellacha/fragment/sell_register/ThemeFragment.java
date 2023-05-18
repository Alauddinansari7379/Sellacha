package com.android.sellacha.fragment.sell_register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.sellacha.R;
import com.android.sellacha.databinding.FragmentThemeBinding;

public class ThemeFragment extends Fragment {

    FragmentThemeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theme, container, false);
        binding.saveBtn.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.uploadLogoFragment);
        });
        return binding.getRoot();
    }
}