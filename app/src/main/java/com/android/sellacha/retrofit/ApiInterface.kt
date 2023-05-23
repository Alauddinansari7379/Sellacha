package com.example.ehcf.retrofit

import com.android.sellacha.LogIn.Model.ModelLogin
import com.android.sellacha.Products.Attributes.activity.MOdel.ModelAttributes
import com.android.sellacha.Products.Coupons.MOdel.ModelCoupons
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.Report.model.ModelReort
import com.android.sellacha.Shipping.Location.ModelCreateLocation
import com.android.sellacha.Shipping.ShippingPrice.model.ModelCreateShipping
import com.android.sellacha.Transaction.Model.ModelTransaction
import com.example.ehcf.Fragment.test.UploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {


    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<ModelLogin>

    @Multipart
    @Headers("Accept: application/json")
    @POST("create_category")
    fun createCategory(
        @Header("Authorization") authHeader: String?,
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("featured") featured: String,
        @Query("menu_status") menu_status: String,
        @Part file: MultipartBody.Part,
    ): Call<UploadResponse>

    @Headers("content-type: application/json")
    @GET("get_category")
    fun getCategory(
        @Header("Authorization") authHeader: String?,
        @Query("type") type: String?
    ): Call<ModelCategory>
    @Headers("content-type: application/json")
    @GET("get_customer")
    fun getCustomer(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelCategory>

    @Headers("content-type: application/json")
    @GET("get_coupon")
    fun getCoupon(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelCoupons>

    @Headers("content-type: application/json")
    @POST("create_location")
    fun createLocation(
        @Header("Authorization") authHeader: String?,
        @Query("title") title: String?
    ): Call<ModelCreateLocation>
    @Headers("content-type: application/json")
    @POST("create_attribute")
    fun createAttribute(
        @Header("Authorization") authHeader: String?,
        @Query("title") title: String?,
        @Query("featured") featured: String?
    ): Call<ModelAttributes>
    @Headers("content-type: application/json")
    @POST("create_shipping")
    fun createShipping(
        @Header("Authorization") authHeader: String?,
        @Query("title") title: String?,
        @Query("price") price: String?,
        @Query("locations") locations: ArrayList<String>
    ): Call<ModelCreateShipping>

    @Headers("content-type: application/json")
    @GET("get_location")
    fun getLocation(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelCategory>

    @Headers("content-type: application/json")
    @GET("get_attribute")
    fun getAttribute(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelAttributes>
    @Headers("content-type: application/json")
    @GET("get_brand")
    fun getBrand(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelCategory>

    @Headers("content-type: application/json")
    @GET("get_review")
    fun getReview(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelCategory>
    @Headers("content-type: application/json")
    @GET("get_shipping")
    fun getShipping(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelCategory>

    @Headers("content-type: application/json")
    @GET("get_transaction")
    fun getTransaction(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelTransaction>
    @Headers("content-type: application/json")
    @GET("get_report")
    fun getReport(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelReort>
    @Headers("content-type: application/json")
    @GET("get_transaction")
    fun getTransactionByTransId(
        @Header("Authorization") authHeader: String?,
        @Query("src") src: String?,
    ): Call<ModelTransaction>

//    @FormUrlEncoded
//    @POST("register")
//    fun register(
//        @Field("customer_name") customer_name: String,
//        @Field("phone_number") phone_number: String,
//        @Field("phone_with_code") phone_with_code: String,
//        @Field("email") email: String,
//        @Field("password") password: String,
//        @Field("blood_group") blood_group: String,
//        @Field("gender") gender: Int,
//        @Field("fcm_token") fcm_token: String,
//    ): Call<RegistationResponse>

//    @FormUrlEncoded
//    @POST("forget_password")
//    fun forgotPassword(
//        @Field("phone_with_code") phone_with_code: String,
//    ): Call<ModelForgotPass>
//
//    @FormUrlEncoded
//    @POST("add_address")
//    fun addAddress(
//        @Field("customer id") customer_id: Int,
//        @Field("address") address: String,
//        @Field("landmark") landmark: String,
//        @Field("lat") lat: String,
//        @Field("lng") lng: String,
//    ): Call<AddAddressResponse>
//
//
//    @POST("all_addresses")
//    fun allAddress(
//        @Query("customer id") customer_id: Int
//    ): Call<AddressListResponse>
//
//    @FormUrlEncoded
//    @POST("reset_password")
//    fun resetPassword(
//        @Field("id") id: String,
//        @Field("password") password: String,
//    ): Call<ResetPassResponse>
//
//    @FormUrlEncoded
//    @POST("get_nearest_doctors")
//    fun searchByLocation(
//        @Field("lat") lat: String,
//        @Field("lng") lng: String,
//        @Field("search") search: String,
//    ): Call<SearchbyLocationRes>




}