package com.abbasi.domain.repository

import com.abbasi.domain.models.Category
import com.abbasi.domain.models.Resource
import com.abbasi.domain.repository.util.CachedDataAccessStrategy
import kotlinx.coroutines.flow.StateFlow

interface CategoryRepository {

    suspend fun getAllWithProducts(dataStrategy: CachedDataAccessStrategy): StateFlow<Resource<List<Category>>>

    suspend fun cleanAllDataAndRepopulate(categoriesWithProducts: List<Category>)
}