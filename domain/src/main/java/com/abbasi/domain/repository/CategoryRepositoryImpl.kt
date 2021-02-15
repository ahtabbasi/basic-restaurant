package com.abbasi.domain.repository

import com.abbasi.domain.datasources.local.CategoryPersistenceDataSource
import com.abbasi.domain.datasources.local.ProductPersistenceDataSource
import com.abbasi.domain.datasources.remote.CategoryRemoteDataSource
import com.abbasi.domain.models.Category
import com.abbasi.domain.models.Resource
import com.abbasi.domain.repository.util.CachedDataAccessStrategy
import kotlinx.coroutines.flow.StateFlow

class CategoryRepositoryImpl(
    private val categoryRDS: CategoryRemoteDataSource,
    private val categoryPDS: CategoryPersistenceDataSource,
    private val productPDS: ProductPersistenceDataSource
) : CategoryRepository {

    override suspend fun getAllWithProducts(dataStrategy: CachedDataAccessStrategy): StateFlow<Resource<List<Category>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFilteredProducts(query: String): StateFlow<Resource<List<Category>>> {
        TODO("Not yet implemented")
    }

    override suspend fun cleanAllDataAndRepopulate(categoriesWithProducts: List<Category>) {
        TODO("Not yet implemented")
    }
}