package com.abbasi.domain.datasources.local

import com.abbasi.domain.models.Category
import kotlinx.coroutines.flow.StateFlow

interface CategoryPersistenceDataSource {

    suspend fun getAllWithProducts(): StateFlow<List<Category>>

    suspend fun saveAll(categories: List<Category>)

    suspend fun getFilteredProducts(query: String): StateFlow<List<Category>>

    suspend fun deleteAll()

}