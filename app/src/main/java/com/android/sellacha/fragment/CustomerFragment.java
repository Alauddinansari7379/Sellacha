package com.android.sellacha.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.sellacha.R;
import com.android.sellacha.adapter.CustomerAdapter;
import com.android.sellacha.databinding.FragmentCustomerBinding;
import com.android.sellacha.api.model.inventoryDM;
import com.android.sellacha.api.model.inventoryTypeDM;

import java.util.ArrayList;


public class CustomerFragment extends Fragment {
    FragmentCustomerBinding binding;
    CustomerAdapter inventoryAdapter;
    ArrayList<inventoryTypeDM> inventoryTypeArr = new ArrayList<>();
    ArrayList<inventoryDM> inventoryArr = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer, container, false);
        inventoryTypeArr.add(new inventoryTypeDM("Total", 4));
        inventoryTypeArr.add(new inventoryTypeDM("In Stock", 41));
        inventoryTypeArr.add(new inventoryTypeDM("Incomplete", 3));


        binding.createAttribute.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.createCustomerFragment);
        });
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Roy", "roy@gmail.com", "Yes", "In Stock"));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Joe", "joe@gmai.com", "Yes", "In Stock"));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Jason", "jeson@gmail.com", "Yes", "Out Stock"));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Roy", "roy@gmail.com", "Yes", "In Stock"));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Joe", "joe@gmai.com", "Yes", "In Stock"));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Jason", "jeson@gmail.com", "Yes", "Out Stock"));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Roy", "roy@gmail.com", "Yes", "In Stock"));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Joe", "joe@gmai.com", "Yes", "In Stock"));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Jason", "jeson@gmail.com", "Yes", "Out Stock"));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Roy", "roy@gmail.com", "Yes", "In Stock"));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Joe", "joe@gmai.com", "Yes", "In Stock"));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Jason", "jeson@gmail.com", "Yes", "Out Stock"));


        binding.attributeRc.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        inventoryAdapter = new CustomerAdapter(getContext(), inventoryArr);
        binding.attributeRc.setAdapter(inventoryAdapter);
        return binding.getRoot();
    }
}