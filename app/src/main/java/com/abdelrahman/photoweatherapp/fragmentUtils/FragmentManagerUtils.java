package com.abdelrahman.photoweatherapp.fragmentUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 08-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.fragmentUtils
 */
public class FragmentManagerUtils {

    public static void addFragment(@NonNull FragmentManager childFragmentManager,
                                                     @NonNull Fragment fragment) {
        final String tag = fragment.getClass().getSimpleName();
        final Fragment fragmentByTag = childFragmentManager.findFragmentByTag(tag);
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        if (fragmentByTag != null && fragmentByTag.isAdded()) {
            fragmentTransaction.remove(fragmentByTag);
        }
        fragmentTransaction.add(fragment,
                tag)
                .commit();
    }
}
