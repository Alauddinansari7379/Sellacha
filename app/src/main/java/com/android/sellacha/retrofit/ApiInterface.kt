package com.example.ehcf.retrofit

import com.android.sellacha.LogIn.Model.ModelLogin
import com.android.sellacha.Products.categories.Model.ModelCategory
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {


    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<ModelLogin>

    @Headers("content-type: application/json")
    @GET("get_category")
    fun getCategory(
        @Header("Authorization") authHeader: String?,
        @Query("type") type: String?
    ): Call<ModelCategory>

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