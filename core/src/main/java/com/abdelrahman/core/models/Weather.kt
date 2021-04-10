package com.abdelrahman.core.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.core.models
 */
data class Weather(
        @SerializedName("id") @Expose
        val id: Int?,
        @SerializedName("main") @Expose
        val main: String?,
        @SerializedName("description") @Expose
        val description: String?
)