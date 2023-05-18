package com.android.sellacha.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.sellacha.R;
import com.android.sellacha.adapter.NotificationAdapter;
import com.android.sellacha.adapter.NotificationResponse;
import com.android.sellacha.databinding.FragmentNotificationBinding;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    ArrayList<NotificationResponse> notificationResponses = new ArrayList<>();
    FragmentNotificationBinding binding;
    NotificationAdapter notificationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);

        notificationResponses.add(new NotificationResponse("Your Order Delivered", "15 Jan 2022"));
        notificationResponses.add(new NotificationResponse("Your Order Delivered", "12 Feb 2022"));
        notificationResponses.add(new NotificationResponse("Your Order Delivered", "22 May 2022"));
        notificationResponses.add(new NotificationResponse("Your Order Delivered", "15 Jun 2022"));
        notificationResponses.add(new NotificationResponse("Your Order Delivered", "16 Jul 2022"));
        notificationResponses.add(new NotificationResponse("Your Order Delivered", "13 Jul 2022"));
        notificationResponses.add(new NotificationResponse("Your Order Delivered", "01 Jul 2022"));


        binding.notificationList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        notificationAdapter = new NotificationAdapter(getContext(), notificationResponses);
        binding.notificationList.setAdapter(notificationAdapter);
        ;
        return binding.getRoot();
    }
}