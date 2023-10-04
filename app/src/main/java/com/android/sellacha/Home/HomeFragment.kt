package com.android.sellacha.Home

import android.annotation.SuppressLint
import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.android.sellacha.Home.model.ModelMonth
import com.android.sellacha.Home.model.ModelOrderCount
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentHomeBinding
import com.android.sellacha.fragment.BaseFragment
import com.android.sellacha.helper.currentMonth
import com.android.sellacha.helper.myToast
import com.android.sellacha.helper.view.LineView
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import com.faskn.lib.Arc
import com.faskn.lib.PieChart
import com.faskn.lib.Slice
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding
    var floatArrayOf = FloatArray(10)
    var list = ArrayList<String>()
    var slices = ArrayList<Slice>()
    private var randomint = 9
     var monthName = ""
    private lateinit var sessionManager: SessionManager
    private var mCountryList = ArrayList<ModelMonth>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        sessionManager = SessionManager(requireContext())





        when (currentMonth) {
            1 -> {
                monthName = "January"
            }
            2 -> {
                monthName = "February"
            }
            3 -> {
                monthName = "March"
            }
            4 -> {
                monthName = "April"
            }
            5 -> {
                monthName = "May"
            }
            6 -> {
                monthName = "June"
            }
            7 -> {
                monthName = "July"
            }
            8 -> {
                monthName = "August"
            }
            9 -> {
                monthName = "September"
            }
            10 -> {
                monthName = "October"
            }
            11 -> {
                monthName = "November"
            }
            12 -> {
                monthName = "December"
            }
            else -> ""
        }

        Log.e("CurrentMont", monthName)


        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            {
                slices.add(Slice(0f, R.color._48C260, "fdsfds", Arc(00f, 280f), 20f, 20))
                slices.add(Slice(40f, R.color.primary_bg, "dasfsaf", Arc(260f, 360f), 20f, 20))
                slices.add(Slice(40f, R.color._FDC103, "fdsfds", Arc(0f, 90f), 20f, 20))
                val pieChart = PieChart(slices, null, 0f, 35f)
                binding!!.chart.setPieChart(pieChart)

                initLineView(binding.lineView)
                randomSet(binding.lineView, binding.lineView2)


                mCountryList!!.add(ModelMonth("January", 0))
                mCountryList!!.add(ModelMonth("February", 0))
                mCountryList!!.add(ModelMonth("March", 0))
                mCountryList!!.add(ModelMonth("April", 0))
                mCountryList!!.add(ModelMonth("May", 0))
                mCountryList!!.add(ModelMonth("June", 0))
                mCountryList!!.add(ModelMonth("July", 0))
                mCountryList!!.add(ModelMonth("August", 0))
                mCountryList!!.add(ModelMonth("September", 0))
                mCountryList.add(ModelMonth("October", 0))
                mCountryList!!.add(ModelMonth("November", 0))
                mCountryList!!.add(ModelMonth("December", 0))

                binding.monthSelect.adapter =
                    ArrayAdapter<ModelMonth>(requireContext(), android.R.layout.simple_list_item_1, mCountryList!!)
                binding.monthSelect.setSelection(currentMonth.toString().toInt()-1);

            }, 500
        )


        spinnerMonth()

        apiCallGetorderCount(monthName)


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



    }

    private fun spinnerMonth() {

        binding.monthSelect.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    if (mCountryList!!.size > 0) {
                        monthName = mCountryList!![i].month
                        apiCallGetorderCount(monthName)
                        Log.e(ContentValues.TAG, "monthName: $monthName")
                    }

                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }

    }

    private fun apiCallGetorderCount(monthName: String) {

        // AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.orderStatics(sessionManager.authToken, monthName)
            .enqueue(object : Callback<ModelOrderCount> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelOrderCount>, response: Response<ModelOrderCount>
                ) {
                    try {
                        if (response.code() == 404) {
                            myToast(requireActivity(), "Server Error")
                            //   AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            //   AppProgressBar.hideLoaderDialog()

                        } else {
                            binding.textView.text = response.body()!!.data.total_pending
                            binding.greenTxt.text = response.body()!!.data.total_completed
                            binding.yellowTxt.text = response.body()!!.data.total_processing
                            binding.circleCount.text = response.body()!!.data.total_orders
                            binding.sellCount.text = response.body()!!.data.total_earnings
                            binding.sellCount2.text = response.body()!!.data.total_orders
                             Log.e("total_pending", response.body()!!.data.total_orders)
                            Log.e("total_completed", response.body()!!.data.total_completed)
                            Log.e("total_processing", response.body()!!.data.total_processing)
                            Log.e("total_orders", response.body()!!.data.total_orders)
//                        Log.e("df",response.body()!!.data.total_processing)
                            //   AppProgressBar.hideLoaderDialog()

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }


                override fun onFailure(call: Call<ModelOrderCount>, t: Throwable) {
                    //  myToast(this@HomeFragment, t.message.toString())
                    AppProgressBar.hideLoaderDialog()

                }

            })
    }

    private fun initLineView(lineView: LineView) {
        val test = ArrayList<String>()
        test.add("Jan")
        test.add("Feb")
        test.add("Mar")
        test.add("Apr")
        test.add("May")
        test.add("Jun")
        test.add("Jul")
        test.add("Aug")
        test.add("Sep")
        test.add("Oct")
        test.add("Nov")
        test.add("Dec")
        //        for (int i = 0; i < randomint; i++) {
//            test.add(String.valueOf(i + 1));
//        }
        lineView.setBottomTextList(test)
        lineView.setColorArray(
            intArrayOf(
                Color.parseColor("#0191B5")
            )
        )
        // lineView.setDrawDotLine(false);
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE)
        binding!!.lineView2.setBottomTextList(test)
        binding!!.lineView2.setColorArray(
            intArrayOf(Color.parseColor("#0191B5"))
        )
        // lineView.setDrawDotLine(false);
        binding!!.lineView2.setShowPopup(LineView.SHOW_POPUPS_NONE)
    }

    private fun randomSet(lineView: LineView, lineViewFloat: LineView) {
        val dataList = ArrayList<Int>()
        dataList.add(0)
        dataList.add(0)
        dataList.add(0)
        dataList.add(0)
        dataList.add(24)
        dataList.add(0)
        dataList.add(0)
        dataList.add(0)
        dataList.add(24)
        dataList.add(64)
        dataList.add(104)
        dataList.add(46)

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
        val dataLists = ArrayList<ArrayList<Int>>()
        dataLists.add(dataList)
        //        dataLists.add(dataList2);
//        dataLists.add(dataList3);
        lineView.setDataList(dataLists)
        lineView.setDataList(dataLists)
        val dataListF = ArrayList<Float>()
        var randomF = (Math.random() * 9 + 1).toFloat()
        for (i in 0 until randomint) {
            dataListF.add((Math.random() * randomF).toFloat())
        }
        val dataListF2 = ArrayList<Float>()
        randomF = (Math.random() * 9 + 1).toInt().toFloat()
        for (i in 0 until randomint) {
            dataListF2.add((Math.random() * randomF).toFloat())
        }
        val dataListF3 = ArrayList<Float>()
        randomF = (Math.random() * 9 + 1).toInt().toFloat()
        for (i in 0 until randomint) {
            dataListF3.add((Math.random() * randomF).toFloat())
        }
        val dataListFs = ArrayList<ArrayList<Float>>()
        dataListFs.add(dataListF)
        //        dataListFs.add(dataListF2);
//        dataListFs.add(dataListF3);
//        dataListFs.add(dataListF2);
//        dataListFs.add(dataListF3);
        lineViewFloat.setFloatDataList(dataListFs)
    }

}