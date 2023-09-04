package com.example.ehcf.retrofit

import com.android.sellacha.Costomer.model.ModelCreateCus
import com.android.sellacha.Home.model.ModelOrderCount
import com.android.sellacha.LogIn.Model.ModelLogin
import com.android.sellacha.OfferAndAds.model.ModelBumpAd
import com.android.sellacha.OfferAndAds.model.ModelCreateAd
import com.android.sellacha.Order.Model.ModelCreateShow
import com.android.sellacha.Products.Attributes.activity.MOdel.ModelAttributes
import com.android.sellacha.Products.Coupons.MOdel.ModelCoupons
import com.android.sellacha.Products.Inventory.Model.Modelinventory
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.Products.categories.Model.ModelCreCatogoryJava
import com.android.sellacha.Products.createProduct.Model.ModelCreatePro
import com.android.sellacha.Profile.model.ModelChangePass
import com.android.sellacha.Profile.model.ModelUserDetial
import com.android.sellacha.Registration.Model.ModelRegJava
import com.android.sellacha.Registration.Model.ModelRegistration
import com.android.sellacha.Report.model.ModelReort
import com.android.sellacha.Shipping.Location.ModelCreateLocation
import com.android.sellacha.Shipping.ShippingPrice.model.ModelCreateShipping
import com.android.sellacha.Transaction.Model.ModelTransaction
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
    ): Call<ModelCreCatogoryJava>

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
    @POST("get_scustomer")
    fun searchCustomer(
        @Header("Authorization") authHeader: String?,
        @Query("type") type: String,
        @Query("src") src: String,
    ): Call<ModelCategory>

    @Headers("content-type: application/json")
    @GET("me")
    fun me(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelUserDetial>

    @Headers("content-type: application/json")
    @GET("get_coupon")
    fun getCoupon(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelCoupons>

    @Headers("content-type: application/json")
    @GET("ads")
    fun bumpAds(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelBumpAd>

    @Headers("content-type: application/json")
    @GET("banner_ads")
    fun bannerAds(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelBumpAd>

    @Headers("content-type: application/json")
    @GET("order_statics")
    fun orderStatics(
        @Header("Authorization") authHeader: String?,
        @Query("month") month: String?,
    ): Call<ModelOrderCount>

    @Headers("content-type: application/json")
    @POST("create_location")
    fun createLocation(
        @Header("Authorization") authHeader: String?,
        @Query("title") title: String?
    ): Call<ModelCreateLocation>

    @Headers("content-type: application/json")
    @POST("customer")
    fun createCustomer(
        @Header("Authorization") authHeader: String?,
        @Query("name") name: String?,
        @Query("email") email: String?,
        @Query("password") password: String?
    ): Call<ModelCreateCus>

    @Headers("content-type: application/json")
    @POST("user_profile_update")
    fun userProfileUpdate(
        @Header("Authorization") authHeader: String?,
        @Query("password_current") password_current: String?,
        @Query("password") password: String?,
    ): Call<ModelChangePass>

    @Headers("content-type: application/json")
    @POST("create_attribute")
    fun createAttribute(
        @Header("Authorization") authHeader: String?,
        @Query("title") title: String?,
        @Query("featured") featured: String?
    ): Call<ModelAttributes>


    @Headers("content-type: application/json")
    @Multipart
    @POST("seller_register/1")
    fun registration(
        @Query("name") name: String?,
        @Query("email") email: String?,
        @Query("password") password: String?,
        @Query("domain") domain: String?,
        @Query("custom_domain") custom_domain: String?,
        @Query("utype") utype: String?,
        @Query("sertype") sertype: String?,
        @Query("refrral") refrral: String?,
        @Query("mob") mob: String?,
        @Query("business_name") business_name: String?,
        @Query("shop_type") shop_type: String?,
        @Part logo: MultipartBody.Part,
        @Part favicon: MultipartBody.Part,
        @Query("theme_color") theme_color: String?,
        @Query("url") url: ArrayList<String>?,
//        @Query("icon[]") icon: String,
        //@Part icon: Array<Part?>?,
        @Query("cname") cname: String?,
        @Query("p_id") p_id: String?,
        @Part file: MultipartBody.Part,
        @Query("featured") featured: String?,
        @Query("menu_status") menu_status: String?,
        @Query("title") title: String?,
        @Query("special_price_start") special_price_start: String?,
        @Query("special_price") special_price: String?,
        @Query("price_type") price_type: String?,
        @Query("price") price: String?,
        @Query("type") type: String?,
        @Query("special_price_end") special_price_end: String?,
        @Query("ga_measurement_id") ga_measurement_id: String?,
        @Query("analytics_view_id") analytics_view_id: String?,
        @Query("astatus") astatus: String?,
        @Query("gfile") gfile: String?,
        @Query("tag_id") tag_id: String?,
        @Query("tstatus") tstatus: String?,
        @Query("pixel_id") pixel_id: String?,
        @Query("pstatus") pstatus: String?,
        @Query("number") number: String?,
        @Query("shop_page_pretext") shop_page_pretext: String?,
        @Query("other_page_pretext") other_page_pretext: String?,
        @Query("wstatus") wstatus: String?,
    ): Call<ModelRegJava>

//    @FormUrlEncoded
//    @POST("service_name")
//    fun functionName(
//        @Field("learning_objective_uuids[]") learning_objective_uuids: ArrayList<String?>?,
//        @Field("user_uuids[]") user_uuids: ArrayList<String?>?,
//        @Field("note") note: String?,
//        callback: Callback<CallBackClass?>?
//    )
//    @Multipart
//    @POST(UPLOAD_SURVEY)
//    fun uploadSurvey(
//        @Part surveyImage: Array<Part?>?,
//        @Part propertyImage: Part?,
//        @Part("DRA") dra: RequestBody?
//    ): Call<UploadSurveyResponseModel?>?

    @Headers("content-type: application/json")
    @POST("inventory")
    fun inventory(
        @Header("Authorization") authHeader: String?,
    ): Call<Modelinventory>

    @Headers("content-type: application/json")
    @GET("create_show")
    fun createShow(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelCreateShow>

    @Headers("content-type: application/json")
    @GET("product")
    fun productSearch(
        @Header("Authorization") authHeader: String?,
        @Query("src") src: String?,
        @Query("type") type: String?
    ): Call<ModelCreateShow>

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

    @Multipart
    @POST("ads_store")
    fun createBumpAdd(
        @Header("Authorization") authHeader: String?,
        @Query("url") url: String?,
        @Part file: MultipartBody.Part,
    ): Call<ModelCreateAd>

    @Multipart
    @POST("banner_store")
    fun createBannerAdd(
        @Header("Authorization") authHeader: String?,
        @Query("url") url: String?,
        @Part file: MultipartBody.Part,
    ): Call<ModelCreateAd>

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
    @GET("get_report")
    fun getFilterReport(
        @Header("Authorization") authHeader: String?,
        @Query("start") start: String,
        @Query("end") end: String,
    ): Call<ModelReort>

    @Headers("content-type: application/json")
    @GET("get_transaction")
    fun getTransactionByTransId(
        @Header("Authorization") authHeader: String?,
        @Query("src") src: String?,
    ): Call<ModelTransaction>

    @Headers("content-type: application/json")
    @POST("store")
    fun createProduct(
        @Header("Authorization") authHeader: String?,
        @Query("title") title: String?,
        @Query("price") price: String?,
        @Query("special_price") special_price: String?,
        @Query("price_type") price_type: String?,
        @Query("special_price_start") special_price_start: String?,
        @Query("special_price_end") special_price_end: String?,
        @Query("type") type: String,
    ): Call<ModelCreatePro>


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