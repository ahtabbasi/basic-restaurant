package com.abbasi.domain.datasources.remote

import com.abbasi.domain.models.Category
import com.abbasi.domain.models.Resource
import kotlinx.coroutines.flow.Flow


interface CategoryRemoteDataSource {
    fun getAll(): Flow<Resource<List<Category>>>
}