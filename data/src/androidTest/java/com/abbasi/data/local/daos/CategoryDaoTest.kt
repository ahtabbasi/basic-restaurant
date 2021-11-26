package com.abbasi.data.local.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.abbasi.data.FakeDataUtil
import com.abbasi.data.local.BasicRestaurantDatabase
import com.abbasi.data.local.DBHelper
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
class CategoryDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var db: BasicRestaurantDatabase
    private lateinit var categoryDao: CategoryDao
    private lateinit var productDao: ProductDao

    @Before
    fun setup() {
        db = DBHelper.getInMemoryDb()
        categoryDao = db.categoryDao()
        productDao = db.productDao()
    }

    @Test
    fun insert() = runBlockingTest {

        val inputData = FakeDataUtil.Local.getAllFakeCategoryEntities()
        categoryDao.insert(inputData)

        val job = launch {

            categoryDao.getAllCategoriesWithProducts().test {

                val outputData = awaitItem().map { it.category }
                assertThat(outputData).hasSize(inputData.size)
                assertThat(outputData).isEqualTo(inputData)
            }
        }
        job.cancel()
    }

    @Test
    fun getAllCategoriesWithProducts() = runBlockingTest {

        val inputCategories = FakeDataUtil.Local.getAllFakeCategoryEntities()
        val inputProducts = FakeDataUtil.Local.getAllFakeProductEntites()

        categoryDao.insert(inputCategories)
        productDao.insert(inputProducts)

        val job = launch {

            categoryDao.getAllCategoriesWithProducts().test {

                val item = awaitItem()
                val outputCategories = item.map { it.category }
                assertThat(outputCategories).hasSize(inputCategories.size)
                assertThat(outputCategories).isEqualTo(inputCategories)

                val outputProducts = item.flatMap { it.products }
                assertThat(outputProducts).hasSize(inputProducts.size)
                assertThat(outputProducts).isEqualTo(inputProducts)
            }
        }
        job.cancel()
    }

    @Test
    fun getFilteredProducts() = runBlockingTest {

        val inputCategories = FakeDataUtil.Local.getAllFakeCategoryEntities()
        val inputProducts = FakeDataUtil.Local.getAllFakeProductEntites()
        val inputSearchQuery = "dd"

        categoryDao.insert(inputCategories)
        productDao.insert(inputProducts)

        val job = launch {

            categoryDao.getFilteredProducts(inputSearchQuery).test {

                val outputProducts = awaitItem().flatMap { it.products }
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
        categoryDao.deleteAll()

        val job = launch {

            categoryDao.getAllCategoriesWithProducts().test {

                val categories = awaitItem().map { it.category }
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