package com.abbasi.domain.datasources.local

import com.abbasi.domain.models.Product
import kotlinx.coroutines.flow.StateFlow

interface ProductPersistenceDataSource {

    suspend fun get(productId: String, categoryId: String): StateFlow<Product>

    suspend fun saveAll(products: List<Product>)

    suspend fun deleteAll()

    suspend fun getByCategoryId(categoryId: String): StateFlow<List<Product>>

}