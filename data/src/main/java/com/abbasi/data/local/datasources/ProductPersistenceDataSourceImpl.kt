package com.abbasi.data.local.datasources

import com.abbasi.data.local.daos.ProductDao
import com.abbasi.domain.datasources.local.ProductPersistenceDataSource
import com.abbasi.domain.models.Product
import kotlinx.coroutines.flow.StateFlow

class ProductPersistenceDataSourceImpl (
    private val productDao: ProductDao
) : ProductPersistenceDataSource {
    override suspend fun get(productId: String, categoryId: String): StateFlow<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(products: List<Product>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun getByCategoryId(categoryId: String): StateFlow<List<Product>> {
        TODO("Not yet implemented")
    }


}