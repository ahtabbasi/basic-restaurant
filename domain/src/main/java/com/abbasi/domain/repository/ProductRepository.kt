package com.abbasi.domain.repository

import com.abbasi.domain.models.Product
import com.abbasi.domain.models.Resource
import kotlinx.coroutines.flow.StateFlow

interface ProductRepository {

    suspend fun get(productId: String, categoryId: String): StateFlow<Resource<Product>>

    suspend fun getByCategoryId(categoryId: String): StateFlow<Resource<List<Product>>>

}