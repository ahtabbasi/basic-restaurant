package com.abbasi.data.local.datasources

import com.abbasi.data.local.daos.ProductDao
import com.abbasi.data.local.models.fromDomain
import com.abbasi.data.local.models.toDomainModel
import com.abbasi.domain.datasources.local.ProductPersistenceDataSource
import com.abbasi.domain.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class ProductPersistenceDataSourceImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductPersistenceDataSource {

    override suspend fun get(productId: String, categoryId: String) =
        productDao.get(productId, categoryId).map {
            // returns null if no such record found
            it.toDomainModel()
        }.stateIn(
            CoroutineScope(coroutineContext)
        )


    override suspend fun saveAll(products: List<Product>) {
        productDao.insert(products.fromDomain())
    }

    override suspend fun deleteAll() = productDao.deleteAll()

    override suspend fun getByCategoryId(categoryId: String) =
        productDao.getByCategoryId(categoryId).map {
            it.toDomainModel()
        }.stateIn(
            CoroutineScope(coroutineContext)
        )

}