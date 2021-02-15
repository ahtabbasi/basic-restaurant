package com.abbasi.domain.repository

import com.abbasi.domain.datasources.local.ProductPersistenceDataSource
import com.abbasi.domain.models.Product
import com.abbasi.domain.models.Resource
import kotlinx.coroutines.flow.StateFlow

class ProductRepositoryImpl(
    private val productPDS: ProductPersistenceDataSource
) : ProductRepository {
    override suspend fun get(productId: String, categoryId: String): StateFlow<Resource<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getByCategoryId(categoryId: String): StateFlow<Resource<List<Product>>> {
        TODO("Not yet implemented")
    }
}