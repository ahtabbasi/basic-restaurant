package com.abbasi.data.remote.api

import com.abbasi.data.remote.models.CategoryDTO
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {

    @GET("/")
    suspend fun getAllCategories(): Response<List<CategoryDTO>>

}