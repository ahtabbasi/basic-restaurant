package com.abbasi.domain.repository

import com.abbasi.domain.models.Category
import com.abbasi.domain.models.Resource
import kotlinx.coroutines.flow.StateFlow

interface CategoryRepository {

    suspend fun getAll(): StateFlow<Resource<List<Category>>>

    suspend fun getFilteredProducts(query: String): StateFlow<Resource<List<Category>>>

    suspend fun cleanAllDataAndRepopulate(categoriesWithProducts: List<Category>)
}