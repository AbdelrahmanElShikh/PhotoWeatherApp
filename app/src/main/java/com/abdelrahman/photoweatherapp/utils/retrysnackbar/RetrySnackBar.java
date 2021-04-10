package com.abdelrahman.photoweatherapp.utils.retrysnackbar;

import androidx.fragment.app.Fragment;


import com.abdelrahman.photoweatherapp.R;
import com.abdelrahman.photoweatherapp.utils.DynamicSnackBar;
import com.google.android.material.snackbar.Snackbar;

public class RetrySnackBar {
    public static void show(RetryHandler retryHandler, Fragment fragment, String msg) {
        DynamicSnackBar.make(fragment.getView(), msg, Snackbar.LENGTH_INDEFINITE)
                .setAction(fragment.getContext().getString(R.string.retry_btn_title), v -> {
                    if (fragment.getView() != null)
                        retryHandler.onRetry();
                })
                .show();
    }

    public static void show(RetryHandler retryHandler, Fragment fragment, int msgRes) {
        DynamicSnackBar.make(fragment.getView(), fragment.getContext().getString(msgRes), Snackbar.LENGTH_INDEFINITE)
                .setAction(fragment.getContext().getString(R.string.retry_btn_title), v -> {
                    if (fragment.getView() != null)
                        retryHandler.onRetry();
                })
                .show();
    }
}
