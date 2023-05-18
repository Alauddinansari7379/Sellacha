package com.android.sellacha.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;

public abstract class BaseDialogFragment extends DialogFragment {

    Context mContext;

    public BaseDialogFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);
        setupClickListener();
        loadData();
    }

    public abstract void initViews(View view, Bundle savedInstanceState);
    public abstract void setupClickListener();
    public abstract void loadData();

    public String getStringRes(@StringRes int resId)
    {
        if(getContext()!=null)
            return getResources().getString(resId);
        return "";
    }

    public String getStringRes(@StringRes int resId, Object... formatArgs)
    {
        if(getContext()!=null)
            return getResources().getString(resId, formatArgs);
        return "";
    }
}

