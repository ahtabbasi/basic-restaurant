package com.abbasi.domain.repository

import com.abbasi.domain.datasources.local.ProductPersistenceDataSource
import com.abbasi.domain.models.Product
import com.abbasi.domain.models.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.coroutineContext

class ProductRepositoryImpl(
    private val productPDS: ProductPersistenceDataSource
) : ProductRepository {

    override suspend fun get(productId: String, categoryId: String) = flow<Resource<Product>> {
        emit(Resource.Loading())
        try {
            emitAll(productPDS.get(productId, categoryId).map { Resource.Valid(it) })
        } catch (e: Exception) {
            emit(Resource.Invalid("Unable to find the products"))
        }
    }.stateIn(
        CoroutineScope(coroutineContext)
    )

    override suspend fun getByCategoryId(categoryId: String) = flow {
        emit(Resource.Loading())
        emitAll(productPDS.getByCategoryId(categoryId).map { Resource.Valid(it) })
    }.stateIn(
        CoroutineScope(coroutineContext)
    )
}