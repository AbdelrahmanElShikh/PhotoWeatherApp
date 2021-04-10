package com.abdelrahman.core.repositories

import com.abdelrahman.core.data.ApiService
import com.abdelrahman.core.data.RetrofitBuilder
import com.abdelrahman.core.datasource.WeatherDataSource
import com.abdelrahman.core.mapper.RemoteResourceMapper
import com.abdelrahman.core.models.WeatherData
import com.abdelrahman.core.resource.PresentationResource
import io.reactivex.Observable

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.core.repositories
 */
class WeatherDataRepository : WeatherDataSource {
    var weatherDataMapper = RemoteResourceMapper<WeatherData>()
    var apiService: ApiService = RetrofitBuilder.getApiService()

    override fun getWeatherDat(latitude: Double, longitude: Double): Observable<PresentationResource<WeatherData>> {
        return apiService.getWeatherData(latitude, longitude).map { weatherDataMapper.map(it) }.toObservable()
    }
}