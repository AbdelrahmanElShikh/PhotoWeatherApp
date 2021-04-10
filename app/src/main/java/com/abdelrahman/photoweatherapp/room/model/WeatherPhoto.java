package com.abdelrahman.photoweatherapp.room.model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.abdelrahman.photoweatherapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 09-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.model
 */

@Entity(tableName = "weather_photo_table")
public class WeatherPhoto {
    @PrimaryKey
    @NonNull
    String photoPath;
    String name;

    public WeatherPhoto(String name, @NonNull String photoPath) {
        this.name = name;
        this.photoPath = photoPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoPath(@NonNull String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getName() {
        return name;
    }

    @BindingAdapter("showPhoto")
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get().
                load(new File(imageUrl))
                .error(R.drawable.error)
                .into(view);
    }
}
