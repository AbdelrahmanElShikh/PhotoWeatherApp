package com.abdelrahman.core.resource

import com.abdelrahman.core.models.ApiError

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.core.resource
 */
class PresentationResource<T>(
        val status: Status,
        val data: T?,
        val apiError: ApiError?,
        val throwable: Throwable?
) {
    constructor(
            status: Status,
            data: T?,
            errorMsg: String?,
            responseCode: Int,
            errorExtras: Map<String, Any>?,
            throwable: Throwable?
    ) : this(status, data,
            ApiError(
                    errorMsg,
                    responseCode,
                    errorExtras
            ), throwable)

    companion object {
        fun <T> success(data: T): PresentationResource<T> {
            return PresentationResource(
                    Status.SUCCESS,
                    data,
                    null,
                    0,
                    null,
                    null
            )
        }

        fun <T> apiError(errorMsg: String?, responseCode: Int, errorExtras: Map<String, Any>?): PresentationResource<T> {
            return PresentationResource(
                    Status.API_ERROR,
                    null,
                    errorMsg,
                    responseCode,
                    errorExtras,
                    null
            )
        }

        fun <T> domainError(throwable: Throwable?): PresentationResource<T> {
            return PresentationResource(
                    Status.DOMAIN_ERROR,
                    null,
                    null,
                    0,
                    null,
                    throwable
            )
        }

        fun <T> loading(): PresentationResource<T> {
            return PresentationResource(
                    Status.LOADING,
                    null,
                    null,
                    0,
                    null,
                    null
            )
        }
    }

    enum class Status {
        SUCCESS, DOMAIN_ERROR, API_ERROR, LOADING
    }
}