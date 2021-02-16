package com.abbasi.data.local.datasources

import com.abbasi.data.local.daos.CategoryDao
import com.abbasi.data.local.models.fromDomain
import com.abbasi.data.local.models.toDomainModel
import com.abbasi.domain.datasources.local.CategoryPersistenceDataSource
import com.abbasi.domain.models.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.coroutineContext

class CategoryPersistenceDataSourceImpl(
    private val categoryDao: CategoryDao
) : CategoryPersistenceDataSource {

    override suspend fun getAllWithProducts() = categoryDao.getAllCategoriesWithProducts()
        .distinctUntilChanged()
        .map {
            it.toDomainModel()
        }.stateIn(
            CoroutineScope(coroutineContext)
        )


    override suspend fun saveAll(categories: List<Category>) =
        categoryDao.insert(categories.fromDomain())


    override suspend fun getFilteredProducts(query: String) =
        categoryDao.getFilteredProducts(query)
            .distinctUntilChanged()
            .map {
                it.toDomainModel()
            }.stateIn(
                CoroutineScope(coroutineContext)
            )

    override suspend fun deleteAll() = categoryDao.deleteAll()

}