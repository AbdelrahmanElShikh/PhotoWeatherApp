package com.abdelrahman.photoweatherapp.permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.ArraySet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.abdelrahman.photoweatherapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 08-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.permissions
 */
public class PermissionFragment extends Fragment {

    private static final String sKEY_PERMISSIONS = "PERMISSIONS";
    private static final Set<Integer> requestCodes = new ArraySet<>();
    private int sREQUEST_PERMISSION_CODE;
    private PermissionListener permissionListener;
    private List<PermissionItem> permissions;

    public static PermissionFragment newInstance(PermissionItem... permissionItems) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(sKEY_PERMISSIONS, new ArrayList<>(Arrays.asList(permissionItems)));
        PermissionFragment fragment = new PermissionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Ensuring the Caller (parent) is handling user permission interactions.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PermissionListener) {
            permissionListener = (PermissionListener) context;
        } else if (getParentFragment() instanceof PermissionListener) {
            permissionListener = (PermissionListener) getParentFragment();
        } else {
            throw new IllegalStateException("parent fragment or activity must implement PermissionListener Callback");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (permissions == null) {
            permissions = getArguments().getParcelableArrayList(sKEY_PERMISSIONS);
        }
        generateRequestCode();
    }

    /**
     * Generating randomize request code.
     */
    private void generateRequestCode() {
        if (sREQUEST_PERMISSION_CODE == 0) {
            int i = new Random().nextInt(100);
            while (requestCodes.contains(i)) {
                i = new Random().nextInt(100);
            }
            sREQUEST_PERMISSION_CODE = i;
            requestCodes.add(i);
        }
    }

    public void requestPermissions() {
        final ArrayList<PermissionItem> permissionItems = new ArrayList<>(permissions);
        final ListIterator<PermissionItem> permissionItemListIterator = permissionItems.listIterator();
        while (permissionItemListIterator.hasNext()) {
            final PermissionItem next = permissionItemListIterator.next();
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    next.getName())) {
                permissionItemListIterator.remove();
                showPermissionRationale(next);
            }
        }
        if (!permissionItems.isEmpty())
            requestPermission(permissionItems.toArray(new PermissionItem[0]));
    }

    private void showPermissionRationale(PermissionItem permissionItem) {
        new AlertDialog.Builder(getContext())
                .setTitle(permissionItem.getRationaleTitleRes())
                .setMessage(getString(permissionItem.getRationaleRes()))
                .setPositiveButton(R.string.button_allow, (dialog, which) -> requestPermission(permissionItem))
                .setNegativeButton(R.string.button_cancel, (dialog, which) -> {
                    //do nothing
                })
                .create().show();
    }

    private void requestPermission(PermissionItem... permissionItems) {
        final String[] permissions = new String[permissionItems.length];
        for (int i = 0; i < permissions.length; i++) {
            permissions[i] = permissionItems[i].getName();
        }
        requestPermissions(permissions,
                sREQUEST_PERMISSION_CODE);
    }

    public boolean hasAllPermissions() {
        for (PermissionItem permissionItem : permissions) {
            int permissionState = ActivityCompat.checkSelfPermission(getContext(),
                    permissionItem.getName());
            if (permissionState != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == sREQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0) {
                final ArrayList<String> grantedStrings = new ArrayList<>();
                addGrantedStrings(grantedStrings, permissions, grantResults);
                final ArrayList<PermissionItem> granted = new ArrayList<>(this.permissions);
                final ArrayList<PermissionItem> denied = new ArrayList<>();
                final ListIterator<PermissionItem> permissionItemListIterator = granted.listIterator();
                while (permissionItemListIterator.hasNext()) {
                    final PermissionItem next = permissionItemListIterator.next();
                    if (!grantedStrings.contains(next.getName())) {
                        permissionItemListIterator.remove();
                        denied.add(next);
                    }
                }
                if (!granted.isEmpty()) permissionListener.onGranted(granted);
                if (!denied.isEmpty()) permissionListener.onDeny(denied);
            }


        }
    }

    private void addGrantedStrings(ArrayList<String> grantedStrings, String[] permissions, int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                grantedStrings.add(permissions[0]);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requestCodes.remove(sREQUEST_PERMISSION_CODE);
    }

    public interface PermissionListener {
        void onGranted(List<PermissionItem> granted);

        void onDeny(List<PermissionItem> denied);
    }
}
