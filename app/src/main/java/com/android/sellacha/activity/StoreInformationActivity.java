package com.android.sellacha.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.sellacha.R;
import com.android.sellacha.databinding.ActivityStoreInformationBinding;
import com.android.sellacha.utils.StatusBarUtils;

public class StoreInformationActivity extends BaseActivity {
    ActivityStoreInformationBinding binding;
    public NavController navController;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_information);
        StatusBarUtils.transparentStatusAndNavigation(this);
        navController = Navigation.findNavController(this, R.id.nav_host_main);
        navigationHandler();
        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    public void navigationHandler() {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.storeInfoFragment) {
                binding.firstStartTxt.setBackgroundResource(R.drawable.blue_cirlce_bg);
                binding.secondStartTxt.setBackgroundResource(R.drawable.unselected_circle_bg);
                binding.thirdStartTxt.setBackgroundResource(R.drawable.unselected_circle_bg);
                binding.fourStartTxt.setBackgroundResource(R.drawable.unselected_circle_bg);

                binding.firstStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
                binding.secondStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
                binding.thirdStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
                binding.fourStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));

            } else if (destination.getId() == R.id.themeFragment) {
                binding.firstStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.secondStartTxt.setBackgroundResource(R.drawable.blue_cirlce_bg);
                binding.thirdStartTxt.setBackgroundResource(R.drawable.unselected_circle_bg);
                binding.fourStartTxt.setBackgroundResource(R.drawable.unselected_circle_bg);

                binding.firstStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.secondStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
                binding.thirdStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
                binding.fourStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
            } else if (destination.getId() == R.id.uploadLogoFragment) {
                binding.firstStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.secondStartTxt.setBackgroundResource(R.drawable.blue_cirlce_bg);
                binding.thirdStartTxt.setBackgroundResource(R.drawable.unselected_circle_bg);
                binding.fourStartTxt.setBackgroundResource(R.drawable.unselected_circle_bg);

                binding.firstStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.secondStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
                binding.thirdStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
                binding.fourStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
            } else if (destination.getId() == R.id.colorSchemeFragment) {
                binding.firstStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.secondStartTxt.setBackgroundResource(R.drawable.blue_cirlce_bg);
                binding.thirdStartTxt.setBackgroundResource(R.drawable.unselected_circle_bg);
                binding.fourStartTxt.setBackgroundResource(R.drawable.unselected_circle_bg);

                binding.firstStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.secondStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
                binding.thirdStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
                binding.fourStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));


                //dONE
            } else if (destination.getId() == R.id.addCategoryFragment) {
                binding.firstStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.secondStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.thirdStartTxt.setBackgroundResource(R.drawable.blue_cirlce_bg);
                binding.fourStartTxt.setBackgroundResource(R.drawable.unselected_circle_bg);

                binding.firstStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.secondStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.thirdStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
                binding.fourStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
            }  else if (destination.getId() == R.id.addProduct2Fragment) {
                binding.firstStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.secondStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.thirdStartTxt.setBackgroundResource(R.drawable.blue_cirlce_bg);
                binding.fourStartTxt.setBackgroundResource(R.drawable.unselected_circle_bg);

                binding.firstStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.secondStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.thirdStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
                binding.fourStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));


            } else if (destination.getId() == R.id.googleAnalyticsFragment) {
                binding.firstStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.secondStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.thirdStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.fourStartTxt.setBackgroundResource(R.drawable.blue_cirlce_bg);

                binding.firstStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.secondStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.thirdStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.fourStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
            } else if (destination.getId() == R.id.tapManagerFragment) {
                binding.firstStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.secondStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.thirdStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.fourStartTxt.setBackgroundResource(R.drawable.blue_cirlce_bg);

                binding.firstStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.secondStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.thirdStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.fourStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
            } else if (destination.getId() == R.id.facebookPixelFragment) {
                binding.firstStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.secondStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.thirdStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.fourStartTxt.setBackgroundResource(R.drawable.blue_cirlce_bg);
                binding.firstStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.secondStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.thirdStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.fourStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
            } else if (destination.getId() == R.id.whatsappApiFragment) {
                binding.firstStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.secondStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.thirdStartTxt.setBackgroundResource(R.drawable.selected_type);
                binding.fourStartTxt.setBackgroundResource(R.drawable.blue_cirlce_bg);

                binding.firstStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.secondStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.thirdStart.setBackgroundColor(getResources().getColor(R.color.green));
                binding.fourStart.setBackgroundColor(getResources().getColor(R.color._E8E8E8));
            }
        });
    }

}