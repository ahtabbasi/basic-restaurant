package com.abbasi.data.local.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.abbasi.data.local.DBHelper
import com.abbasi.data.FakeDataUtil
import com.abbasi.data.local.BasicRestaurantDatabase
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
class ProductDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: BasicRestaurantDatabase
    private lateinit var productDao: ProductDao


    @Before
    fun setup() {
        db = DBHelper.getInMemoryDb()
        productDao = db.productDao()
    }


    @Test
    fun getAndInsert() = runBlockingTest {

        val inputData = FakeDataUtil.Local.product1
        productDao.insert(listOf(inputData))

        val job = launch {

            productDao.get(
                inputData.id, inputData.categoryId
            ).test {

                val outputData = awaitItem()
                Truth.assertThat(outputData.id).isEqualTo(inputData.id)
                Truth.assertThat(outputData.categoryId).isEqualTo(inputData.categoryId)
            }
        }
        job.cancel()
    }

    @Test
    fun getByCategoryId() = runBlockingTest {

        val inputProducts = FakeDataUtil.Local.getAllFakeProductEntites()
        productDao.insert(inputProducts)

        val job = launch {

            productDao.getByCategoryId("1").test {

                val output = awaitItem()
                Truth.assertThat(output).hasSize(2)
            }
        }
        job.cancel()
    }

    @Test
    fun deleteAll() = runBlockingTest {

        val inputData = FakeDataUtil.Local.product1
        productDao.insert(listOf(inputData))
        productDao.deleteAll()

        val job = launch {

            productDao.get(inputData.id, inputData.categoryId).test {

                val categories = awaitItem()
                Truth.assertThat(categories).isEqualTo(null)
            }
        }
        job.cancel()
    }


    @After
    fun teardown() {
        db.close()
    }
}