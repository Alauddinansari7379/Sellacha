package com.example.ehcf.retrofit

import com.android.sellacha.Costomer.model.ModelCreateCus
import com.android.sellacha.Home.model.ModelOrderCount
import com.android.sellacha.LogIn.Model.ModelLogin
import com.android.sellacha.Notification.Model.ModelNotification
import com.android.sellacha.OfferAndAds.model.ModelBumpAd
import com.android.sellacha.OfferAndAds.model.ModelCreateAd
import com.android.sellacha.Order.Model.*
import com.android.sellacha.Products.Attributes.activity.MOdel.ModelAttributes
import com.android.sellacha.Products.Coupons.MOdel.ModelCoupons
import com.android.sellacha.Products.Coupons.MOdel.ModelCreateCoupon
import com.android.sellacha.Products.Inventory.Model.ModelUpdateInv
import com.android.sellacha.Products.Inventory.Model.Modelinventory
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.Products.categories.Model.ModelCreCatogoryJava
import com.android.sellacha.Products.createProduct.Model.ModelCreatePro
import com.android.sellacha.Profile.model.ModelChangePass
import com.android.sellacha.Profile.model.ModelLogo
import com.android.sellacha.Profile.model.ModelUpdateProfile
import com.android.sellacha.Profile.model.ModelUserDetial
import com.android.sellacha.Registration.Model.ModelRegJava
import com.android.sellacha.Report.model.ModelReort
import com.android.sellacha.Shipping.Location.ModelCreateLocation
import com.android.sellacha.Shipping.Nimbus.Model.ModelLoginNimbus
import com.android.sellacha.Shipping.Nimbus.Model.ModelNimbusToken
import com.android.sellacha.Shipping.ShippingPrice.model.ModelCreateShipping
import com.android.sellacha.Transaction.Model.ModelTransaction
import com.android.sellacha.marketingTools.model.ModelFacebookPixel
import com.android.sellacha.marketingTools.model.ModelGoolgeAna
import com.android.sellacha.marketingTools.model.ModelTagManager
import com.android.sellacha.marketingTools.model.ModelWhatsaap
import com.android.sellacha.setting.Domain.model.ModelDomain
import com.android.sellacha.setting.Domain.model.ModelUpdateDomain
import com.android.sellacha.setting.Payment.Model.ModelPaymentList
import com.android.sellacha.setting.model.ModelSubscription
import com.android.sellacha.shopSetting.model.ModelGetSlider
import com.android.sellacha.shopSetting.model.ModelSlider
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {


    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device_token") device_token: String,
        @Field("device_type") device_type: String,
    ): Call<ModelLogin>

    @Headers("Accept: application/json")
    @POST("forgeot_pass")
    fun forgotPass(
        @Query("email") email: String,
    ): Call<ModelCoupon>


    @Multipart
    @Headers("Accept: application/json")
    @POST("create_brand")
    fun createBrand(
        @Header("Authorization") authHeader: String?,
        @Query("name") name: String,
        @Query("featured") featured: String,
        @Part file: MultipartBody.Part,
    ): Call<ModelCreCatogoryJava>

    @Headers("Accept: application/json")
    @POST("destroy_brand")
    fun deleteBrand(
        @Header("Authorization") authHeader: String?,
        @Query("id") id: String,
        @Query("type") featured: String,
    ): Call<ModelCreCatogoryJava>

    @Headers("Accept: application/json")
    @POST("edit_location")
    fun editLocation(
        @Header("Authorization") authHeader: String?,
        @Query("id") id: String,
        @Query("title") title: String,
    ): Call<ModelCreCatogoryJava>

    @Headers("Accept: application/json")
    @POST("destroy_location")
    fun deleteLocation(
        @Header("Authorization") authHeader: String?,
        @Query("ids") id: String,
        @Query("method") method: String,
    ): Call<ModelCreCatogoryJava>

    @Multipart
    @Headers("Accept: application/json")
    @POST("edit_brand")
    fun editBrand(
        @Header("Authorization") authHeader: String?,
        @Query("name") name: String,
        @Query("featured") featured: String,
        @Query("id") id: String,
        @Part file: MultipartBody.Part,
    ): Call<ModelCreCatogoryJava>

    @Multipart
    @Headers("Accept: application/json")
    @POST("edit_category")
    fun editCategory(
        @Header("Authorization") authHeader: String?,
        @Query("name") name: String,
        @Part file: MultipartBody.Part,
        @Query("id") id: String,
    ): Call<ModelCreCatogoryJava>

    @Headers("Accept: application/json")
    @POST("edit_category")
    fun editCategoryWithOutImg(
        @Header("Authorization") authHeader: String?,
        @Query("name") name: String,
         @Query("id") id: String,
    ): Call<ModelCreCatogoryJava>

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

    @Headers("Accept: application/json")
    @POST("create_category")
    fun createCategoryWithOutImg(
        @Header("Authorization") authHeader: String?,
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("featured") featured: String,
        @Query("menu_status") menu_status: String,
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
    @POST("destroy_customer")
    fun deleteCustomer(
        @Header("Authorization") authHeader: String?,
        @Query("ids") ids: String,
        @Query("type") type: String,
    ): Call<ModelCreCatogoryJava>

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
    @POST("create_coupon")
    fun createCoupon(
        @Header("Authorization") authHeader: String?,
        @Query("coupon_code") coupon_code: String?,
        @Query("percent") percent: String?,
        @Query("date") date: String?,
    ): Call<ModelCreateCoupon>

    @Headers("content-type: application/json")
    @POST("edit_coupon")
    fun editCoupon(
        @Header("Authorization") authHeader: String?,
        @Query("coupon_code") coupon_code: String?,
        @Query("percent") percent: String?,
        @Query("date") date: String?,
        @Query("id") id: String?,
    ): Call<ModelCreateCoupon>

    @Headers("content-type: application/json")
    @POST("edit_attribute")
    fun editAttribute(
        @Header("Authorization") authHeader: String?,
        @Query("title") title: String?,
        @Query("featured") featured: String?,
        @Query("id") id: String?,
    ): Call<ModelCreateCoupon>

    @Headers("content-type: application/json")
    @POST("destroy_coupon")
    fun deleteCoupon(
        @Header("Authorization") authHeader: String?,
        @Query("ids") ids: String?,
        @Query("method") method: String?,
    ): Call<ModelCreateCoupon>

    @Headers("content-type: application/json")
    @GET("ads")
    fun bumpAds(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelBumpAd>

    @Headers("content-type: application/json")
    @POST("add_remove")
    fun deleteBumpAds(
        @Header("Authorization") authHeader: String?,
        @Query("addid") addid: String?,
    ): Call<ModelBumpAd>

    @Headers("content-type: application/json")
    @POST("delete_slider")
    fun deleteSlider(
        @Header("Authorization") authHeader: String?,
        @Query("id") addid: String?,
    ): Call<ModelSlider>

    @Headers("content-type: application/json")
    @GET("banner_ads")
    fun bannerAds(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelBumpAd>

    @Headers("content-type: application/json")
    @POST("get_slider")
    fun getSlider(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelGetSlider>

    @Multipart
    @POST("add_slider")
    fun addSlider(
        @Header("Authorization") authHeader: String?,
        @Query("url") url: String?,
        @Query("title") title: String?,
        @Query("btn_text") btn_text: String?,
        @Part file: MultipartBody.Part
    ): Call<ModelSlider>

    @Headers("content-type: application/json")
    @POST("dashboard/order_statics")
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
    @POST("edit_customer")
    fun editCustomer(
        @Header("Authorization") authHeader: String?,
        @Query("ids") ids: String?,
        @Query("change_password") change_password: String?,
        @Query("name") name: String?,
        @Query("email") email: String?,
        @Query("password") password: String?
    ): Call<ModelCreateCus>

    @Headers("content-type: application/json")
    @POST("user_profile_update")
    fun userProfileUpdatePass(
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
    ): Call<ModelCoupon>

    @Headers("content-type: application/json")
    @POST("order_change")
    fun orderChange(
        @Header("Authorization") authHeader: String?,
        @Query("orderid") orderid: String?,
        @Query("status") status: String?
    ): Call<ModelCoupon>

    @Headers("content-type: application/json")
    @POST("payment_change")
    fun paymentChange(
        @Header("Authorization") authHeader: String?,
        @Query("orderid") orderid: String?,
        @Query("status") status: String?
    ): Call<ModelCoupon>


    @Headers("content-type: application/json")
   // @Multipart
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
//        @Part logo: MultipartBody.Part,
//        @Part favicon: MultipartBody.Part,
        @Query("theme_color") theme_color: String?,
        @Query("url") url: ArrayList<String>?,
        @Query("cname") cname: String?,
        @Query("p_id") p_id: String?,
      //  @Part file: MultipartBody.Part,
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
        @Query("plnt") plnt: String?,
        @Query("plan_id") plan_id: String?,
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
    @GET("get_cart")
    fun getCart(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelGetCart>

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
        @Query("locations") locations: String?,
        // @Query("locations") locations: ArrayList<String>
    ): Call<ModelCreateShipping>

    @Headers("content-type: application/json")
    @POST("edit_shipping")
    fun editShipping(
        @Header("Authorization") authHeader: String?,
        @Query("title") title: String?,
        @Query("price") price: String?,
        @Query("locations") locations: String?,
        //@Query("locations") locations: ArrayList<String>,
        @Query("id") id: String?
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
    @POST("destroy_attribute")
    fun deleteAttribute(
        @Header("Authorization") authHeader: String?,
        @Query("ids") ids: String?,
        @Query("method") method: String?,
    ): Call<ModelCreateCoupon>

    @Headers("content-type: application/json")
    @POST("destroy_shipping")
    fun deleteShipping(
        @Header("Authorization") authHeader: String?,
        @Query("ids") ids: String?,
        @Query("method") method: String?,
    ): Call<ModelCreateCoupon>

    @Headers("content-type: application/json")
    @POST("destroy_category")
    fun deleteAttribute(
        @Header("Authorization") authHeader: String?,
        @Query("ids") ids: String?,
        @Query("type") type: String?,
        @Query("typeu") typeu: String?,
    ): Call<ModelCreateCoupon>

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
    @GET("plan_details")
    fun planDetails(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelSubscription>

    @Headers("content-type: application/json")
    @GET("get_review")
    fun getReview(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelCategory>

    @Headers("content-type: application/json")
    @POST("destroy_cart")
    fun destroyCart(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelCoupon>

    @Headers("content-type: application/json")
    @GET("get_shipping")
    fun getShipping(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelCategory>

    @Headers("content-type: application/json")
    @POST("get_shipping_city")
    fun getShippingCity(
        @Header("Authorization") authHeader: String?,
        @Query("id") id: String
    ): Call<ModelShippingCiry>

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

    @Multipart
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
        @Query("stock_manage") stock_manage: String,
        @Query("stock_qty") stock_qty: String,
        @Query("sku") sku: String,
        @Query("stock_status") stock_status: String,
        @Part media: MultipartBody.Part,
    ): Call<ModelCreatePro>


    @Headers("content-type: application/json")
    @POST("add_to_cart")
    fun addToCart(
        @Header("Authorization") authHeader: String?,
        @Query("term_id") term_id: String?,
        @Query("qty") qty: String?,
    ): Call<ModelAddToCart>

    @Headers("content-type: application/json")
    @POST("apply_coupon")
    fun applyCoupon(
        @Header("Authorization") authHeader: String?,
        @Query("code") code: String?,
    ): Call<ModelCoupon>

    @Headers("content-type: application/json")
    @POST("cart_remove")
    fun cartRemove(
        @Header("Authorization") authHeader: String?,
        @Query("id") id: String?,
         @Query("device_id") device_id: String?,
    ): Call<ModelGetCart>

    @Headers("content-type: application/json")
    @POST("user_profile_update")
    fun userProfileUpdate(
        @Header("Authorization") authHeader: String?,
        @Query("name") name: String?,
        @Query("email") email: String?,
        @Query("mob") mob: String?,
    ): Call<ModelCoupon>

    @Headers("content-type: application/json")
    @POST("update_inventory")
    fun updateInventory(
        @Header("Authorization") authHeader: String?,
        @Query("id") id: String?,
        @Query("stock_status") stock_status: String?,
        @Query("stock_qty") stock_qty: String?,
        @Query("sku") sku: String?,
        @Query("stock_manage") stock_manage: String?,
    ): Call<ModelUpdateInv>

    @Headers("content-type: application/json")
    @POST("make_order")
    fun makeOrder(
        @Header("Authorization") authHeader: String?,
        @Query("customer_type") customer_type: String?,
        @Query("delivery_type") delivery_type: String?,
        @Query("shipping_method") shipping_method: String?,
        @Query("payment_id") payment_id: String?,
        @Query("payment_method") payment_method: String?,
        @Query("payment_status") payment_status: String?,
        @Query("name") name: String?,
        @Query("email") email: String?,
        @Query("phone") phone: String?,
        @Query("comment") comment: String?,
        @Query("address") address: String?,
        @Query("zip_code") zip_code: String?,
        @Query("location") location: String?,
        @Query("total")total:String?,
        @Query("discount")discount:String?,
        @Query("tax")tax:String?,
    ): Call<ModelCoupon>

    @Multipart
    @POST("profile")
    fun updateProfilePic(
        @Header("Authorization") Authorization: String,
        @Part logo: MultipartBody.Part,
        @Part("desc") desc: RequestBody,
    ): Call<ModelUpdateProfile>

    @Headers("content-type: application/json")
    @GET("logos")
    fun getLogo(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelLogo>


    @Headers("content-type: application/json")
    @POST("marketing")
    fun marketing(
        @Header("Authorization") authHeader: String?,
        @Query("type") type: String?,
        @Query("tag_id") tag_id: String?,
        @Query("status") status: String?,
    ): Call<ModelCoupon>

    @Headers("content-type: application/json")
    @POST("marketing")
    fun marketingWhatsapp(
        @Header("Authorization") authHeader: String?,
        @Query("type") type: String?,
        @Query("number") number: String?,
        @Query("shop_page_pretext") shop_page_pretext: String?,
        @Query("other_page_pretext") other_page_pretext: String?,
        @Query("status") status: String?,
    ): Call<ModelCoupon>

    @Headers("content-type: application/json")
    @POST("marketing")
    fun marketingGoogleAnalytics(
        @Header("Authorization") authHeader: String?,
        @Query("type") type: String?,
        @Query("ga_measurement_id") ga_measurement_id: String?,
        @Query("analytics_view_id") shop_page_pretext: String?,
        @Query("status") status: String?,
    ): Call<ModelCoupon>

    @Headers("content-type: application/json")
    @POST("get_marketing")
    fun marketingGetAnalytics(
        @Header("Authorization") authHeader: String?,
        @Query("param") param: String?,
    ): Call<ModelGoolgeAna>

    @Headers("content-type: application/json")
    @POST("get_marketing")
    fun marketingGetWhatsapp(
        @Header("Authorization") authHeader: String?,
        @Query("param") param: String?,
    ): Call<ModelWhatsaap>

    @Headers("content-type: application/json")
    @POST("get_marketing")
    fun marketingGetGoogleTab(
        @Header("Authorization") authHeader: String?,
        @Query("param") param: String?,
    ): Call<ModelTagManager>

    @Headers("content-type: application/json")
    @POST("get_marketing")
    fun marketingGetFaceBook(
        @Header("Authorization") authHeader: String?,
        @Query("param") param: String?,
    ): Call<ModelFacebookPixel>

    @Headers("content-type: application/json")
    @POST("theme_settings")
    fun themeSettingsGeneral(
        @Header("Authorization") authHeader: String?,
        @Query("type") type: String?,
        @Query("shop_name") shop_name: String?,
        @Query("shop_description") shop_description: String?,
        @Query("store_email") store_email: String?,
        @Query("order_prefix") order_prefix: String?,
        @Query("currency_position") currency_position: String?,
        @Query("currency_name") currency_name: String?,
        @Query("currency_icon") currency_icon: String?,
        @Query("order_receive_method") order_receive_method: String?,
        @Query("shop_type") shop_type: String?,
        @Query("tax") tax: String?,
    ): Call<ModelCoupon>

    @Headers("content-type: application/json")
    @POST("theme_settings")
    fun themeSettingsLocation(
        @Header("Authorization") authHeader: String?,
        @Query("company_name") company_name: String?,
        @Query("type") type: String?,
        @Query("address") address: String?,
        @Query("city") city: String?,
        @Query("state") state: String?,
        @Query("zip_code") zip_code: String?,
        @Query("email") email: String?,
        @Query("phone") phone: String?,
        @Query("invoice_description") invoice_description: String?,
    ): Call<ModelCoupon>

    @Headers("content-type: application/json")
    @POST("domain_details?")
    fun domainDetails(
        @Header("Authorization") authHeader: String?,
    ): Call<ModelDomain>

    @Headers("content-type: application/json")
    @POST("domain_update_popup?")
    fun domainUpdate(
        @Header("Authorization") authHeader: String?,
        @Query("domain") domain:String
    ): Call<ModelUpdateDomain>

    @Headers("content-type: application/json")
    @POST("authorise_nimbus?")
    fun getNimbusToken(
        @Header("Authorization") authHeader: String?,
     ): Call<ModelNimbusToken>

    @Headers("content-type: application/json")
    @POST("login_nimbus?")
    fun loginNimbus(
        @Header("Authorization") authHeader: String?,
        @Query("email") email:String,
        @Query("password") password:String,
     ): Call<ModelLoginNimbus>

    @Headers("content-type: application/json")
    @POST("payment_list?")
    fun paymentList(
        @Header("Authorization") authHeader: String?,
     ): Call<ModelPaymentList>

    @Headers("content-type: application/json")
    @POST("noti_list?")
    fun notiList(
        @Header("Authorization") authHeader: String?,
     ): Call<ModelNotification>

}