package com.android.sellacha.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import com.android.sellacha.Profile.activity.ProfileActivity
import com.android.sellacha.Profile.model.ModelLogo
import com.android.sellacha.R
import com.android.sellacha.databinding.ActivityHomeDashBoardBinding
import com.android.sellacha.helper.CbnMenuItem
import com.android.sellacha.helper.myToast
import com.android.sellacha.support.CustomerSupport
import com.android.sellacha.utils.KeyboardListenerUtils
import com.android.sellacha.utils.StatusBarUtils
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
 import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class HomeDashBoard : BaseActivity() {
    var navController: NavController? = null
    lateinit var binding: ActivityHomeDashBoardBinding
    var cbnMenuItems = ArrayList<CbnMenuItem>()
    var isLoad = false
    val handler = Handler(Looper.getMainLooper())
    lateinit var sessionManager: SessionManager

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_dash_board)
        StatusBarUtils.transparentStatusAndNavigation(this)
        navController = findNavController(this, R.id.nav_host_main)

        sessionManager = SessionManager(this@HomeDashBoard)

        if (sessionManager.deviceId!!.isEmpty()) {
            val deviceID = getDeviceId(this@HomeDashBoard)
            sessionManager.deviceId = deviceID
        }
        cbnMenuItems.add(
            CbnMenuItem(
                R.drawable.icn_home,
                R.drawable.selected_home,
                R.id.homeFragment
            )
        )
        cbnMenuItems.add(
            CbnMenuItem(
                R.drawable.icn_note,
                R.drawable.selected_note,
                R.id.orderFragment
            )
        )
        cbnMenuItems.add(
            CbnMenuItem(
                R.drawable.icn_product,
                R.drawable.product_selected,
                R.id.productFragment
            )
        )
        cbnMenuItems.add(
            CbnMenuItem(
                R.drawable.icn_notification,
                R.drawable.notification_selected,
                R.id.notificationFragment
            )
        )
        KeyboardListenerUtils.addKeyboardToggleListener(this) { isVisible ->
            if (isVisible) {
                binding.mainLayout.bottomNav.visibility = View.GONE
            } else {
                binding.mainLayout.bottomNav.visibility = View.VISIBLE
            }
        }

        apiCallGetLogo()
        if (sessionManager.profilePic != "") {
            Picasso.get().load("${sessionManager.profilePic}").networkPolicy(
                NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.user).stableKey("id")
                .into(binding.mainLayout.headerLayout.profileBtn)
            Log.e("pofile", "${sessionManager.profilePic}")
        }


        binding.mainLayout.bottomNav.setMenuItems(cbnMenuItems, 0)
        binding.mainLayout.bottomNav.setupWithNavController(navController!!)
        binding.mainLayout.headerLayout.menuBtn.setOnClickListener { view: View? -> drawerLock() }
        binding.drawerLayout.backBtn.setOnClickListener { view: View? -> drawerLock() }
        binding.drawerLayout.Settings.setOnClickListener { view: View? ->
            drawerLock()
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.settingFragment, null, navOptions)
        }
        binding.drawerLayout.ReviewRatingsll.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.ratingFragment)

            // handler.postDelayed(() ->
            navController!!.navigate(R.id.ratingFragment, null, navOptions)
        }
        binding.drawerLayout.customersll.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.customerFragment)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.customerFragment, null, navOptions)
        }
        binding.mainLayout.headerLayout.profileBtn.setOnClickListener { view: View? ->
            startActivity(Intent(this, ProfileActivity::class.java))
            overridePendingTransition(R.anim.bottom_anim, R.anim.bottom_out_anim)
        }
        binding.drawerLayout.locationBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.locationFragment)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.locationFragment, null, navOptions)
        }
        binding.drawerLayout.shippingPriceBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.ShippingFragmentFragment)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.ShippingFragmentFragment, null, navOptions)
        }
        binding.drawerLayout.googleAnlBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.googleAnalyticsFragment)
            //  handler.postDelayed(() ->
            navController!!.navigate(R.id.googleAnalyticsFragment, null, navOptions)
        }
        binding.drawerLayout.googleTapBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.tapManagerFragment)
            //handler.postDelayed(() ->
            navController!!.navigate(R.id.tapManagerFragment, null, navOptions)
        }
        binding.drawerLayout.whatsappApiBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.whatsappApiFragment)
            //  handler.postDelayed(() ->
            navController!!.navigate(R.id.whatsappApiFragment, null, navOptions)
        }
        binding.drawerLayout.facebookPixelBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.facebookPixelFragment)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.facebookPixelFragment, null, navOptions)
        }
        binding.drawerLayout.allOrderBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.orderFragment)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.orderFragment, null, navOptions)
        }
        binding.drawerLayout.cancelOrderBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.orderFragment)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.orderFragment, null, navOptions)
        }
        binding.drawerLayout.allProductBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.productFragment)
            //  handler.postDelayed(() ->
            navController!!.navigate(R.id.productFragment, null, navOptions)
        }
        binding.drawerLayout.invantoryBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.inventroyFragment)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.inventroyFragment, null, navOptions)
        }
        binding.drawerLayout.categoryBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.categoryFragment)
            //  handler.postDelayed(() ->
            navController!!.navigate(R.id.categoryFragment, null, navOptions)
        }
        binding.drawerLayout.attributesBtn.setOnClickListener { view: View? ->
//            drawerLock();
//            navController.navigate(R.id.attributeFragment);
//            handler.postDelayed(() -> navController.navigate(R.id.attributeFragment, null, getNavOptions()), 300);
            drawerLock()
            navController!!.navigate(R.id.attributeFragment)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.attributeFragment, null, navOptions)
        }
        binding.drawerLayout.brandBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.brandFragment)
            //  handler.postDelayed(() ->
            navController!!.navigate(R.id.brandFragment, null, navOptions)
        }
        binding.drawerLayout.bumpAdsBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.BumpAdsFragmentFragment)
            //  handler.postDelayed(() ->
            navController!!.navigate(R.id.BumpAdsFragmentFragment, null, navOptions)
        }
        binding.drawerLayout.bannerAdsBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.BannerAdsFragmentFragment)
            //  handler.postDelayed(() ->
            navController!!.navigate(R.id.BannerAdsFragmentFragment, null, navOptions)
        }
        binding.drawerLayout.transactions.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.transactionFragment)
            //  handler.postDelayed(() ->
            navController!!.navigate(R.id.transactionFragment, null, navOptions)
        }
        binding.drawerLayout.reportsll.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.reportFragment)
            //  handler.postDelayed(() ->
            navController!!.navigate(R.id.reportFragment, null, navOptions)
        }
        binding.drawerLayout.couponBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.couponFragment)
            //handler.postDelayed(() ->
            navController!!.navigate(R.id.couponFragment, null, navOptions)
        }


        binding.drawerLayout.dashboardll.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.homeFragment)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.homeFragment, null, navOptions)
        }
        binding.drawerLayout.UpgradePlan.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.UpgradePlan)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.UpgradePlan, null, navOptions)
        }
        binding.drawerLayout.PaymentOBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.PaymentOption)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.PaymentOption, null, navOptions)
        }

        binding.drawerLayout.ThemesAndApk.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.ThemesAndAPK)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.ThemesAndAPK, null, navOptions)
        }
        binding.drawerLayout.SubscriptionsBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.Subscriptions)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.Subscriptions, null, navOptions)
        }
        binding.drawerLayout.DomainSBtn.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.DomainSetting)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.DomainSetting, null, navOptions)
        }

        binding.drawerLayout.GeneralShop.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.GeneralFragment)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.GeneralFragment, null, navOptions)
        }

        binding.drawerLayout.Slider.setOnClickListener {
            drawerLock()
            navController!!.navigate(R.id.SliderFragment)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.SliderFragment, null, navOptions)
        }

        binding.drawerLayout.LocationShop.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.LocationShopFragment)
            // handler.postDelayed(() ->
            navController!!.navigate(R.id.LocationShopFragment, null, navOptions)
        }

        binding.drawerLayout.Nimbus.setOnClickListener { view: View? ->
            drawerLock()
            navController!!.navigate(R.id.NimbusFragment)
             navController!!.navigate(R.id.NimbusFragment, null, navOptions)
        }

        binding.drawerLayout.CustomerSupport.setOnClickListener { view: View? ->
            startActivity(Intent(this@HomeDashBoard,CustomerSupport::class.java))
        }
        navController!!.addOnDestinationChangedListener { controller: NavController?, destination: NavDestination, arguments: Bundle? ->
            if (destination.id == R.id.homeFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Home"
                isLoad = true
            } else if (destination.id == R.id.orderFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Order"
            } else if (destination.id == R.id.productFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Product"
            } else if (destination.id == R.id.notificationFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Notification"
            } else if (destination.id == R.id.orderDetailsFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Order Detail"
            } else if (destination.id == R.id.settingFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Setting"
            } else if (destination.id == R.id.generalSettingFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "General Setting"
            } else if (destination.id == R.id.createCustomerFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Create Customer"
            } else if (destination.id == R.id.customerFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Customer"
            } else if (destination.id == R.id.ratingFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Ratings"
            } else if (destination.id == R.id.createProductFragmnet) {
                binding.mainLayout.headerLayout.txtLogo.text = "Create Product"
            } else if (destination.id == R.id.inventroyFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Inventory"
            } else if (destination.id == R.id.categoryFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Categories"
            } else if (destination.id == R.id.attributeFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Attributes"
            } else if (destination.id == R.id.brandFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Brands"
            } else if (destination.id == R.id.couponFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Coupons"
            } else if (destination.id == R.id.locationFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Location"
            } else if (destination.id == R.id.CreateShippingMethodFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Shipping"
            } else if (destination.id == R.id.googleAnalyticsFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Analytics"
            } else if (destination.id == R.id.tapManagerFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Tap Manager"
            } else if (destination.id == R.id.facebookPixelFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Facebook Api"
            } else if (destination.id == R.id.whatsappApiFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Whatsapp Api"
            } else if (destination.id == R.id.transactionFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Transaction"
            } else if (destination.id == R.id.reportFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Report"
            } else if (destination.id == R.id.BumpAdsFragmentFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Bump Ads"
            } else if (destination.id == R.id.BannerAdsFragmentFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Banner Ads"
            } else if (destination.id == R.id.CreateOrderShow) {
                binding.mainLayout.headerLayout.txtLogo.text = "Product List"
            } else if (destination.id == R.id.UpgradePlan) {
                binding.mainLayout.headerLayout.txtLogo.text = "Upgrade Your Plan "
            } else if (destination.id == R.id.PaymentOption) {
                binding.mainLayout.headerLayout.txtLogo.text = "Payment Options"
            } else if (destination.id == R.id.Subscriptions) {
                binding.mainLayout.headerLayout.txtLogo.text = "Subscriptions"
            } else if (destination.id == R.id.DomainSetting) {
                binding.mainLayout.headerLayout.txtLogo.text = "Domain Settings"
            } else if (destination.id == R.id.Instamojo) {
                binding.mainLayout.headerLayout.txtLogo.text = "Instamojo"
            } else if (destination.id == R.id.Razorpay) {
                binding.mainLayout.headerLayout.txtLogo.text = "Razorpay"
            } else if (destination.id == R.id.Paypal) {
                binding.mainLayout.headerLayout.txtLogo.text = "Paypal"
            } else if (destination.id == R.id.ShippingFragmentFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Shipping"
            } else if (destination.id == R.id.GeneralFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "General"
            } else if (destination.id == R.id.LocationShopFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Location"
            } else if (destination.id == R.id.SliderFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Slider"
            } else if (destination.id == R.id.NimbusFragment) {
                binding.mainLayout.headerLayout.txtLogo.text = "Nimbus Shipping"
            }
        }
        binding.drawerLayout.productsrl.setOnClickListener { view: View? ->
            if (binding.drawerLayout.productLayout.visibility == View.VISIBLE) {
                binding.drawerLayout.productLayout.visibility = View.GONE
                binding.drawerLayout.productLayout.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.productsrl.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.productArrow.setImageResource(R.drawable.icn_next_arrow)
            } else {
                binding.drawerLayout.productLayout.visibility = View.VISIBLE
                binding.drawerLayout.productsrl.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.productLayout.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.productArrow.setImageResource(R.drawable.icn_drop_arrow)
            }
        }
        binding.drawerLayout.orders.setOnClickListener { view: View? ->
            if (binding.drawerLayout.orderLayout.visibility == View.VISIBLE) {
                binding.drawerLayout.orderLayout.visibility = View.GONE
                binding.drawerLayout.orderLayout.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.orders.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.orderArrow.setImageResource(R.drawable.icn_next_arrow)
            } else {
                binding.drawerLayout.orderLayout.visibility = View.VISIBLE
                binding.drawerLayout.orders.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.orderLayout.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.orderArrow.setImageResource(R.drawable.icn_drop_arrow)
            }
        }
        binding.drawerLayout.shippingrl.setOnClickListener { view: View? ->
            if (binding.drawerLayout.lLocationsll.visibility == View.VISIBLE) {
                binding.drawerLayout.lLocationsll.visibility = View.GONE
                binding.drawerLayout.lLocationsll.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.shippingrl.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.shippingIconArrow.setImageResource(R.drawable.icn_next_arrow)
            } else {
                binding.drawerLayout.lLocationsll.visibility = View.VISIBLE
                binding.drawerLayout.shippingrl.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.lLocationsll.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.shippingIconArrow.setImageResource(R.drawable.icn_drop_arrow)
            }
        }
        binding.drawerLayout.offerl.setOnClickListener { view: View? ->
            if (binding.drawerLayout.layoutAds.visibility == View.VISIBLE) {
                binding.drawerLayout.layoutAds.visibility = View.GONE
                binding.drawerLayout.layoutAds.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.offerl.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.offerIconArrow.setImageResource(R.drawable.icn_next_arrow)
            } else {
                binding.drawerLayout.layoutAds.visibility = View.VISIBLE
                binding.drawerLayout.offerl.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.layoutAds.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.offerIconArrow.setImageResource(R.drawable.icn_drop_arrow)
            }
        }
        binding.drawerLayout.Settings.setOnClickListener { view: View? ->
            if (binding.drawerLayout.layoutSetting.visibility == View.VISIBLE) {
                binding.drawerLayout.layoutSetting.visibility = View.GONE
                binding.drawerLayout.layoutSetting.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.Settings.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.settingIconArrow.setImageResource(R.drawable.icn_next_arrow)
            } else {
                binding.drawerLayout.layoutSetting.visibility = View.VISIBLE
                binding.drawerLayout.Settings.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.layoutSetting.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.settingIconArrow.setImageResource(R.drawable.icn_drop_arrow)
            }
        }
        binding.drawerLayout.marketingTools.setOnClickListener { view: View? ->
            if (binding.drawerLayout.marketingMenu.visibility == View.VISIBLE) {
                binding.drawerLayout.marketingMenu.visibility = View.GONE
                binding.drawerLayout.marketingTools.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.marketingMenu.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.marketingIcn.setImageResource(R.drawable.icn_next_arrow)
            } else {
                binding.drawerLayout.marketingMenu.visibility = View.VISIBLE
                binding.drawerLayout.marketingTools.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.marketingMenu.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.marketingIcn.setImageResource(R.drawable.icn_drop_arrow)
            }
        }
        binding.drawerLayout.ShopSettings.setOnClickListener { view: View? ->
            if (binding.drawerLayout.layoutShopSetting.visibility == View.VISIBLE) {
                binding.drawerLayout.layoutShopSetting.visibility = View.GONE
                binding.drawerLayout.ShopSettings.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.layoutShopSetting.setBackgroundColor(resources.getColor(R.color.white))
                binding.drawerLayout.ShopSettingIconArrow.setImageResource(R.drawable.icn_next_arrow)
            } else {
                binding.drawerLayout.layoutShopSetting.visibility = View.VISIBLE
                binding.drawerLayout.ShopSettings.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.layoutShopSetting.setBackgroundColor(resources.getColor(R.color._F8F8F8))
                binding.drawerLayout.ShopSettingIconArrow.setImageResource(R.drawable.icn_drop_arrow)
            }
        }
        // navController.navigate(R.id.homeFragment);
    }

    private fun drawerLock() {
        if (binding!!.mainDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            binding!!.mainDrawerLayout.closeDrawer(Gravity.LEFT)
        } else {
            binding!!.mainDrawerLayout.openDrawer(Gravity.LEFT)
        }
    }
    private fun getDeviceId(context: Context): String? {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    }

    private fun apiCallGetLogo() {

        // AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getLogo(sessionManager.authToken)
            .enqueue(object : Callback<ModelLogo> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelLogo>, response: Response<ModelLogo>
                ) {
                    try {
                        if (response.code() == 404) {
                            myToast(this@HomeDashBoard, "Server Error")
                            //   AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 500) {
                            myToast(this@HomeDashBoard, "Server Error")
                            //   AppProgressBar.hideLoaderDialog()

                        } else {
                           // sessionManager.profilePic = response.body()!!.data.logo

//                            Glide
//                                .with(this@HomeDashBoard)
//                                .load("${response.body()!!.data.logo}")
//                                .centerCrop()
//                                .placeholder(R.drawable.user)
//                                .into(binding.mainLayout.headerLayout.profileBtn)
                            Picasso.get().load("${response.body()!!.data.logo}").networkPolicy(
                                NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .placeholder(R.drawable.user).stableKey("id")
                                .into(binding.mainLayout.headerLayout.profileBtn)
                            Log.e("URL", response.body()!!.data.logo)
                            sessionManager.profilePic=response.body()!!.data.logo
                            Log.e("sessionManager", sessionManager.profilePic.toString())
                            //  AppProgressBar.hideLoaderDialog()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }


                override fun onFailure(call: Call<ModelLogo>, t: Throwable) {
                    //  myToast(this@HomeFragment, t.message.toString())
                    // AppProgressBar.hideLoaderDialog()

                }

            })
    }

    private val navOptions: NavOptions
        get() = NavOptions.Builder()
            .setEnterAnim(R.anim.wait)
            .setExitAnim(R.anim.slide_nav)
            .setPopEnterAnim(R.anim.slide_right)
            .setPopExitAnim(R.anim.slide_nav)
            .build()
}