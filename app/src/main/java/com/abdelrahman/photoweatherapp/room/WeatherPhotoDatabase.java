package com.abdelrahman.photoweatherapp.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.abdelrahman.photoweatherapp.room.model.WeatherPhoto;

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 09-Apr-21
 * @Project : com.abdelrahman.photoweatherapp.room
 */
@Database(entities = WeatherPhoto.class, version = 1)
public abstract class WeatherPhotoDatabase extends RoomDatabase {
    private static WeatherPhotoDatabase instance;
    private static final String DATA_BASE_NAME = "weather_database";

    public abstract WeatherPhotoDao getWeatherPhotoDao();

    public static synchronized WeatherPhotoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WeatherPhotoDatabase.class,
                    DATA_BASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
