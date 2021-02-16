package com.abbasi.domain.datasources.local

import com.abbasi.domain.models.Category
import kotlinx.coroutines.flow.StateFlow

interface CategoryPersistenceDataSource {

    /**
     *  Returns a list of all the categories with their products
     */
    suspend fun getAllWithProducts(): StateFlow<List<Category>>

    suspend fun saveAll(categories: List<Category>)

    /**
     *  Returns a list of all the categories with all of their products whose
     *  name contains the query string
     */
    suspend fun getFilteredProducts(query: String): StateFlow<List<Category>>

    suspend fun deleteAll()

}