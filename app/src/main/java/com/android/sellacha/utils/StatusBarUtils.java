package com.android.sellacha.utils;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.android.sellacha.R;

public class StatusBarUtils {
    public static void statusBarColor(Activity context, int color) {
        context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//  set status text dark
        context.getWindow().setStatusBarColor(ContextCompat.getColor(context, color));//
        context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static void transparentStatusAndNavigation(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//  set status text dark
        activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.primary_bg));//
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

//        //make full transparent statusBar
//        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
//            setWindowFlag(activity,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            activity.getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//            );
//        }
//        if (Build.VERSION.SDK_INT >= 21) {
//            setWindowFlag(activity,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
//        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
