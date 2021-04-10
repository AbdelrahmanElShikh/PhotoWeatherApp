package com.abdelrahman.core.data

import com.abdelrahman.core.models.WeatherData
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.core.data
 */
interface ApiService {
    @GET("data/2.5/weather")
    fun getWeatherData(@Query("lat") latitude: Double, @Query("lon") longitude: Double):
            Single<Response<WeatherData>>
}