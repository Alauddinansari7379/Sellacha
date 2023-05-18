package com.android.sellacha.fragment.sell_register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.sellacha.R;
import com.android.sellacha.activity.HomeDashBoard;
import com.android.sellacha.databinding.FragmentSellerWhatsappBinding;

public class SellerWhatsappFragment extends Fragment {
    FragmentSellerWhatsappBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_seller_whatsapp, container, false);
        binding.saveBtn.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), HomeDashBoard.class));
            getActivity().overridePendingTransition(R.anim.bottom_anim, R.anim.bottom_out_anim);
        });
        return binding.getRoot();
    }
}