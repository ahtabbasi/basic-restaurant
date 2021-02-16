package com.abbasi.data.local.datasources

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.abbasi.data.FakeDataUtil
import com.abbasi.data.local.BasicRestaurantDatabase
import com.abbasi.data.local.DBHelper
import com.abbasi.data.local.daos.CategoryDao
import com.abbasi.data.local.daos.ProductDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class CategoryPersistenceDataSourceImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var categoryPDS: CategoryPersistenceDataSourceImpl
    private lateinit var db: BasicRestaurantDatabase
    private lateinit var categoryDao: CategoryDao
    private lateinit var productDao: ProductDao


    @Before
    fun setup() {
        db = DBHelper.getInMemoryDb()
        categoryDao = db.categoryDao()
        productDao = db.productDao()
        categoryPDS = CategoryPersistenceDataSourceImpl(categoryDao)
    }


    @Test
    fun getAllWithProducts() = runBlockingTest {

        val inputCategories = FakeDataUtil.Local.getAllFakeCategoryEntities()
        val inputProducts = FakeDataUtil.Local.getAllFakeProductEntites()

        categoryDao.insert(inputCategories)
        productDao.insert(inputProducts)

        val job = launch {

            categoryPDS.getAllWithProducts().test {

                val outputCategories = expectItem()
                assertThat(outputCategories).hasSize(inputCategories.size)
                assertThat(outputCategories.first().id).isEqualTo(inputCategories.first().id)
                assertThat(outputCategories.last().id).isEqualTo(inputCategories.last().id)

                val outputProducts = outputCategories.flatMap { it.products }
                assertThat(outputProducts).hasSize(inputProducts.size)
                assertThat(outputProducts.first().id).isEqualTo(inputProducts.first().id)
                assertThat(outputProducts.last().id).isEqualTo(inputProducts.last().id)
            }
        }
        job.cancel()
    }


    @Test
    fun saveAll() = runBlockingTest {

        val inputCategories = FakeDataUtil.Domain.getAllFakeCategories()
        categoryPDS.saveAll(inputCategories)

        val job = launch {
            categoryDao.getAllCategoriesWithProducts().test {
                val output = expectItem()
                val outputCategories = output.map { it.category }
                assertThat(outputCategories).hasSize(inputCategories.size)
                assertThat(outputCategories.first().id).isEqualTo(inputCategories.first().id)
                assertThat(outputCategories.last().id).isEqualTo(inputCategories.last().id)
            }
        }
        job.cancel()

    }


    @Test
    fun getFilteredProducts() = runBlockingTest {

        val inputCategories = FakeDataUtil.Local.getAllFakeCategoryEntities()
        val inputProducts = FakeDataUtil.Local.getAllFakeProductEntites()

        categoryDao.insert(inputCategories)
        productDao.insert(inputProducts)
        val inputSearchQuery = "dd"

        val job = launch {

            categoryPDS.getFilteredProducts(inputSearchQuery).test {

                val outputProducts = expectItem().flatMap { it.products }
                assertThat(!outputProducts.any {
                    !it.name.contains("dd")
                })
            }
        }
        job.cancel()
    }


    @Test
    fun deleteAll() = runBlockingTest {

        val inputData = FakeDataUtil.Local.getAllFakeCategoryEntities()
        categoryDao.insert(inputData)
        categoryPDS.deleteAll()

        val job = launch {

            categoryDao.getAllCategoriesWithProducts().test {

                val categories = expectItem().map { it.category }
                assertThat(categories).hasSize(0)
            }
        }
        job.cancel()
    }


    @After
    fun teardown() {
        db.close()
    }
}