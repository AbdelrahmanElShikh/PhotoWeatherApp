package com.abdelrahman.core.mapper

import com.abdelrahman.core.resource.PresentationResource
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Response

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.core
 */
class RemoteResourceMapper<T> {
    fun map(response: Response<T>): PresentationResource<T> {
        return when (response.code()) {
            200 -> PresentationResource.success(response.body()!!)
            401, 404, 500 ->{
                val jsonObject = JSONObject(response.errorBody()!!.string().trim())
                PresentationResource.apiError(
                        jsonObject.getString("message"),
                        response.code(),
                        null
                )
            }
            else -> {
                val errorResponse: ErrorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), ErrorResponse::class.java)
                return PresentationResource.apiError(
                        errorResponse.errorMsg,
                        response.code(),
                        errorResponse.extras
                )
            }
        }
    }


    fun mapList(response: Response<List<T>>): PresentationResource<List<T>> {
        return when (response.code()) {
            200 -> PresentationResource.success(response.body()!!)
            401, 429, 500 -> PresentationResource.apiError(
                    response.message(),
                    response.code(),
                    null
            )
            else -> {
                val errorResponse: ErrorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), ErrorResponse::class.java)
                return PresentationResource.apiError(
                        errorResponse.errorMsg,
                        response.code(),
                        errorResponse.extras
                )
            }
        }
    }

}