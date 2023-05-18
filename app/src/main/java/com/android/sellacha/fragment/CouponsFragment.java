package com.android.sellacha.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.sellacha.R;
import com.android.sellacha.adapter.CouponAdapter;
import com.android.sellacha.databinding.FragmentCouponsBinding;
import com.android.sellacha.api.model.reviewRatingDM;

import java.util.ArrayList;

public class CouponsFragment extends Fragment {
    FragmentCouponsBinding binding;
    CouponAdapter couponAdapter;
    ArrayList<reviewRatingDM> reviewRatingArr = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coupons, container, false);
        reviewRatingArr.add(new reviewRatingDM("4", "12/05/2020", false));
        reviewRatingArr.add(new reviewRatingDM("2", "18/05/2020", true));
        reviewRatingArr.add(new reviewRatingDM("5", "19/05/2020", false));
        reviewRatingArr.add(new reviewRatingDM("4", "12/05/2020", false));
        reviewRatingArr.add(new reviewRatingDM("2", "18/05/2020", true));
        reviewRatingArr.add(new reviewRatingDM("5", "19/05/2020", false));
        reviewRatingArr.add(new reviewRatingDM("4", "12/05/2020", false));
        reviewRatingArr.add(new reviewRatingDM("2", "18/05/2020", true));
        reviewRatingArr.add(new reviewRatingDM("5", "19/05/2020", false));
        reviewRatingArr.add(new reviewRatingDM("4", "12/05/2020", false));
        reviewRatingArr.add(new reviewRatingDM("2", "18/05/2020", true));
        reviewRatingArr.add(new reviewRatingDM("5", "19/05/2020", false));


        binding.reviewRatingRc.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        couponAdapter = new CouponAdapter(getContext(), reviewRatingArr);
        couponAdapter.notifyDataSetChanged();
        binding.reviewRatingRc.setAdapter(couponAdapter);
        return binding.getRoot();
    }
}
