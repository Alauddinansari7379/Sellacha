package com.android.sellacha.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.sellacha.R;
import com.android.sellacha.adapter.BrandsAdapter;
import com.android.sellacha.databinding.FragmentBrandBinding;
import com.android.sellacha.api.model.reviewRatingDM;

import java.util.ArrayList;


public class BrandFragment extends Fragment {

    FragmentBrandBinding binding;
    BrandsAdapter brandsAdapter;
    ArrayList<reviewRatingDM> reviewRatingArr = new ArrayList<>();

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_brand, container, false);
        reviewRatingArr.add(new reviewRatingDM("4", "Test", false));
        reviewRatingArr.add(new reviewRatingDM("2", "Test1", true));
        reviewRatingArr.add(new reviewRatingDM("5", "Test 2", false));
        reviewRatingArr.add(new reviewRatingDM("4", "Test", false));
        reviewRatingArr.add(new reviewRatingDM("2", "Test1", true));
        reviewRatingArr.add(new reviewRatingDM("5", "Test 2", false));
        reviewRatingArr.add(new reviewRatingDM("4", "Test", false));
        reviewRatingArr.add(new reviewRatingDM("2", "Test1", true));
        reviewRatingArr.add(new reviewRatingDM("5", "Test 2", false));
        reviewRatingArr.add(new reviewRatingDM("4", "Test", false));
        reviewRatingArr.add(new reviewRatingDM("2", "Test1", true));
        reviewRatingArr.add(new reviewRatingDM("5", "Test 2", false));


        binding.reviewRatingRc.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        brandsAdapter = new BrandsAdapter(getContext(), reviewRatingArr);
        binding.reviewRatingRc.setAdapter(brandsAdapter);
        return binding.getRoot();
    }
}