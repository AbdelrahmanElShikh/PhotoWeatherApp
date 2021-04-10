package com.abdelrahman.core.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.core.models
 */
class ApiError(
        @SerializedName("message") @Expose val apiErrorMessage : String?,
        @SerializedName("cod") @Expose val apiErrorCode : Int?,
        val extras : Map<String,Any>?
)