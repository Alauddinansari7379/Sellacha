package com.android.sellacha.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.TypedValue;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.sellacha.activity.BaseActivity;
import com.android.sellacha.dialog.AppDialog;
import com.android.sellacha.utils.AppProgressBar;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;


public class BaseFragment extends Fragment {
    public Context mContext;
    public boolean isfavUpdated = false;
    Toast toast;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void showToast(String msg) {
        try {
            toast.getView().isShown();     // true if visible
            toast.setText(msg);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        }
        toast.show();  //finally display it
    }


    public void showLog(String tag, String msg) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showLog(tag, msg);
        }
    }

    public void showDebug(String tag, String msg) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showDebug(tag, msg);
        }
    }

    public void showSnackBar(View v, String msg) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).errorSnackBar(v, msg);
        }
    }

    public void errorSnackBar(View v, String message) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).errorSnackBar(v, message);
        }
    }

    public void successSnackBar(View v, String message) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).errorSnackBar(v, message);
        }
    }

    public void showAlertDialog(String title, String msg, String positiveBtn, String negativeBtn, AppDialog.WayremAlertDialogListener listener) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AppDialog infoDialogFragment = AppDialog.getInstance(title, msg, positiveBtn, negativeBtn, listener);
        infoDialogFragment.setupListener(listener);
        ft.add(infoDialogFragment, AppDialog.TAG);
        ft.commitAllowingStateLoss();
    }

    public void showLoader() {
        AppProgressBar.showLoaderDialog(mContext);
    }

    public void hideLoader() {
        AppProgressBar.hideLoaderDialog();
    }

    public void loadWebView(WebView webView, ProgressBar pBar, String fileUrl) {
        pBar.setVisibility(View.VISIBLE);
        String url = "https://docs.google.com/gview?embedded=true&url=" + fileUrl;
        webView.getSettings().setDomStorageEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadWithOverviewMode(false);
        webView.getSettings().setUseWideViewPort(false);
        webView.getSettings().setBlockNetworkImage(false);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += " Do you want to continue anyway?";
                builder.setMessage(message);
                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
                hideLoader();
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
            }
        });
    }

    public File createImageFile() throws IOException {
        String imageFileName = "Wayrem_Image-" + System.currentTimeMillis() + "_";
        File storageDir = mContext.getFilesDir();
        return File.createTempFile(imageFileName, ".png", storageDir);
    }

    public boolean haveWritePermission(Context context) {
        int result = context.checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    public int dp2px(@NotNull Resources resource, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resource.getDisplayMetrics());
    }


    public int productAvailabilityCheck(int qty, int threshold) {
        if (qty - threshold == 0) {
            return qty;
        } else if (qty > threshold) {
            return qty;
        } else if (qty <= threshold) {
            return qty;
        }
        return qty;
    }

    public String productAvailabilityCheckLB(int qty, int threshold) {
        if (qty - threshold == 0) {
            //    return "Only " + qty + " left in stock";
            return "Out of Stock";
        } else if (qty <= 0) {
            return "Out of Stock";
        } else if (qty > threshold) {
            return "";
        } else if (qty <= threshold) {
            return "Only " + qty + " left in stock";
        }
        return "";
    }

}
