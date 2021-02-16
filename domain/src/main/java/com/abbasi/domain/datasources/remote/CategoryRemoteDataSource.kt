package com.abbasi.domain.datasources.remote

import com.abbasi.domain.models.Category
import com.abbasi.domain.models.Resource
import kotlinx.coroutines.flow.Flow


interface CategoryRemoteDataSource {

    /**
     * Returns all the data from server
     */
    fun getAll(): Flow<Resource<List<Category>>>
}