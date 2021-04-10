package com.abdelrahman.photoweatherapp.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abdelrahman.photoweatherapp.room.model.WeatherPhoto;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 09-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.room
 */
@Dao
public interface WeatherPhotoDao {
    @Insert
    Completable insertWeatherPhoto(WeatherPhoto weatherPhoto);

    @Query("SELECT * FROM WEATHER_PHOTO_TABLE")
    Observable<List<WeatherPhoto>> getWeatherPhotos();
}
