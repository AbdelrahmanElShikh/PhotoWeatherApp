package com.abdelrahman.core.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.core.data
 */
class RetrofitBuilder {
    companion object {
        private val BASE_URL = "https://api.openweathermap.org"
        private const val API_KEY = "APPID"

        fun getOkHttpClient(): OkHttpClient {
            val httpClient = OkHttpClient.Builder()
            httpClient.networkInterceptors().add(Interceptor { chain ->
                val original = chain.request()
                val httpUrl = original.url
                val url = httpUrl.newBuilder()
                        .addQueryParameter(API_KEY, "a4f64f561f948a4e590fc09ff8550246")
                        .build()
                val requestBuilder = original.newBuilder().url(url)
                requestBuilder.header("Accept", "application/json")
                chain.proceed(requestBuilder.build())
            })
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logger)
                    .build()

            return httpClient.build()
        }

        fun getRetrofit(): Retrofit.Builder {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }


        fun getApiService(): ApiService = getRetrofit()
                .build()
                .create(ApiService::class.java)
    }

}