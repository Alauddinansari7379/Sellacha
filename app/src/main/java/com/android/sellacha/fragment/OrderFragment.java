package com.android.sellacha.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.sellacha.R;
import com.android.sellacha.activity.CheckOut;
import com.android.sellacha.adapter.OrderAdapter;
import com.android.sellacha.adapter.OrderFilterSelector;
import com.android.sellacha.api.model.filterItemsDM;
import com.android.sellacha.api.response.GetAllorder.OrderItem;
import com.android.sellacha.api.response.GetAllorder.OrderResponse;
import com.android.sellacha.api.service.MainService;
import com.android.sellacha.databinding.FilterDailogBinding;
import com.android.sellacha.databinding.FragmentOrderBinding;
import com.android.sellacha.utils.AppProgressBar;
import com.google.gson.Gson;
import com.google.gson.JsonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class OrderFragment extends BaseFragment {
    FragmentOrderBinding binding;
    OrderFilterSelector filterNameAdapter;
    OrderAdapter orderAdapter;
    List<OrderItem> orderList = new ArrayList<>();
    filterItemsDM[] myListData;
    Dialog filterDialog;
    int filterSelector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
            getAllOrder();
            //   getFilterList();
            filterDialog = new Dialog(mContext);
            binding.orderList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            binding.searchTxt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    filterPlaces(charSequence.toString());
                    if (binding.searchTxt.getText().toString().isEmpty()) {
                        filterSelector(filterSelector);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            binding.searchCloseBtn.setOnClickListener(view -> {
                binding.searchTxt.setText("");
            });
            binding.createOrder.setOnClickListener(v -> {
                startActivity(new Intent(mContext, CheckOut.class));
            });
            binding.selectFulfillment.setOnClickListener(view -> {
                filterDialog(binding.selectFulfillment);
            });
            binding.filterBtn.setOnClickListener(v -> {
                filterDialog();
            });
        }
        return binding.getRoot();
    }

    private void filterPlaces(String title) {
        ArrayList<OrderItem> filteredPlaces = new ArrayList<>();
        for (OrderItem bookModel : orderList) {
            if (bookModel.getOrderNo().toLowerCase().trim().contains(title.toLowerCase().trim())) {
                filteredPlaces.add(bookModel);
            }
        }
        orderAdapter.filteredList(filteredPlaces);
    }

    public void getAllOrder() {
        AppProgressBar.showLoaderDialog(mContext);
        MainService.getAllOrder(mContext).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorSnackBar(binding.getRoot(), getString(R.string.something_wrong));
            } else {
                if (!(response.getData() instanceof JsonNull)) {
                    if (response.getData() != null) {
                        OrderResponse orderResponse = new Gson().fromJson(response.getData(), OrderResponse.class);
                        orderList.clear();
                        orderList.addAll(orderResponse.getOrders().getData());
                        getFilterList(orderResponse);
                        orderAdapter = new OrderAdapter(getContext(), orderList, 0);
                        binding.orderList.setAdapter(orderAdapter);
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

    public void getFilterOrder(int id, String status, String start, String end) {
        AppProgressBar.showLoaderDialog(mContext);
        MainService.getFilterOrder(mContext, id, status, start, end).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorSnackBar(binding.getRoot(), getString(R.string.something_wrong));
            } else {
                if (!(response.getData() instanceof JsonNull)) {
                    if (response.getData() != null) {
                        OrderResponse orderResponse = new Gson().fromJson(response.getData(), OrderResponse.class);
                        orderList.clear();
                        orderList.addAll(orderResponse.getOrders().getData());
                        orderAdapter = new OrderAdapter(getContext(), orderList, 0);
                        binding.orderList.setAdapter(orderAdapter);
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

    public void filterSelector(int position) {
        filterSelector = position;
        if (position == 0) {
            getAllOrder();
        } else if (position == 1) {
            orderAdapter.notifySelection(orderList, "pending");
        } else if (position == 2) {
            orderAdapter.notifySelection(orderList, "processing");
        } else if (position == 3) {
            orderAdapter.notifySelection(orderList, "pickup");
        } else if (position == 4) {
            orderAdapter.notifySelection(orderList, "completed");
        } else if (position == 5) {
            orderAdapter.notifySelection(orderList, "canceled");
        } else if (position == 6) {
            orderAdapter.notifySelection(orderList, "archived");
        }
    }

    public void getFilterList(OrderResponse orderResponse) {
        myListData = new filterItemsDM[]{
                new filterItemsDM("All", orderResponse.getOrders().getData().size()),
                new filterItemsDM("Awaiting Processing", orderResponse.getPendings()),
                new filterItemsDM("Processing", orderResponse.getProcessing()),
                new filterItemsDM("Ready For Pickup", orderResponse.getPickup()),
                new filterItemsDM("Completed", orderResponse.getCompleted()),
                new filterItemsDM("Cancelled", orderResponse.getCanceled()),
                new filterItemsDM("Archived", orderResponse.getArchived()),
        };
        binding.filterRv.setVisibility(View.VISIBLE);
        binding.filterRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        filterNameAdapter = new OrderFilterSelector(getContext(), myListData, 0, this);
        binding.filterRv.setAdapter(filterNameAdapter);
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

    public void filterDialog() {
        final int[] payment = {0};
        FilterDailogBinding sDialog = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.filter_dailog, null, false);
        filterDialog.setContentView(sDialog.getRoot());
        filterDialog.setCancelable(true);
        filterDialog.setCanceledOnTouchOutside(true);
        sDialog.startingDateTxt.setOnClickListener(v -> {
            datePicker(sDialog.startingDateTxt);
        });
        sDialog.endingDateTxt.setOnClickListener(v -> {
            datePicker(sDialog.endingDateTxt);
        });
        sDialog.fulfilmentstatusTxt.setOnClickListener(v -> {
            filterDialog(sDialog.fulfilmentstatusTxt);
        });
        sDialog.fulfilmentstatusTxt.setOnClickListener(v -> {
            filterDialog(sDialog.fulfilmentstatusTxt);
        });
        sDialog.paymentStatusTxt.setOnClickListener(v -> {
            paymentDialog(sDialog.paymentStatusTxt);
        });
        sDialog.cancelBtn.setOnClickListener(v -> {
            sDialog.startingDateTxt.setText("");
            sDialog.endingDateTxt.setText("");
            sDialog.fulfilmentstatusTxt.setText("");
            sDialog.paymentStatusTxt.setText("");
        });
        sDialog.okBtn.setOnClickListener(v -> {
            filterDialog.dismiss();
            if (sDialog.paymentStatusTxt.getText().toString().equals("Pending")) {
                payment[0] = 2;
            } else if (sDialog.paymentStatusTxt.getText().toString().equals("Complete")) {
                payment[0] = 1;
            } else if (sDialog.paymentStatusTxt.getText().toString().equals("Cancel")) {

            }
            getFilterOrder(payment[0], sDialog.fulfilmentstatusTxt.toString(), sDialog.startingDateTxt.toString(), sDialog.endingDateTxt.toString());
        });
        filterDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        filterDialog.getWindow().setGravity(Gravity.CENTER);
        filterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        filterDialog.show();
    }

    public void datePicker(TextView textView) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; // your format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                textView.setText(sdf.format(myCalendar.getTime()));
            }
        };
        new DatePickerDialog(mContext, R.style.datepicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

}