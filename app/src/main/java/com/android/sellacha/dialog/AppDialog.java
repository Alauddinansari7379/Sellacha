package com.android.sellacha.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.android.sellacha.R;
import com.android.sellacha.databinding.CustomDialogBinding;


public class AppDialog extends BaseDialogFragment {
    public static final String TAG = "WayremAlertDialogFragment";
    AppDialog _self;
    CustomDialogBinding binding;
    WayremAlertDialogListener mListener;

    public static String ARG_POSITIVE_BTN = "ARG_POSITIVE_BTN";
    public static String ARG_NEGATIVE_BTN = "ARG_NEGATIVE_BTN";
    public static String ARG_TITLE = "ARG_TITLE";
    public static String ARG_MSG = "ARG_MSG";

    String title, msg, positiveBtn, negativeBtn;

    public AppDialog() {
        _self = this;
    }

    public static AppDialog getInstance(String title, String msg, String positiveBtn, String negativeBtn, WayremAlertDialogListener listener) {
        AppDialog frag = new AppDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_POSITIVE_BTN, positiveBtn);
        bundle.putString(ARG_NEGATIVE_BTN, negativeBtn);
        bundle.putString(ARG_TITLE, title);
        bundle.putString(ARG_MSG, msg);
        frag.setupListener(listener);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback( new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

            }
        });
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            msg = getArguments().getString(ARG_MSG);
            positiveBtn = getArguments().getString(ARG_POSITIVE_BTN);
            negativeBtn = getArguments().getString(ARG_NEGATIVE_BTN);
        }

    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        if (!TextUtils.isEmpty(title)) {
            binding.heading.setText(title);
        } else {
            binding.heading.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(msg)) {
            binding.message.setText(msg);
        } else {
            binding.message.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(positiveBtn)) {
            binding.okBtn.setText(positiveBtn);
        } else {
            binding.okBtn.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(negativeBtn)) {
            binding.cancelBtn.setText(negativeBtn);
        } else {
            binding.cancelBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void setupClickListener() {
        binding.okBtn.setOnClickListener(v -> {
            WayremAlertDialogListener listener = getListener();
            if (listener != null) {
                listener.onOk(_self);
                dismiss();
            } else {
                dismiss();
            }
        });
        binding.cancelBtn.setOnClickListener(v -> {
            WayremAlertDialogListener listener = getListener();
            if (listener != null) {
                listener.onCancel(_self);
                dismiss();
            } else {
                dismiss();
            }
        });
    }

    @Override
    public void loadData() {

    }

    public interface WayremAlertDialogListener {
        void onOk(AppDialog dialogFragment);
      default  void onCancel(AppDialog dialogFragment){}
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog_Short);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        if (dialog.getWindow() != null)
        {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(mContext.getColor(R.color.light_transprate)));
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_dialog, container, false);
        return binding.getRoot();
    }

    public void setupListener(WayremAlertDialogListener listener) {
        mListener = listener;
    }

    private WayremAlertDialogListener getListener()
    {
        WayremAlertDialogListener listener = mListener;

        if (listener==null && getTargetFragment()!=null && getTargetFragment() instanceof WayremAlertDialogListener)
            listener = (WayremAlertDialogListener) getTargetFragment();

        if (listener==null && getActivity()!=null && getActivity() instanceof WayremAlertDialogListener)
            listener = (WayremAlertDialogListener) getActivity();

        return listener;
    }

}
