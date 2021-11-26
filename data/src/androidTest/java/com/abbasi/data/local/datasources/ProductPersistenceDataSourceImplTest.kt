package com.abbasi.data.local.datasources

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.abbasi.data.local.DBHelper
import com.abbasi.data.FakeDataUtil
import com.abbasi.data.local.BasicRestaurantDatabase
import com.abbasi.data.local.daos.ProductDao
import com.google.common.truth.Truth
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
class ProductPersistenceDataSourceImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var productPDS: ProductPersistenceDataSourceImpl
    private lateinit var db: BasicRestaurantDatabase
    private lateinit var productDao: ProductDao

    @Before
    fun setup() {
        db = DBHelper.getInMemoryDb()
        productDao = db.productDao()
        productPDS = ProductPersistenceDataSourceImpl(productDao)
    }


    @Test
    fun get() = runBlockingTest {

        val input = FakeDataUtil.Domain.product1
        val inputProducts = listOf(input)
        productPDS.saveAll(inputProducts)

        val job = launch {
            productDao.get(input.id, input.categoryId).test {
                val output = awaitItem()

                Truth.assertThat(output.id).isEqualTo(input.id)
                Truth.assertThat(output.categoryId).isEqualTo(input.categoryId)
            }
        }
        job.cancel()

    }


    @Test
    fun getAndSaveAll() = runBlockingTest {

        val input = FakeDataUtil.Domain.product1
        val inputProducts = listOf(input)
        productPDS.saveAll(inputProducts)

        val job = launch {
            productDao.get(input.id, input.categoryId).test {
                val output = awaitItem()

                Truth.assertThat(output.id).isEqualTo(input.id)
                Truth.assertThat(output.categoryId).isEqualTo(input.categoryId)
            }
        }
        job.cancel()

    }


    @Test
    fun deleteAll() = runBlockingTest {

        val inputData = FakeDataUtil.Local.getAllFakeProductEntites()
        productDao.insert(inputData)
        productPDS.deleteAll()

        val job = launch {

            productDao.getByCategoryId(inputData.first().categoryId).test {

                val output = awaitItem()
                Truth.assertThat(output).hasSize(0)
            }
        }
        job.cancel()
    }


    @Test
    fun getByCategoryId() = runBlockingTest {

        val input = FakeDataUtil.Local.getAllFakeProductEntites()
        productDao.insert(input)

        val job = launch {
            productPDS.getByCategoryId("1").test {
                val output = awaitItem()

                Truth.assertThat(output).hasSize(2)
            }
        }
        job.cancel()

    }


    @After
    fun teardown() {
        db.close()
    }
}