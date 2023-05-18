package com.android.sellacha.fragment.sell_register;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sellacha.R;
import com.android.sellacha.databinding.FragmentColorSchemeBinding;
import com.android.sellacha.databinding.FragmentThemeBinding;

public class ColorSchemeFragment extends Fragment {

    FragmentColorSchemeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_color_scheme, container, false);
        binding.saveBtn.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.addCategoryFragment);
        });
        return binding.getRoot();
    }
}