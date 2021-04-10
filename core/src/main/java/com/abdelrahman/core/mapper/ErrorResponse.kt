package com.abdelrahman.core.mapper

import com.google.gson.annotations.SerializedName

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.core.mapper
 */
data class ErrorResponse(
        @SerializedName(value = "message")
        val errorMsg:String,
        @SerializedName(value= "errors")
        val extras:HashMap<String, Any>
)