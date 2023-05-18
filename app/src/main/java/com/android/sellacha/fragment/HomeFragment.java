package com.android.sellacha.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;

import androidx.databinding.DataBindingUtil;

import com.android.sellacha.R;
import com.android.sellacha.SpinnerItem;
import com.android.sellacha.databinding.FragmentHomeBinding;
import com.android.sellacha.helper.view.LineView;
import com.faskn.lib.Arc;
import com.faskn.lib.PieChart;
import com.faskn.lib.Slice;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {
    FragmentHomeBinding binding;
    float[] floatArrayOf = new float[10];
    ArrayList<String> list = new ArrayList<>();
    ArrayList<Slice> slices = new ArrayList<>();
    int randomint = 9;
    private ArrayList<SpinnerItem> mCountryList;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        monthList();
        SpinnerAdapter mAdapter = new com.android.sellacha.activity.SpinnerAdapter(mContext, mCountryList);
        binding.monthSelect.setAdapter(mAdapter);
        binding.monthSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem clickedItem = (SpinnerItem) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getCountryName();
           //     Toast.makeText(mContext, clickedCountryName + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            slices.add(new Slice(0f, R.color._48C260, "fdsfds", new Arc(90, 280), 20f, 20));
            slices.add(new Slice(40f, R.color.primary_bg, "dasfsaf", new Arc(260, 360), 20f, 20));
            slices.add(new Slice(40f, R.color._FDC103, "fdsfds", new Arc(0, 90), 20f, 20));
            PieChart pieChart = new PieChart(slices, null, 0f, 35f);
            binding.chart.setPieChart(pieChart);
            initLineView(binding.lineView);
            randomSet(binding.lineView, binding.lineView2);
        }, 500);


        //binding.chart.sho(binding.action0);
//
//
//        floatArrayOf[0] = 113000;
//        floatArrayOf[1] = 113000;
//        floatArrayOf[2] = 113000;
//        floatArrayOf[3] = 113000;
////
////
//        list.add("05/21");
//        list.add("05/21");
//        list.add("05/21");
//        graphList.add(0);
//        graphList.add(40);
//        graphList.add(20);
//        graphList.add(4);
//
//        binding.lineView.setDrawDotLine(false); //optional
//        binding.lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
//        binding.lineView.setBottomTextList(list);
//        binding.lineView.setColorArray(new int[]{Color.BLACK, Color.GREEN, Color.GRAY, Color.CYAN});
//        binding.lineView.setDataList(graphList); //or lineView.setFloatDataList(floatDataLists)
////
////
////        ChartEntity firstChartEntity = new ChartEntity(Color.WHITE, floatArrayOf);
////
////        graphList.add(firstChartEntity);
////        binding.lineChart.setLegend(list);
////        binding.lineChart.setList(graphList);


        return binding.getRoot();
    }


    private void initLineView(LineView lineView) {
        ArrayList<String> test = new ArrayList<String>();
        test.add("Jan");
        test.add("Feb");
        test.add("Mar");
        test.add("Apr");
        test.add("May");
        test.add("Jun");
        test.add("Jul");
        test.add("Aug");
        test.add("Sep");
//        for (int i = 0; i < randomint; i++) {
//            test.add(String.valueOf(i + 1));
//        }
        lineView.setBottomTextList(test);
        lineView.setColorArray(new int[]{
                Color.parseColor("#0191B5")
        });
        // lineView.setDrawDotLine(false);
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);


        binding.lineView2.setBottomTextList(test);
        binding.lineView2.setColorArray(new int[]{
                Color.parseColor("#0191B5")
        });
        // lineView.setDrawDotLine(false);
        binding.lineView2.setShowPopup(LineView.SHOW_POPUPS_NONE);
    }

    private void randomSet(LineView lineView, LineView lineViewFloat) {
        ArrayList<Integer> dataList = new ArrayList<>();
        dataList.add(0);
        dataList.add(54);
        dataList.add(65);
        dataList.add(84);
        dataList.add(24);
        dataList.add(64);
        dataList.add(74);

//
//        ArrayList<Integer> dataList2 = new ArrayList<>();
//        random = (int) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++) {
//            dataList2.add((int) (Math.random() * random));
//        }
//
//        ArrayList<Integer> dataList3 = new ArrayList<>();
//        random = (int) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++) {
//            dataList3.add((int) (Math.random() * random));
//        }

        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        dataLists.add(dataList);
//        dataLists.add(dataList2);
//        dataLists.add(dataList3);

        lineView.setDataList(dataLists);

        lineView.setDataList(dataLists);
        ArrayList<Float> dataListF = new ArrayList<>();
        float randomF = (float) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataListF.add((float) (Math.random() * randomF));
        }

        ArrayList<Float> dataListF2 = new ArrayList<>();
        randomF = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataListF2.add((float) (Math.random() * randomF));
        }

        ArrayList<Float> dataListF3 = new ArrayList<>();
        randomF = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataListF3.add((float) (Math.random() * randomF));
        }

        ArrayList<ArrayList<Float>> dataListFs = new ArrayList<>();
        dataListFs.add(dataListF);
//        dataListFs.add(dataListF2);
//        dataListFs.add(dataListF3);
//        dataListFs.add(dataListF2);
//        dataListFs.add(dataListF3);

        lineViewFloat.setFloatDataList(dataListFs);
    }

    public void monthList() {
        mCountryList = new ArrayList<>();
        mCountryList.add(new SpinnerItem("January", 0));
        mCountryList.add(new SpinnerItem("February", 0));
        mCountryList.add(new SpinnerItem("March", 0));
        mCountryList.add(new SpinnerItem("April", 0));
        mCountryList.add(new SpinnerItem("May", 0));
        mCountryList.add(new SpinnerItem("June", 0));
        mCountryList.add(new SpinnerItem("July", 0));
        mCountryList.add(new SpinnerItem("August", 0));
        mCountryList.add(new SpinnerItem("September", 0));
        mCountryList.add(new SpinnerItem("October", 0));
        mCountryList.add(new SpinnerItem("November", 0));
        mCountryList.add(new SpinnerItem("December", 0));
    }

}