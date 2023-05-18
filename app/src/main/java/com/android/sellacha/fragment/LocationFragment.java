package com.android.sellacha.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.sellacha.R;
import com.android.sellacha.adapter.LocationAdapter;
import com.android.sellacha.databinding.FragmentLocationBinding;
import com.android.sellacha.api.model.locationDM;


public class LocationFragment extends Fragment {
    FragmentLocationBinding binding;
    LocationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location, container, false);
        locationDM[] myListData = new locationDM[]{
                new locationDM("Name", true),
                new locationDM("Sontosh Nagar", true),
                new locationDM("Abids", true),
                new locationDM("Hyderabad", true),
                new locationDM("Abids", true),
                new locationDM("Hyderabad", true),
                new locationDM("Abids", true),
                new locationDM("Hyderabad", true), new locationDM("Abids", true),
                new locationDM("Hyderabad", true), new locationDM("Abids", true),
                new locationDM("Hyderabad", true),
                new locationDM("Abids", true),
                new locationDM("Hyderabad", true)


        };
        binding.locationList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new LocationAdapter(getContext(), myListData, 0);
        binding.locationList.setAdapter(adapter);
        return binding.getRoot();
    }
}