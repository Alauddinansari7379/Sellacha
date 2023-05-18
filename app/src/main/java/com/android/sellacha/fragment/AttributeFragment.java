package com.android.sellacha.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.sellacha.R;
import com.android.sellacha.adapter.AttributeAdapter;
import com.android.sellacha.databinding.FragmentAttributeBinding;
import com.android.sellacha.api.model.attributeDM;

import java.util.ArrayList;


public class AttributeFragment extends Fragment {
    FragmentAttributeBinding binding;

    AttributeAdapter attributeAdapter;
    ArrayList<attributeDM> attributeArr = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_attribute, container, false);
        attributeArr.add(new attributeDM(false, "Test", "L"));
        attributeArr.add(new attributeDM(true, "Test1", "XXL"));
        attributeArr.add(new attributeDM(false, "Test 2", "M"));
        attributeArr.add(new attributeDM(false, "Test", "L"));
        attributeArr.add(new attributeDM(true, "Test1", "XXL"));
        attributeArr.add(new attributeDM(false, "Test 2", "M"));
        attributeArr.add(new attributeDM(false, "Test", "L"));
        attributeArr.add(new attributeDM(true, "Test1", "XXL"));
        attributeArr.add(new attributeDM(false, "Test 2", "M"));


        binding.attributeRc.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        attributeAdapter = new AttributeAdapter(getContext(), attributeArr);
        attributeAdapter.notifyDataSetChanged();
        binding.attributeRc.setAdapter(attributeAdapter);
        return binding.getRoot();
    }
}