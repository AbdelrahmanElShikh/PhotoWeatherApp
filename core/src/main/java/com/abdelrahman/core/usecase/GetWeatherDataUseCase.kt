package com.abdelrahman.core.usecase

import com.abdelrahman.core.models.WeatherData
import com.abdelrahman.core.repositories.WeatherDataRepository
import com.abdelrahman.core.resource.PresentationResource
import io.reactivex.Observable

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.core.usecase
 */
class GetWeatherDataUseCase {
    private val weatherDataRepository: WeatherDataRepository = WeatherDataRepository()


    fun getWeatherData(latitude: Double, longitude: Double): Observable<PresentationResource<WeatherData>> {
        return weatherDataRepository.getWeatherDat(latitude, longitude)
    }
}