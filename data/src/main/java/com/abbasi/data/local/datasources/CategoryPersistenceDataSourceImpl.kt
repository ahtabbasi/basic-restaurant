package com.abbasi.data.local.datasources

import com.abbasi.data.local.daos.CategoryDao
import com.abbasi.domain.datasources.local.CategoryPersistenceDataSource
import com.abbasi.domain.models.Category
import kotlinx.coroutines.flow.StateFlow

class CategoryPersistenceDataSourceImpl(
    private val categoryDao: CategoryDao
) : CategoryPersistenceDataSource {
    override suspend fun getAllWithProducts(): StateFlow<List<Category>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(categories: List<Category>) {
        TODO("Not yet implemented")
    }

    override suspend fun getFilteredProducts(query: String): StateFlow<List<Category>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }


}