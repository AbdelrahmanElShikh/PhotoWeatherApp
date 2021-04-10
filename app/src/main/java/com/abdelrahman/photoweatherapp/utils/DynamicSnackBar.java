package com.abdelrahman.photoweatherapp.utils;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 08-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.utils
 * @Function : Show dynamic MultiLine SnackBar
 */
public class DynamicSnackBar {
    public static Snackbar make(View view, String text, int length) {
        Snackbar snackbar = Snackbar.make(view, text, length);
        View snackBarView = snackbar.getView();
        TextView textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setMaxLines(5);
        return snackbar;
    }
}
