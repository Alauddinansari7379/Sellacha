package com.android.sellacha.Products;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.sellacha.R;
import com.android.sellacha.adapter.ProductAdapter;
import com.android.sellacha.adapter.ProductFilterSelector;
import com.android.sellacha.api.model.filterItemsDM;
import com.android.sellacha.api.response.Product.DataItem;
import com.android.sellacha.api.response.Product.GetAllProduct;
import com.android.sellacha.api.service.MainService;
import com.android.sellacha.databinding.FragmentProductBinding;
import com.android.sellacha.fragment.BaseFragment;
import com.android.sellacha.utils.AppProgressBar;
import com.google.gson.Gson;
import com.google.gson.JsonNull;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends BaseFragment {

    FragmentProductBinding binding;
    ProductFilterSelector filterNameAdapter;
    ProductAdapter productAdapter;
    List<DataItem> productList = new ArrayList<>();
    int filterSelector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false);
        binding.orderList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        productAdapter = new ProductAdapter(getContext(), productList, 0, binding.orderList);
        productAdapter.setOnLoadMoreListener(new ProductAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (productList.size() <= 40) {
                    productList.add(null);
                    productAdapter.notifyItemInserted(productList.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            productList.remove(productList.size() - 1);
                            productAdapter.notifyItemRemoved(productList.size());
                            //Generating more data
                            int index = productList.size();
                            int end = index + 20;
                            for (int i = index; i < end; i++) {
                                productList.add(null);
                            }
                            productAdapter.notifyDataSetChanged();
                            productAdapter.setLoaded();
                        }
                    }, 5000);
                } else {
                    Toast.makeText(mContext, "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getPublish();

        binding.addNewBtn.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.createProductFragmnet);
        });

        binding.selectFulfillment.setOnClickListener(view -> {
            selectFulfilment(binding.selectFulfillment);
        });
        binding.searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterPlaces(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });



//        productAdapter = new ProductAdapter(getContext(), orderData, 0);
//        binding.orderList.setAdapter(productAdapter);


        return binding.getRoot();
    }

    private void filterPlaces(String title) {
        ArrayList<DataItem> filteredPlaces = new ArrayList<>();
        for (DataItem bookModel : productList) {
            if (bookModel.getTitle().toLowerCase().trim().contains(title.toLowerCase().trim())) {
                filteredPlaces.add(bookModel);
            }
        }
        productAdapter.filteredList(filteredPlaces);
    }

    public void getPublish() {
        AppProgressBar.showLoaderDialog(mContext);
        MainService.getPublishProduct(mContext).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorSnackBar(binding.getRoot(), getString(R.string.something_wrong));
            } else {
                if (!(response.getData() instanceof JsonNull)) {
                    if (response.getData() != null) {
                        GetAllProduct orderResponse = new Gson().fromJson(response.getData(), GetAllProduct.class);
                        productList.clear();
                        productList.addAll(orderResponse.getPosts().getData());
                        getFilterList(orderResponse);
                        productAdapter = new ProductAdapter(getContext(), productList, 0, binding.orderList);
                        binding.orderList.setAdapter(productAdapter);
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

    public void getDraft() {
        AppProgressBar.showLoaderDialog(mContext);
        MainService.getDraftProduct(mContext).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorSnackBar(binding.getRoot(), getString(R.string.something_wrong));
            } else {
                if (!(response.getData() instanceof JsonNull)) {
                    if (response.getData() != null) {
                        GetAllProduct orderResponse = new Gson().fromJson(response.getData(), GetAllProduct.class);
                        productList.clear();
                        productList.addAll(orderResponse.getPosts().getData());
                        productAdapter = new ProductAdapter(getContext(), productList, 0, binding.orderList);
                        binding.orderList.setAdapter(productAdapter);
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

    public void getIncomplete() {
        AppProgressBar.showLoaderDialog(mContext);
        MainService.getIncompleteProduct(mContext).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorSnackBar(binding.getRoot(), getString(R.string.something_wrong));
            } else {
                if (!(response.getData() instanceof JsonNull)) {
                    if (response.getData() != null) {
                        GetAllProduct orderResponse = new Gson().fromJson(response.getData(), GetAllProduct.class);
                        productList.clear();
                        productList.addAll(orderResponse.getPosts().getData());
                        productAdapter = new ProductAdapter(getContext(), productList, 0, binding.orderList);
                        binding.orderList.setAdapter(productAdapter);
                    } else {
                        AppProgressBar.hideLoaderDialog();
                        showAlertDialog(getString(R.string.app_name), response.getMessage(), "OK", "", DialogFragment::dismiss);
                    }
                } else {
                    AppProgressBar.hideLoaderDialog();
                    errorSnackBar(binding.getRoot(), response.getMessage());
                }
            }

        });
    }

    public void getTrash() {
        AppProgressBar.showLoaderDialog(mContext);
        MainService.getTrash(mContext).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorSnackBar(binding.getRoot(), getString(R.string.something_wrong));
            } else {
                if (!(response.getData() instanceof JsonNull)) {
                    if (response.getData() != null) {
                        GetAllProduct orderResponse = new Gson().fromJson(response.getData(), GetAllProduct.class);
                        productList.clear();
                        productList.addAll(orderResponse.getPosts().getData());
                        productAdapter = new ProductAdapter(getContext(), productList, 0, binding.orderList);
                        binding.orderList.setAdapter(productAdapter);
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

    public void getFilterList(GetAllProduct orderResponse) {
        filterItemsDM[] myListData = new filterItemsDM[]{
                new filterItemsDM("Publish", orderResponse.getActives()),
                new filterItemsDM("Draft", orderResponse.getDrafts()),
                new filterItemsDM("Incomplete", orderResponse.getIncomplete()),
                new filterItemsDM("Trash", orderResponse.getTrash())
        };
        binding.filterRv.setVisibility(View.VISIBLE);
        binding.filterRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        filterNameAdapter = new ProductFilterSelector(getContext(), myListData, 0, this);
        binding.filterRv.setAdapter(filterNameAdapter);
    }

    public void filterSelector(int position) {
        filterSelector = position;
        if (position == 0) {
            getPublish();
            //   productAdapter.notifySelection(productList, "1");
        } else if (position == 1) {
            getDraft();
            //    productAdapter.notifySelection(productList, "2");
        } else if (position == 2) {
            getIncomplete();
            //   productAdapter.notifySelection(productList, "3");
        } else if (position == 3) {
            getTrash();
            //    productAdapter.notifySelection(productList, "0");
        }
    }

    public void selectFulfilment(TextView textView) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setTitle("Select fulfillment");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.dialog_txt_item);
        arrayAdapter.add("Publish Now");
        arrayAdapter.add("Draft");
        arrayAdapter.add("Move to Trash");
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


//    public void getAllProduct() {
//        AppProgressBar.showLoaderDialog(mContext);
//        MainService.getAllProduct(mContext).observe(getViewLifecycleOwner(), response -> {
//            if (response == null) {
//                errorSnackBar(binding.getRoot(), getString(R.string.something_wrong));
//            } else {
//                if (!(response.getData() instanceof JsonNull)) {
//                    if (response.getData() != null) {
//                        GetAllProduct orderResponse = new Gson().fromJson(response.getData(), GetAllProduct.class);
//                        productList.clear();
//                        productList.addAll(orderResponse.getPosts().getData());
//                        getFilterList(orderResponse);
//                        List<DataItem> tempList = new ArrayList<>();
//                        for (int x = 0; x < productList.size(); x++) {
//                            if (productList.get(x).getStatus().equalsIgnoreCase("1")) {
//                                tempList.add(productList.get(x));
//                            }
//                        }
//                        productAdapter = new ProductAdapter(getContext(), tempList, 0);
//                        binding.orderList.setAdapter(productAdapter);
//                    } else {
//                        showAlertDialog(getString(R.string.app_name), response.getMessage(), "OK", "", DialogFragment::dismiss);
//                    }
//                } else {
//                    errorSnackBar(binding.getRoot(), response.getMessage());
//                }
//            }
//            AppProgressBar.hideLoaderDialog();
//        });
//    }
}