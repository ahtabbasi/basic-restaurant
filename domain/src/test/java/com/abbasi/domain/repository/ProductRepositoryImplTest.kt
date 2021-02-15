package com.abbasi.domain.repository

import app.cash.turbine.test
import com.abbasi.domain.FakeDataUtil
import com.abbasi.domain.datasources.local.FakeProductPersistenceDataSource
import com.abbasi.domain.models.Resource
import com.abbasi.domain.models.getDataOrNull
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class ProductRepositoryImplTest {


    private lateinit var productRepository: ProductRepositoryImpl
    private lateinit var fakeProductPersistenceDataSource: FakeProductPersistenceDataSource

    @Before
    fun setup() {
        fakeProductPersistenceDataSource = FakeProductPersistenceDataSource()
        productRepository = ProductRepositoryImpl(
            fakeProductPersistenceDataSource
        )
    }

    @Test
    fun GivenInvalidProductIdValidCategoryId_WhenGet_ThenInvalid() =
        runBlockingTest {
            val testProduct = FakeDataUtil.product1
            val products = listOf(testProduct)
            val validCategoryId = testProduct.categoryId
            fakeProductPersistenceDataSource.saveAll(products)

            launch {
                productRepository.get("RANDOM", validCategoryId)
                    .test {

                        var resource = expectItem()

                        // first loading with empty data
                        Truth.assertThat(resource).isInstanceOf(Resource.Loading::class.java)
                        Truth.assertThat(resource.getDataOrNull()).isEqualTo(null)


                        // iterate all till invalid response is received..
                        while (resource !is Resource.Invalid) {
                            resource = expectItem()
                        }

                        Truth.assertThat(resource.getDataOrNull()).isEqualTo(null)

                        // to finish the parent coroutine and kill the stateFlow
                        this@launch.cancel()
                    }
            }
        }

    @Test
    fun GivenValidProductIdInvalidCategoryId_WhenGet_ThenInvalid() =
        runBlockingTest {
            val testProduct = FakeDataUtil.product1
            val products = listOf(testProduct)
            val validProductId = testProduct.id
            fakeProductPersistenceDataSource.saveAll(products)

            launch {
                productRepository.get(validProductId, "RANDOM")
                    .test {

                        var resource = expectItem()

                        // first loading with empty data
                        Truth.assertThat(resource).isInstanceOf(Resource.Loading::class.java)
                        Truth.assertThat(resource.getDataOrNull()).isEqualTo(null)


                        // iterate all till invalid response is received..
                        while (resource !is Resource.Invalid) {
                            resource = expectItem()
                        }

                        Truth.assertThat(resource.getDataOrNull()).isEqualTo(null)

                        // to finish the parent coroutine and kill the stateFlow
                        this@launch.cancel()
                    }
            }
        }

    @Test
    fun GivenInvalidProductIdInvalidCategoryId_WhenGet_ThenInvalid() =
        runBlockingTest {

            launch {
                productRepository.get("RANDOM", "RANDOM")
                    .test {

                        var resource = expectItem()

                        // first loading with empty data
                        Truth.assertThat(resource).isInstanceOf(Resource.Loading::class.java)
                        Truth.assertThat(resource.getDataOrNull()).isEqualTo(null)


                        // iterate all till invalid response is received..
                        while (resource !is Resource.Invalid) {
                            resource = expectItem()
                        }

                        Truth.assertThat(resource.getDataOrNull()).isEqualTo(null)

                        // to finish the parent coroutine and kill the stateFlow
                        this@launch.cancel()
                    }
            }
        }

    @Test
    fun GivenValidProductIdValidCategoryId_WhenGet_ThenValid() =
        runBlockingTest {
            val testProduct = FakeDataUtil.product1
            val products = listOf(testProduct)
            val validProductId = testProduct.id
            val validCategoryId = testProduct.categoryId
            fakeProductPersistenceDataSource.saveAll(products)

            launch {
                productRepository.get(validProductId, validCategoryId)
                    .test {

                        var resource = expectItem()

                        // first loading with empty data
                        Truth.assertThat(resource).isInstanceOf(Resource.Loading::class.java)
                        Truth.assertThat(resource.getDataOrNull()).isEqualTo(null)


                        // iterate all till invalid response is received..
                        while (resource !is Resource.Valid) {
                            resource = expectItem()
                        }

                        Truth.assertThat(resource.getDataOrNull()).isEqualTo(testProduct)

                        // to finish the parent coroutine and kill the stateFlow
                        this@launch.cancel()
                    }
            }
        }


    @Test
    fun GivenNoProductsAgainstCategoryId_WhenGetByCategoryId_ThenEmptyListValid() =
        runBlockingTest {

            launch {
                productRepository.getByCategoryId("RANDOM")
                    .test {

                        var resource = expectItem()

                        // first loading with empty data
                        Truth.assertThat(resource).isInstanceOf(Resource.Loading::class.java)
                        Truth.assertThat(resource.getDataOrNull()).isEqualTo(null)


                        // iterate all till invalid response is received..
                        while (resource !is Resource.Valid) {
                            resource = expectItem()
                        }

                        Truth.assertThat(resource.getDataOrNull()).isInstanceOf(List::class.java)
                        Truth.assertThat(resource.getDataOrNull()).hasSize(0)

                        // to finish the parent coroutine and kill the stateFlow
                        this@launch.cancel()
                    }
            }
        }


    @Test
    fun GivenValidCategoryIdAndProducts_WhenGetByCategoryId_ThenValidWithProducts() =
        runBlockingTest {

            val testCategory = FakeDataUtil.category1
            fakeProductPersistenceDataSource.saveAll(testCategory.products)

            launch {
                productRepository.getByCategoryId(testCategory.id)
                    .test {

                        var resource = expectItem()

                        // first loading with empty data
                        Truth.assertThat(resource).isInstanceOf(Resource.Loading::class.java)
                        Truth.assertThat(resource.getDataOrNull()).isEqualTo(null)


                        // iterate all till invalid response is received..
                        while (resource !is Resource.Valid) {
                            resource = expectItem()
                        }

                        Truth.assertThat(resource.getDataOrNull()).isInstanceOf(List::class.java)
                        Truth.assertThat(resource.getDataOrNull())
                            .hasSize(testCategory.products.size)

                        // to finish the parent coroutine and kill the stateFlow
                        this@launch.cancel()
                    }
            }
        }
}