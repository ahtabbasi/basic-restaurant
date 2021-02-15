package com.abbasi.data.remote.datasources

import com.abbasi.data.remote.api.ApiClient
import com.abbasi.domain.datasources.remote.CategoryRemoteDataSource
import com.abbasi.domain.models.Category
import com.abbasi.domain.models.Resource
import kotlinx.coroutines.flow.Flow

class CategoryRemoteDataSourceImpl(
    private val apiClient: ApiClient
) : CategoryRemoteDataSource, BaseRemoteDataSource() {

    override fun getAll(): Flow<Resource<List<Category>>> {
        TODO("Not yet implemented")
    }

}
