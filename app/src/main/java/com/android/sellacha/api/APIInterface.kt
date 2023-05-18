package com.android.sellacha.api

import com.android.sellacha.LogIn.Model.ModelLogin
import com.android.sellacha.api.request.LoginRequest
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @POST("login")
    @FormUrlEncoded
    fun userLogin(
        @Query("email") email: String?,
        @Query("password") password: String?
    ): Call<ModelLogin?>?

    @POST("login")
    fun userLogin(
        @Body model: LoginRequest?

   ): Call<ApiResponse?>?

    //All Order APi's
    @Headers("content-type: application/json")
    @GET("orders/all")
    fun getAllOrder(
        @Header("Authorization") authHeader: String?
    ): Call<ApiResponse?>?

    @Headers("content-type: application/json")
    @POST("show")
    fun getOrderByID(
        @Header("Authorization") authHeader: String?,
        @Body model: RequestModel?
    ): Call<ApiResponse?>?

    @Headers("content-type: application/json")
    @GET("orders/all")
    fun getFilterOrder(
        @Header("Authorization") authHeader: String?,
        @Query("payment_status") payment_status: Int,
        @Query("status") status: String?,
        @Query("start") start: String?,
        @Query("end") end: String?
    ): Call<ApiResponse?>?

    @Headers("content-type: application/json")
    @GET("logout")
    fun logOut(
        @Header("Authorization") authHeader: String?
    ): Call<ApiResponse?>?

    @Headers("content-type: application/json")
    @GET("product?type=products")
    fun getAllProduct(
        @Header("Authorization") authHeader: String?
    ): Call<ApiResponse?>?

    @Headers("content-type: application/json")
    @GET("products/1?type=products")
    fun getPublishProduct(
        @Header("Authorization") authHeader: String?
    ): Call<ApiResponse?>?

    @Headers("content-type: application/json")
    @GET("products/3?type=products")
    fun getIncompleteProduct(
        @Header("Authorization") authHeader: String?
    ): Call<ApiResponse?>?

    @Headers("content-type: application/json")
    @GET("products/0?type=products")
    fun getTrashProduct(
        @Header("Authorization") authHeader: String?
    ): Call<ApiResponse?>?

    @Headers("content-type: application/json")
    @GET("products/2?type=products")
    fun getDraftProduct(
        @Header("Authorization") authHeader: String?
    ): Call<ApiResponse?>?
}