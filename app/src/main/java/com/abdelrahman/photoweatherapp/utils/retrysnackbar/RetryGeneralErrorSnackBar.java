package com.abdelrahman.photoweatherapp.utils.retrysnackbar;

import androidx.fragment.app.Fragment;

import com.abdelrahman.photoweatherapp.R;


public class RetryGeneralErrorSnackBar {
    public static void show(RetryHandler retryHandler, Fragment fragment) {
        RetrySnackBar.show(retryHandler, fragment, R.string.general_error_msg);
    }
}
