package com.abdelrahman.photoweatherapp.ui.history;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.abdelrahman.core.resource.PresentationResource;
import com.abdelrahman.photoweatherapp.BuildConfig;
import com.abdelrahman.photoweatherapp.R;
import com.abdelrahman.photoweatherapp.databinding.FragmentHistoryBinding;
import com.abdelrahman.photoweatherapp.fragmentUtils.FragmentManagerUtils;
import com.abdelrahman.photoweatherapp.permissions.PermissionFragment;
import com.abdelrahman.photoweatherapp.permissions.PermissionItem;
import com.abdelrahman.photoweatherapp.room.model.WeatherPhoto;
import com.abdelrahman.photoweatherapp.ui.history.adapter.HistoryAdapter;
import com.abdelrahman.photoweatherapp.ui.history.listeners.OnHistoryItemClick;
import com.abdelrahman.photoweatherapp.ui.history.viewmodel.HistoryViewModel;
import com.abdelrahman.photoweatherapp.utils.DynamicSnackBar;
import com.abdelrahman.photoweatherapp.utils.ImageUtils;
import com.abdelrahman.photoweatherapp.utils.errorchains.domain.chains.DomainErrorChain;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 08-Apr-21
 * @Project : com.abdelrahman.photoweatherapp
 */
public class HistoryFragment extends Fragment implements View.OnClickListener, PermissionFragment.PermissionListener, OnHistoryItemClick {
    private static final int CAMERA_REQUEST = 989;
    private final DomainErrorChain domainErrorChain = DomainErrorChain.Builder()
            .buildWithDefaultChainLinks();
    private FragmentHistoryBinding binding;
    private HistoryViewModel viewModel;

    private final PermissionFragment permissionFragment = PermissionFragment.newInstance(
            new PermissionItem(Manifest.permission.CAMERA, R.string.camera, R.string.cameraRationale)
    );

    private final Observer<PresentationResource<List<WeatherPhoto>>> roomWeatherPhotosObserver = photos -> {
        switch (photos.getStatus()) {
            case LOADING:
                showProgressbar();
                break;
            case DOMAIN_ERROR:
                domainErrorChain.execute(photos.getThrowable(), this, () -> viewModel.getWeatherPhotoList());
                break;
            case SUCCESS:
                setUpWeatherPhotosRecyclerView(photos.getData());
                break;
        }
        hideProgressBar();
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManagerUtils.addFragment(getChildFragmentManager(),
                permissionFragment);
        viewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        binding.fabOpenCamera.setOnClickListener(this);
        observeWeatherPhotos();
        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == binding.fabOpenCamera.getId()) {
            if (permissionFragment.hasAllPermissions()) {
                OpenCamera();
            } else {
                permissionFragment.requestPermissions();
            }
        }
    }


    private void observeWeatherPhotos() {
        viewModel.getWeatherPhotoList().observe(getViewLifecycleOwner(), roomWeatherPhotosObserver);
    }

    private void showProgressbar() {
        binding.progressbar.setVisibility(View.VISIBLE);
    }

    private void setUpWeatherPhotosRecyclerView(List<WeatherPhoto> weatherPhotos) {
        binding.txtNoData.setVisibility(weatherPhotos == null || weatherPhotos.isEmpty() ? View.VISIBLE : View.GONE);
        HistoryAdapter adapter = new HistoryAdapter(weatherPhotos, this);
        hideProgressBar();
        binding.rvWeatherPhotos.setAdapter(adapter);
    }


    private void hideProgressBar() {
        binding.progressbar.setVisibility(View.GONE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            if (ImageUtils.getImagePath() != null)
                navController().navigate(HistoryFragmentDirections.actionFromHistoryToPhotoCameraFragment(ImageUtils.getImagePath()));
        }
    }

    @Override
    public void onGranted(List<PermissionItem> granted) {
        OpenCamera();
    }

    private void OpenCamera() {
        ImageUtils.dispatchTakePictureIntent(this, CAMERA_REQUEST);
    }

    @Override
    public void onDeny(List<PermissionItem> denied) {
        PermissionItem currentDeniedPermission = denied.get(0);
        showSnackBar(view -> {
            // Build intent that displays the App settings screen.
            Intent intent = new Intent();
            intent.setAction(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package",
                    BuildConfig.APPLICATION_ID, null);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }, currentDeniedPermission.getRationaleRes());
    }

    private void showSnackBar(View.OnClickListener action, int rationaleRes) {
        DynamicSnackBar.make(getView(), getString(rationaleRes), Snackbar.LENGTH_LONG)
                .setAction(R.string.settings, action).show();
    }

    private NavController navController() {
        return NavHostFragment.findNavController(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void OnHistoryItemClick(WeatherPhoto weatherPhoto) {
        navController().navigate(
                HistoryFragmentDirections.actionHistoryFragmentToPhotoViewFragment(weatherPhoto.getName(), weatherPhoto.getPhotoPath())
        );
    }
}
