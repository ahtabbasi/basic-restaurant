package com.abbasi.domain.repository

import com.abbasi.domain.models.Category
import com.abbasi.domain.models.Resource
import kotlinx.coroutines.flow.StateFlow

class CategoryRepositoryImpl : CategoryRepository {

    override suspend fun getAll(): StateFlow<Resource<List<Category>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFilteredProducts(query: String): StateFlow<Resource<List<Category>>> {
        TODO("Not yet implemented")
    }

    override suspend fun cleanAllDataAndRepopulate(categoriesWithProducts: List<Category>) {
        TODO("Not yet implemented")
    }
}