package com.abdelrahman.core.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.core.models
 */
data class WeatherData (
    @SerializedName("weather") @Expose
    val weather: List<Weather>,
    @SerializedName("sys") @Expose
    val sys: Sys,
    @SerializedName("name") @Expose
    val name: String,
    @SerializedName("main") @Expose
    val main: Main

)