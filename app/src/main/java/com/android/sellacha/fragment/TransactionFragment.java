package com.android.sellacha.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.sellacha.R;
import com.android.sellacha.adapter.TransactionsAdapter;
import com.android.sellacha.databinding.FragmentTransactionBinding;
import com.android.sellacha.api.model.transactionsDM;

import java.util.ArrayList;

public class TransactionFragment extends Fragment {
    FragmentTransactionBinding binding;
    TransactionsAdapter transactionsAdapter;
    ArrayList<transactionsDM> transactionsArr = new ArrayList<>();

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction, container, false);
        transactionsArr.add(new transactionsDM(4.5, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(4.2, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(5.0, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(4.5, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(4.2, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(5.0, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(4.5, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(4.2, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(5.0, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(4.5, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(4.2, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(5.0, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(4.5, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(4.2, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(5.0, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(4.5, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(4.2, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(5.0, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(4.5, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(4.2, "Completed", "COD", "#45245", "#546565635656"));
        transactionsArr.add(new transactionsDM(5.0, "Completed", "COD", "#45245", "#546565635656"));


        binding.transactionsRc.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        transactionsAdapter = new TransactionsAdapter(getContext(), transactionsArr);
        binding.transactionsRc.setAdapter(transactionsAdapter);
        return binding.getRoot();
    }
}