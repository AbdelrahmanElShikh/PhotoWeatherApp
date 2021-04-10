package com.abdelrahman.photoweatherapp.ui.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.abdelrahman.photoweatherapp.R;



/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.ui.photo
 */
public class PhotoViewFragment extends BasePhotoFragment{
    private String imagePath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePath = PhotoViewFragmentArgs.fromBundle(getArguments()).getImagePath();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.abdelrahman.photoweatherapp.databinding.FragmentPhotoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo, container, false);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        binding.img.setImageBitmap(bitmap);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    String getImagePath() {
        return imagePath;
    }

    @Override
    boolean allowShare() {
        return true;
    }
}
