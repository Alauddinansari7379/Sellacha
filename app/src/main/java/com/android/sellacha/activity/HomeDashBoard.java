package com.android.sellacha.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.android.sellacha.Profile.activity.ProfileActivity;
import com.android.sellacha.R;
import com.android.sellacha.databinding.ActivityHomeDashBoardBinding;
import com.android.sellacha.utils.KeyboardListenerUtils;
import com.android.sellacha.utils.StatusBarUtils;

import java.util.ArrayList;

import com.android.sellacha.helper.CbnMenuItem;

public class HomeDashBoard extends BaseActivity {
    public NavController navController;
    ActivityHomeDashBoardBinding binding;
    ArrayList<CbnMenuItem> cbnMenuItems = new ArrayList<CbnMenuItem>();
    boolean isLoad = false;
    final Handler handler = new Handler(Looper.getMainLooper());

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_dash_board);
        StatusBarUtils.transparentStatusAndNavigation(this);
        navController = Navigation.findNavController(this, R.id.nav_host_main);
        cbnMenuItems.add(new CbnMenuItem(R.drawable.icn_home, R.drawable.selected_home, R.id.homeFragment));
        cbnMenuItems.add(new CbnMenuItem(R.drawable.icn_note, R.drawable.selected_note, R.id.orderFragment));
        cbnMenuItems.add(new CbnMenuItem(R.drawable.icn_product, R.drawable.product_selected, R.id.productFragment));
        cbnMenuItems.add(new CbnMenuItem(R.drawable.icn_notification, R.drawable.notification_selected, R.id.notificationFragment));
        KeyboardListenerUtils.addKeyboardToggleListener(this, new KeyboardListenerUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                if (isVisible) {
                    binding.mainLayout.bottomNav.setVisibility(View.GONE);
                } else {
                    binding.mainLayout.bottomNav.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.mainLayout.bottomNav.setMenuItems(cbnMenuItems, 0);
        binding.mainLayout.bottomNav.setupWithNavController(navController);

        binding.mainLayout.headerLayout.menuBtn.setOnClickListener(view ->
                {
                    drawerLock();
                }
        );

        binding.drawerLayout.backBtn.setOnClickListener(view ->
                {
                    drawerLock();
                }
        );

        binding.drawerLayout.Settings.setOnClickListener(view ->
                {
                    drawerLock();
                    // handler.postDelayed(() ->
                    navController.navigate(R.id.settingFragment, null, getNavOptions());

                }
        );
        binding.drawerLayout.ReviewRatingsll.setOnClickListener(view ->
                {
                    drawerLock();
                    navController.navigate(R.id.ratingFragment);

                    // handler.postDelayed(() ->
                    navController.navigate(R.id.ratingFragment, null, getNavOptions());
                }
        );
        binding.drawerLayout.customersll.setOnClickListener(view ->
                {
                    drawerLock();
                    navController.navigate(R.id.customerFragment);
                    // handler.postDelayed(() ->
                    navController.navigate(R.id.customerFragment, null, getNavOptions());
                }
        );
        binding.mainLayout.headerLayout.profileBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, ProfileActivity.class));
            overridePendingTransition(R.anim.bottom_anim, R.anim.bottom_out_anim);
        });

        binding.drawerLayout.locationBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.locationFragment);
            // handler.postDelayed(() ->
            navController.navigate(R.id.locationFragment, null, getNavOptions());
        });
        binding.drawerLayout.shippingPriceBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.ShippingFragmentFragment);
            // handler.postDelayed(() ->
            navController.navigate(R.id.ShippingFragmentFragment, null, getNavOptions());
        });

        binding.drawerLayout.googleAnlBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.googleAnalyticsFragment);
            //  handler.postDelayed(() ->
            navController.navigate(R.id.googleAnalyticsFragment, null, getNavOptions());
        });

        binding.drawerLayout.googleTapBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.tapManagerFragment);
            //handler.postDelayed(() ->
            navController.navigate(R.id.tapManagerFragment, null, getNavOptions());
        });

        binding.drawerLayout.whatsappApiBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.whatsappApiFragment);
            //  handler.postDelayed(() ->
            navController.navigate(R.id.whatsappApiFragment, null, getNavOptions());
        });
        binding.drawerLayout.facebookPixelBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.facebookPixelFragment);
            // handler.postDelayed(() ->
            navController.navigate(R.id.facebookPixelFragment, null, getNavOptions());
        });

        binding.drawerLayout.allOrderBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.orderFragment);
            // handler.postDelayed(() ->
            navController.navigate(R.id.orderFragment, null, getNavOptions());
        });

//        binding.drawerLayout.cancelOrderBtn.setOnClickListener(view -> {
//            drawerLock();
//            navController.navigate(R.id.orderFragment);
//            // handler.postDelayed(() ->
//            navController.navigate(R.id.orderFragment, null, getNavOptions());
//        });

        binding.drawerLayout.allProductBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.productFragment);
            //  handler.postDelayed(() ->
            navController.navigate(R.id.productFragment, null, getNavOptions());
        });

        binding.drawerLayout.invantoryBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.inventroyFragment);
            // handler.postDelayed(() ->
            navController.navigate(R.id.inventroyFragment, null, getNavOptions());
        });

        binding.drawerLayout.categoryBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.categoryFragment);
            //  handler.postDelayed(() ->
            navController.navigate(R.id.categoryFragment, null, getNavOptions());
        });

        binding.drawerLayout.attributesBtn.setOnClickListener(view -> {
//            drawerLock();
//            navController.navigate(R.id.attributeFragment);
//            handler.postDelayed(() -> navController.navigate(R.id.attributeFragment, null, getNavOptions()), 300);
            drawerLock();
            navController.navigate(R.id.attributeFragment);
            // handler.postDelayed(() ->
            navController.navigate(R.id.attributeFragment, null, getNavOptions());
        });

        binding.drawerLayout.brandBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.brandFragment);
            //  handler.postDelayed(() ->
            navController.navigate(R.id.brandFragment, null, getNavOptions());
        });

        binding.drawerLayout.bumpAdsBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.BumpAdsFragmentFragment);
            //  handler.postDelayed(() ->
            navController.navigate(R.id.BumpAdsFragmentFragment, null, getNavOptions());
        });

        binding.drawerLayout.bannerAdsBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.BannerAdsFragmentFragment);
            //  handler.postDelayed(() ->
            navController.navigate(R.id.BannerAdsFragmentFragment, null, getNavOptions());
        });

        binding.drawerLayout.transactions.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.transactionFragment);
            //  handler.postDelayed(() ->
            navController.navigate(R.id.transactionFragment, null, getNavOptions());
        });
        binding.drawerLayout.reportsll.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.reportFragment);
            //  handler.postDelayed(() ->
            navController.navigate(R.id.reportFragment, null, getNavOptions());
        });

        binding.drawerLayout.couponBtn.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.couponFragment);
            //handler.postDelayed(() ->
            navController.navigate(R.id.couponFragment, null, getNavOptions());
        });

        binding.drawerLayout.dashboardll.setOnClickListener(view -> {
            drawerLock();
            navController.navigate(R.id.homeFragment);
            // handler.postDelayed(() ->
            navController.navigate(R.id.homeFragment, null, getNavOptions());
        });


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.homeFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Home");
                isLoad = true;
            } else if (destination.getId() == R.id.orderFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Order");
            } else if (destination.getId() == R.id.productFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Product");
            } else if (destination.getId() == R.id.notificationFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Notification");
            } else if (destination.getId() == R.id.orderDetailsFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Order Detail");
            } else if (destination.getId() == R.id.settingFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Setting");
            } else if (destination.getId() == R.id.generalSettingFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("General Setting");
            } else if (destination.getId() == R.id.createCustomerFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Create Customer");
            } else if (destination.getId() == R.id.customerFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Customer");
            } else if (destination.getId() == R.id.ratingFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Ratings");
            } else if (destination.getId() == R.id.createProductFragmnet) {
                binding.mainLayout.headerLayout.txtLogo.setText("Create Product");
            } else if (destination.getId() == R.id.inventroyFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Inventory");
            } else if (destination.getId() == R.id.categoryFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Categories");
            } else if (destination.getId() == R.id.attributeFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Attributes");
            } else if (destination.getId() == R.id.brandFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Brands");
            } else if (destination.getId() == R.id.couponFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Coupons");
            } else if (destination.getId() == R.id.locationFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Location");
            } else if (destination.getId() == R.id.CreateShippingMethodFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Shipping");
            } else if (destination.getId() == R.id.googleAnalyticsFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Analytics");
            } else if (destination.getId() == R.id.tapManagerFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Tap Manager");
            } else if (destination.getId() == R.id.facebookPixelFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Facebook Api");
            } else if (destination.getId() == R.id.whatsappApiFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Whatsapp Api");
            } else if (destination.getId() == R.id.transactionFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Transaction");
            } else if (destination.getId() == R.id.reportFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Report");
            } else if (destination.getId() == R.id.BumpAdsFragmentFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Bump Ads");
            } else if (destination.getId() == R.id.BannerAdsFragmentFragment) {
                binding.mainLayout.headerLayout.txtLogo.setText("Banner Ads");


            }
        });

        binding.drawerLayout.productsrl.setOnClickListener(view -> {
            if (binding.drawerLayout.productLayout.getVisibility() == View.VISIBLE) {
                binding.drawerLayout.productLayout.setVisibility(View.GONE);

                binding.drawerLayout.productLayout.setBackgroundColor(getResources().getColor(R.color.white));
                binding.drawerLayout.productsrl.setBackgroundColor(getResources().getColor(R.color.white));
                binding.drawerLayout.productArrow.setImageResource(R.drawable.icn_next_arrow);
            } else {
                binding.drawerLayout.productLayout.setVisibility(View.VISIBLE);

                binding.drawerLayout.productsrl.setBackgroundColor(getResources().getColor(R.color._F8F8F8));
                binding.drawerLayout.productLayout.setBackgroundColor(getResources().getColor(R.color._F8F8F8));
                binding.drawerLayout.productArrow.setImageResource(R.drawable.icn_drop_arrow);
            }
        });


        binding.drawerLayout.orders.setOnClickListener(view -> {
            if (binding.drawerLayout.orderLayout.getVisibility() == View.VISIBLE) {
                binding.drawerLayout.orderLayout.setVisibility(View.GONE);

                binding.drawerLayout.orderLayout.setBackgroundColor(getResources().getColor(R.color.white));
                binding.drawerLayout.orders.setBackgroundColor(getResources().getColor(R.color.white));
                binding.drawerLayout.orderArrow.setImageResource(R.drawable.icn_next_arrow);
            } else {
                binding.drawerLayout.orderLayout.setVisibility(View.VISIBLE);

                binding.drawerLayout.orders.setBackgroundColor(getResources().getColor(R.color._F8F8F8));
                binding.drawerLayout.orderLayout.setBackgroundColor(getResources().getColor(R.color._F8F8F8));
                binding.drawerLayout.orderArrow.setImageResource(R.drawable.icn_drop_arrow);
            }
        });

        binding.drawerLayout.shippingrl.setOnClickListener(view -> {
            if (binding.drawerLayout.lLocationsll.getVisibility() == View.VISIBLE) {
                binding.drawerLayout.lLocationsll.setVisibility(View.GONE);

                binding.drawerLayout.lLocationsll.setBackgroundColor(getResources().getColor(R.color.white));
                binding.drawerLayout.shippingrl.setBackgroundColor(getResources().getColor(R.color.white));
                binding.drawerLayout.shippingIconArrow.setImageResource(R.drawable.icn_next_arrow);
            } else {
                binding.drawerLayout.lLocationsll.setVisibility(View.VISIBLE);

                binding.drawerLayout.shippingrl.setBackgroundColor(getResources().getColor(R.color._F8F8F8));
                binding.drawerLayout.lLocationsll.setBackgroundColor(getResources().getColor(R.color._F8F8F8));
                binding.drawerLayout.shippingIconArrow.setImageResource(R.drawable.icn_drop_arrow);
            }
        });
        binding.drawerLayout.offerl.setOnClickListener(view -> {
            if (binding.drawerLayout.layoutAds.getVisibility() == View.VISIBLE) {
                binding.drawerLayout.layoutAds.setVisibility(View.GONE);

                binding.drawerLayout.layoutAds.setBackgroundColor(getResources().getColor(R.color.white));
                binding.drawerLayout.offerl.setBackgroundColor(getResources().getColor(R.color.white));
                binding.drawerLayout.offerIconArrow.setImageResource(R.drawable.icn_next_arrow);
            } else {
                binding.drawerLayout.layoutAds.setVisibility(View.VISIBLE);

                binding.drawerLayout.offerl.setBackgroundColor(getResources().getColor(R.color._F8F8F8));
                binding.drawerLayout.layoutAds.setBackgroundColor(getResources().getColor(R.color._F8F8F8));
                binding.drawerLayout.offerIconArrow.setImageResource(R.drawable.icn_drop_arrow);
            }
        });

        binding.drawerLayout.marketingTools.setOnClickListener(view -> {
            if (binding.drawerLayout.marketingMenu.getVisibility() == View.VISIBLE) {
                binding.drawerLayout.marketingMenu.setVisibility(View.GONE);
                binding.drawerLayout.marketingTools.setBackgroundColor(getResources().getColor(R.color.white));
                binding.drawerLayout.marketingMenu.setBackgroundColor(getResources().getColor(R.color.white));
                binding.drawerLayout.marketingIcn.setImageResource(R.drawable.icn_next_arrow);
            } else {
                binding.drawerLayout.marketingMenu.setVisibility(View.VISIBLE);
                binding.drawerLayout.marketingTools.setBackgroundColor(getResources().getColor(R.color._F8F8F8));
                binding.drawerLayout.marketingMenu.setBackgroundColor(getResources().getColor(R.color._F8F8F8));
                binding.drawerLayout.marketingIcn.setImageResource(R.drawable.icn_drop_arrow);
            }
        });
        // navController.navigate(R.id.homeFragment);
    }

    void drawerLock() {
        if (binding.mainDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            binding.mainDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            binding.mainDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    protected NavOptions getNavOptions() {

        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.wait)
                .setExitAnim(R.anim.slide_nav)
                .setPopEnterAnim(R.anim.slide_right)
                .setPopExitAnim(R.anim.slide_nav)
                .build();

        return navOptions;
    }
}