package com.android.sellacha.Products.Inventory.activity;

import static com.android.sellacha.utils.DateUtils.getCurrentDay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.sellacha.R;
import com.android.sellacha.adapter.OrderFilterSelector;
import com.android.sellacha.adapter.InventoryAdapter;
import com.android.sellacha.api.model.filterItemsDM;
import com.android.sellacha.api.model.inventoryDM;
import com.android.sellacha.api.model.inventoryTypeDM;
import com.android.sellacha.databinding.FragmentInventoryBinding;

import java.util.ArrayList;

public class InventoryFragment extends Fragment {
    FragmentInventoryBinding binding;

    OrderFilterSelector filterSelectorAdapter;
    InventoryAdapter inventoryAdapter;

    ArrayList<inventoryDM> inventoryArr = new ArrayList<>();
    ArrayList<inventoryTypeDM> inventoryTypeArr = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inventory, container, false);
        inventoryTypeArr.add(new inventoryTypeDM("Total", 4));
        inventoryTypeArr.add(new inventoryTypeDM("In Stock", 41));
        inventoryTypeArr.add(new inventoryTypeDM("Incomplete", 3));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Cable", "23233", "Yes", "In Stock"));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Cable", "23233", "Yes", "In Stock"));
        inventoryArr.add(new inventoryDM(R.drawable.icn_image, "Cable", "23233", "Yes", "Out Stock"));

        filterItemsDM[] myListData = new filterItemsDM[]{
                new filterItemsDM("Total", 12),
                new filterItemsDM("In Stock", 10),
                new filterItemsDM("Incomplete", 2)

        };

//        binding.inventoryTypeRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        filterSelectorAdapter = new FilterSelectorAdapter(getContext(), myListData, 0);
//        binding.inventoryTypeRv.setAdapter(filterSelectorAdapter);

        binding.inventoryRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        inventoryAdapter = new InventoryAdapter(getContext(), inventoryArr);
        binding.inventoryRv.setAdapter(inventoryAdapter);
        return binding.getRoot();
    }
}