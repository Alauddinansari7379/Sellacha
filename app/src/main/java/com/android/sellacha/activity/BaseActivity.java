package com.android.sellacha.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.sellacha.utils.AppProgressBar;
import com.android.sellacha.R;
import com.android.sellacha.dialog.AppDialog;
import com.google.android.material.snackbar.Snackbar;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class BaseActivity extends AppCompatActivity {
    Toast toast;
    public Dialog noInternetdialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noInternetdialog = new Dialog(this);
    }

    public void showToast(String msg) {
        try {
            toast.getView().isShown();
            toast.setText(msg);
        } catch (Exception e) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public void showLog(String tag, String msg) {
        Log.e(tag, msg);
    }

    public void showDebug(String tag, String msg) {
        Log.d(tag, msg);
    }

    public void showSuccessToast(String title, String message) {
        MotionToast.Companion.createToast(this,
                title,
                message,
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.montserrat_bold));

    }

    public void showErrorToast(String title, String message) {
        MotionToast.Companion.createToast(this,
                title,
                message,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.montserrat_bold));
    }


    public void textHighLight(String str, TextView text, String color) {
        Link link = new Link(str)
                .setTextColor(Color.parseColor(color))
                .setHighlightAlpha(.4f);
        LinkBuilder.on(text)
                .addLink(link)
                .build();
    }

    public void hideLoader() {
        AppProgressBar.hideLoaderDialog();
    }

    public void showAlertDialog(String title, String msg, String positiveBtn, String negativeBtn, AppDialog.WayremAlertDialogListener listener) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AppDialog infoDialogFragment = AppDialog.getInstance(title, msg, positiveBtn, negativeBtn, listener);
        infoDialogFragment.setupListener(listener);
        ft.add(infoDialogFragment, AppDialog.TAG);
        ft.commitAllowingStateLoss();
    }

    public void errorSnackBar(View v, String message) {
        final Snackbar snackbar = Snackbar.make(v, "", Snackbar.LENGTH_LONG);
        View customSnackView = getLayoutInflater().inflate(R.layout.error_snackbar, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        View view = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snackbarLayout.addView(customSnackView, 0);
        TextView textView = snackbarLayout.findViewById(R.id.textView2);
        textView.setText(message);
        snackbar.show();
    }
    public void successSnackBar(View v, String message) {
        final Snackbar snackbar = Snackbar.make(v, "", Snackbar.LENGTH_LONG);
        View customSnackView = getLayoutInflater().inflate(R.layout.success_snackbar, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        View view = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snackbarLayout.addView(customSnackView, 0);
        TextView textView = snackbarLayout.findViewById(R.id.textView2);
        textView.setText(message);
        snackbar.show();
    }

}
