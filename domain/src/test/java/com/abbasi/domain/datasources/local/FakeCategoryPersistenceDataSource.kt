package com.abbasi.domain.datasources.local

import com.abbasi.domain.models.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.coroutineContext

class FakeCategoryPersistenceDataSource : CategoryPersistenceDataSource {

    // map uses id as key to avoid duplication
    private val storedCategories = mutableMapOf<String, Category>()

    override suspend fun getAllWithProducts(): StateFlow<List<Category>> = flow {
        emit(storedCategories.map { it.value })
    }.stateIn(
        CoroutineScope(coroutineContext)
    )

    override suspend fun saveAll(categories: List<Category>) {
        categories.forEach { storedCategories[it.id] = it }
    }

    override suspend fun getFilteredProducts(query: String): StateFlow<List<Category>> = flow {
        val categoriesFound = mutableListOf<Category>()
        storedCategories.forEach { category ->
            val productsFound = category.value.products.filter { it.name.contains(query) }
            if (productsFound.isNotEmpty())
                categoriesFound.add(category.value.copy(products = productsFound))
        }
        emit(categoriesFound)
    }.stateIn(
        CoroutineScope(coroutineContext)
    )

    override suspend fun deleteAll() {
        storedCategories.clear()
    }


    fun fake_getAllWithProducts(): List<Category> = storedCategories.map { it.value }
}