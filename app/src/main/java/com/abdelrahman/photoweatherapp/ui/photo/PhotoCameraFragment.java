package com.abdelrahman.photoweatherapp.ui.photo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.abdelrahman.core.models.WeatherData;
import com.abdelrahman.core.resource.PresentationResource;
import com.abdelrahman.photoweatherapp.BuildConfig;
import com.abdelrahman.photoweatherapp.R;
import com.abdelrahman.photoweatherapp.databinding.FragmentPhotoBinding;
import com.abdelrahman.photoweatherapp.room.model.WeatherPhoto;
import com.abdelrahman.photoweatherapp.ui.photo.viewmodel.WeatherDataViewModel;
import com.abdelrahman.photoweatherapp.utils.ImageUtils;
import com.abdelrahman.photoweatherapp.utils.errorchains.domain.chains.DomainErrorChain;
import com.google.android.material.snackbar.Snackbar;


/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 09-Apr-21
 * @Project : com.abdelrahman.photoweatherapp
 */
public class PhotoCameraFragment extends BasePhotoFragment implements LocationListener {
    private static final String TAG = "PhotoFragment";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private FragmentPhotoBinding binding;
    private String imagePath;
    private LocationManager locationManager;
    private WeatherDataViewModel viewModel;
    private Location userLocation;
    private boolean allowShare;
    private final DomainErrorChain domainErrorChain = DomainErrorChain.Builder()
            .buildWithDefaultChainLinks();
    private Snackbar snackbar;
    private final Observer<PresentationResource<WeatherData>> remoteWeatherDataObserver = (Observer<PresentationResource<WeatherData>>) weatherData -> {
        switch (weatherData.getStatus()) {
            case LOADING:
                showProgressbar();
                break;
            case SUCCESS:
                handleOverLayWithData(weatherData.getData());
                break;
            case API_ERROR:
                Snackbar.make(getView(), weatherData.getApiError().getApiErrorMessage(), Snackbar.LENGTH_SHORT).show();
                break;
            case DOMAIN_ERROR:
                domainErrorChain.execute(weatherData.getThrowable(), this, () -> viewModel.getWeatherData(userLocation.getLatitude(), userLocation.getLongitude()));
        }
        hideProgressBar();
    };

    private void showProgressbar() {
        binding.progressbar.setVisibility(View.VISIBLE);
    }

    private final Observer<PresentationResource<Boolean>> roomInsertObserver = isPhotoSaved -> {
        switch (isPhotoSaved.getStatus()) {
            case LOADING:
                showProgressbar();
                break;
            case DOMAIN_ERROR:
                domainErrorChain.execute(isPhotoSaved.getThrowable(), this,
                        () -> viewModel.insertPhoto(new WeatherPhoto(ImageUtils.getImagePath(), ImageUtils.getFile().getAbsolutePath())));
                break;
            case SUCCESS:
                //sharing is disabled till the image with the overlay is processed and saved to room db.
                allowShare = true;
                Log.d(TAG, "Imaged Saved TO Room Successfully: ");
                break;
        }
        hideProgressBar();
    };

    private void handleOverLayWithData(WeatherData weatherData) {
        String location = weatherData.getSys().getCountry() + " , " + weatherData.getName();
        String temp = weatherData.getMain().getTemp() + " \u212A";
        binding.overlay.setVisibility(View.VISIBLE);
        binding.txtLocation.setText(location);
        binding.txtDesc.setText(weatherData.getWeather().get(0).getDescription());
        binding.txtTemp.setText(temp);
        binding.layoutWeatherData.setVisibility(View.VISIBLE);
        handleImageInRoom();
    }

    private void handleImageInRoom() {
        binding.container.post(() -> {
            ImageUtils.saveImageIntoTempFile(getActivity(), binding.container);
            saveImagePathIntoRoom();
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        imagePath = PhotoCameraFragmentArgs.fromBundle(getArguments()).getImagePath();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo, container, false);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        binding.img.setImageBitmap(bitmap);
        ImageUtils.deleteImageFromStorage();
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        showProgressbar();
        checkPermission();
    }

    private void hideProgressBar() {
        binding.progressbar.setVisibility(View.GONE);
    }

    private void saveImagePathIntoRoom() {
        viewModel.insertPhoto(new WeatherPhoto(ImageUtils.getImagePath(), ImageUtils.getFile().getAbsolutePath())).observe(getViewLifecycleOwner(), roomInsertObserver);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    String getImagePath() {
        return imagePath;
    }

    @Override
    boolean allowShare() {
        return allowShare;
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        locationManager.removeUpdates(this);
        userLocation = location;
        viewModel.getWeatherData(location.getLatitude(), location.getLongitude()).observe(this, remoteWeatherDataObserver);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
        } else {
            requestLocationPermission();
        }
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            handleLocationPermissionDeny();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
            } else {
                handleLocationPermissionDeny();
            }
        }

    }

    void handleLocationPermissionDeny() {
        hideProgressBar();
        snackbar = Snackbar.make(binding.container, "Location permission is required", Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.settings, view -> {
                    Intent intent = new Intent();
                    intent.setAction(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package",
                            BuildConfig.APPLICATION_ID, null);
                    intent.setData(uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
        snackbar.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(snackbar != null ){
            snackbar.dismiss();
        }
    }
}
