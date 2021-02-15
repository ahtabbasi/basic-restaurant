package com.abbasi.domain.datasources.local

import com.abbasi.domain.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.coroutineContext

class FakeProductPersistenceDataSource : ProductPersistenceDataSource {

    private val storedProducts = mutableListOf<Product>()

    override suspend fun get(productId: String, categoryId: String) = flow {
        storedProducts.firstOrNull {
            it.id == productId && it.categoryId == categoryId
        }?.let {
            emit(it)
        }
    }.stateIn(
        CoroutineScope(coroutineContext)
    )

    override suspend fun saveAll(products: List<Product>) {
        // to avoid duplicates
        storedProducts.removeIf { old ->
            products.any { new ->
                new.id == old.id && new.categoryId == old.categoryId
            }
        }
        storedProducts.addAll(products)
    }

    override suspend fun deleteAll() {
        storedProducts.clear()
    }

    override suspend fun getByCategoryId(categoryId: String) = flow {
        emit(storedProducts.filter { it.categoryId == categoryId })
    }.stateIn(
        CoroutineScope(coroutineContext)
    )


    fun fake_getAll() = storedProducts
}