package com.abdelrahman.core.datasource

import com.abdelrahman.core.models.WeatherData
import com.abdelrahman.core.resource.PresentationResource
import io.reactivex.Observable

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.core.datasource
 */
interface WeatherDataSource {
    fun getWeatherDat(latitude: Double, longitude: Double): Observable<PresentationResource<WeatherData>>
}