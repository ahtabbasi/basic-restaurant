package com.abbasi.data.remote.datasources

import com.abbasi.data.remote.api.ApiClient
import com.abbasi.data.remote.models.toDomainModel
import com.abbasi.domain.datasources.remote.CategoryRemoteDataSource
import com.abbasi.domain.models.transform
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor(
    private val apiClient: ApiClient
) : CategoryRemoteDataSource, BaseRemoteDataSource() {

    override fun getAll() = safeApiCall {
        apiClient.getAllCategories()
    }.map { it ->
        it.transform { it.toDomainModel() }
    }

}
