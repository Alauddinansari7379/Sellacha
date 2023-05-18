package com.android.sellacha.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.sellacha.R;
import com.android.sellacha.adapter.OrderInfoAdapter;
import com.android.sellacha.api.response.OrderByID.OrderByID;
import com.android.sellacha.api.response.OrderByID.OrderContent;
import com.android.sellacha.api.response.OrderByID.OrderItemItem;
import com.android.sellacha.api.service.MainService;
import com.android.sellacha.databinding.FragmentOrderDetailBinding;
import com.android.sellacha.utils.AppProgressBar;
import com.android.sellacha.utils.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonNull;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailFragment extends BaseFragment {
    FragmentOrderDetailBinding binding;
    OrderInfoAdapter orderAdapter;
    int orderID;
    List<OrderItemItem> orderList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false);
        if (getArguments() != null) {
            orderID = getArguments().getInt("orderID");
        }
        getOrderByID();
        binding.printInvoice.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://thedemostore.in/seller/orders/invoicenew/21/22"));
            startActivity(browserIntent);
        });
        binding.paymentOption.setOnClickListener(v -> {
            paymentDialog(binding.paymentOption);
        });
        binding.orderStatusSelector.setOnClickListener(v -> {
            filterDialog(binding.orderStatusSelector);
        });

        return binding.getRoot();
    }

    public void getOrderByID() {
        AppProgressBar.showLoaderDialog(mContext);
        MainService.getOrderByID(mContext, orderID).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorSnackBar(binding.getRoot(), getString(R.string.something_wrong));
            } else {
                if (!(response.getData() instanceof JsonNull)) {
                    if (response.getData() != null) {
                        OrderByID orderByID = new Gson().fromJson(response.getData(), OrderByID.class);
                        setData(orderByID);
                    } else {
                        showAlertDialog(getString(R.string.app_name), response.getMessage(), "OK", "", DialogFragment::dismiss);
                    }
                } else {
                    errorSnackBar(binding.getRoot(), response.getMessage());
                }
            }
            AppProgressBar.hideLoaderDialog();
        });
    }

    public void setData(OrderByID data) {
        binding.parentView.setVisibility(View.VISIBLE);
        binding.orderNoTv.setText("Order No:  " + TextUtils.getString(data.getInfo().getOrderNo()));
        OrderContent content = data.getOrderContent();
        binding.name.setText(TextUtils.getString(content.getName()));
        binding.email.setText(TextUtils.getString(content.getEmail()));
        binding.phone.setText(TextUtils.getString(content.getPhone()));
        binding.zipcode.setText(TextUtils.getString(content.getZipCode()));
        binding.address.setText(TextUtils.getString(content.getAddress()));
        binding.transactionsIDTxt.setText(TextUtils.getString(data.getInfo().getTransactionId()));
        orderList = data.getInfo().getOrderItem();
        binding.orderName.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        orderAdapter = new OrderInfoAdapter(getContext(), orderList, 0);
        binding.orderName.setAdapter(orderAdapter);

        if ((TextUtils.getString(data.getInfo().getPaymentStatus()).equalsIgnoreCase("1"))) {
            binding.paymentStatusTxt.setBackgroundResource(R.drawable.green_10_bg);
            binding.paymentStatusTxt.setText("Completed");

        } else if ((TextUtils.getString(data.getInfo().getPaymentStatus()).equalsIgnoreCase("2"))) {
            binding.paymentStatusTxt.setBackgroundResource(R.drawable.yellow_10_bg);
            //  binding.payment.setPadding(0, 0, 0, 0);
            binding.paymentStatusTxt.setText("Pending");
        }


        if (TextUtils.getString(data.getInfo().getStatus().toString()).equals("completed")) {
            binding.orderStatusC.setText("Competed");
            binding.orderStatusC.setBackgroundResource(R.drawable.green_10_bg);
        } else if (TextUtils.getString(data.getInfo().getStatus().toString()).equals("pending")) {
            binding.orderStatusC.setText("Pending");
            binding.orderStatusC.setBackgroundResource(R.drawable.yellow_10_bg);
        }
    }

    public void paymentDialog(TextView textView) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setTitle("Select Payment Status");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.dialog_txt_item);
        arrayAdapter.add("Pending");
        arrayAdapter.add("Complete");
        arrayAdapter.add("Cancel");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                textView.setText(strName);
            }
        });
        builderSingle.show();
    }


    public void filterDialog(TextView textView) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setTitle("Select fulfillment");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.dialog_txt_item);
        arrayAdapter.add("Awaiting processing");
        arrayAdapter.add("Processing");
        arrayAdapter.add("Ready-for-pickup");
        arrayAdapter.add("Completed");
        arrayAdapter.add("Archived");
        arrayAdapter.add("Canceled");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                textView.setText(strName);
            }
        });
        builderSingle.show();
    }

}