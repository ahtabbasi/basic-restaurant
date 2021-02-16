package com.abbasi.data.remote

import com.abbasi.data.BuildConfig
import com.abbasi.data.remote.api.ApiClient
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit

@ExperimentalSerializationApi
object NetworkHelper {


    // tODO: Manage this class with Hilt
    fun getMockApiClient(mockServer: MockWebServer): ApiClient {
        return getApiClient(
            getRetrofit(
                getOkHttpClient(), mockServer
            )
        )
    }


    private fun getOkHttpClient() =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()


    private fun getRetrofit(okHttpClient: OkHttpClient, mockServer: MockWebServer): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(mockServer.url(BuildConfig.BASE_URL))
            .client(okHttpClient)
            .addConverterFactory(Json {
                isLenient = true
                ignoreUnknownKeys = true
            }.asConverterFactory(contentType))
            .build()
    }

    private fun getApiClient(retrofit: Retrofit): ApiClient =
        retrofit.create(ApiClient::class.java)
}
