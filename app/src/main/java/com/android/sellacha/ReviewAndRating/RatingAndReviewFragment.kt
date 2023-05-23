package com.android.sellacha.ReviewAndRating

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.Products.categories.adapter.AdapterCategory
import com.android.sellacha.R
import com.android.sellacha.Report.model.ModelReort
import com.android.sellacha.ReviewAndRating.adapter.AdapterReviewAndRating
import com.android.sellacha.Transaction.adapter.AdapterReport
import com.android.sellacha.adapter.ReviewRatingAdapter
import com.android.sellacha.api.model.reviewRatingDM
import com.android.sellacha.databinding.FragmentRatingAndReviewBinding
import com.android.sellacha.databinding.FragmentReportBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingAndReviewFragment : Fragment() {
    var binding: FragmentRatingAndReviewBinding? = null
    var reviewRatingAdapter: ReviewRatingAdapter? = null
    private lateinit var sessionManager: SessionManager

    var reviewRatingArr = ArrayList<reviewRatingDM>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rating_and_review, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRatingAndReviewBinding.bind(view)
        sessionManager = SessionManager(requireContext())
        apiCallReviewAndRating()
    }
    private fun apiCallReviewAndRating() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getReview(sessionManager.authToken)
            .enqueue(object : Callback<ModelCategory> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCategory>, response: Response<ModelCategory>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.body()!!.data.posts.data.isEmpty()) {
                        binding!!.reviewRatingRc.adapter =
                            activity?.let { AdapterReviewAndRating(it, response.body()!!) }
                        binding!!.reviewRatingRc.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Rating Found")
                        AppProgressBar.hideLoaderDialog()
                    } else {
                        binding!!.reviewRatingRc.adapter =
                            activity?.let { AdapterReviewAndRating(it, response.body()!!) }
                        AppProgressBar.hideLoaderDialog()


                    }
                }

                override fun onFailure(call: Call<ModelCategory>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

}