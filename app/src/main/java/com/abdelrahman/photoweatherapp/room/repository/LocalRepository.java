package com.abdelrahman.photoweatherapp.room.repository;

import android.content.Context;

import com.abdelrahman.photoweatherapp.room.model.WeatherPhoto;
import com.abdelrahman.photoweatherapp.room.WeatherPhotoDao;
import com.abdelrahman.photoweatherapp.room.WeatherPhotoDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 09-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.ui.history.adapter
 */
public class LocalRepository {
    private final WeatherPhotoDao weatherPhotoDao;

    public LocalRepository(Context context) {
        WeatherPhotoDatabase weatherPhotoDatabase = WeatherPhotoDatabase.getInstance(context);
        weatherPhotoDao = weatherPhotoDatabase.getWeatherPhotoDao();
    }

    public Observable<List<WeatherPhoto>> getWeatherPhotos() {
        return weatherPhotoDao.getWeatherPhotos();
    }

    public Completable insertPhoto(WeatherPhoto weatherPhoto){
        return weatherPhotoDao.insertWeatherPhoto(weatherPhoto);
    }
}
