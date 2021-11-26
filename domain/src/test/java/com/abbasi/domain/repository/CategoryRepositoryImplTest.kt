package com.abbasi.domain.repository

import app.cash.turbine.test
import com.abbasi.domain.FakeDataUtil
import com.abbasi.domain.datasources.local.FakeCategoryPersistenceDataSource
import com.abbasi.domain.datasources.local.FakeProductPersistenceDataSource
import com.abbasi.domain.datasources.remote.FakeCategoryRemoteDataSource
import com.abbasi.domain.datasources.remote.FakeCategoryRemoteDataSource.Behavior.SHOULD_RETURN_INVALID
import com.abbasi.domain.datasources.remote.FakeCategoryRemoteDataSource.Behavior.SHOULD_RETURN_VALID_DATA
import com.abbasi.domain.models.Resource
import com.abbasi.domain.models.getDataOrNull
import com.abbasi.domain.repository.util.CacheFirstStrategy
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class CategoryRepositoryImplTest {


    private lateinit var categoryRepository: CategoryRepositoryImpl
    private lateinit var fakeCategoryRemoteDataSource: FakeCategoryRemoteDataSource
    private lateinit var fakeCategoryPersistenceDataSource: FakeCategoryPersistenceDataSource
    private lateinit var fakeProductPersistenceDataSource: FakeProductPersistenceDataSource

    @Before
    fun setup() {
        fakeCategoryRemoteDataSource = FakeCategoryRemoteDataSource()
        fakeCategoryPersistenceDataSource = FakeCategoryPersistenceDataSource()
        fakeProductPersistenceDataSource = FakeProductPersistenceDataSource()
        categoryRepository = CategoryRepositoryImpl(
            fakeCategoryRemoteDataSource,
            fakeCategoryPersistenceDataSource,
            fakeProductPersistenceDataSource
        )
    }

    @Test
    fun GivenOutdatedCacheAndValidRemoteResponse_WhenGetAll_ThenReturnRemoteResponseAndUpdateCache() =
        runBlocking {

            val testRemoteResponse = FakeDataUtil.getAllFakeCategories()
            fakeCategoryPersistenceDataSource.saveAll(listOf(FakeDataUtil.category1))
            fakeProductPersistenceDataSource.saveAll(listOf(FakeDataUtil.product1))
            fakeCategoryRemoteDataSource.fake_setBehavior(SHOULD_RETURN_VALID_DATA)
            fakeCategoryRemoteDataSource.fake_setValidData(testRemoteResponse)


            val getAllOperation = launch {
                categoryRepository.getAllWithProducts(CacheFirstStrategy)
                    .test {

                        //iterate all till valid response is received..
                        var resource = awaitItem()
                        while (resource !is Resource.Valid) {
                            resource = awaitItem()
                        }

                        assertThat(resource.getDataOrNull()).isEqualTo(testRemoteResponse)

                        // to finish the parent coroutine and kill the stateFlow
                        this@launch.cancel()
                    }
            }
            getAllOperation.join()

            val allDataInCache = fakeCategoryPersistenceDataSource.fake_getAllWithProducts()
            val productsInCache = fakeProductPersistenceDataSource.fake_getAll()
            val productsFromRemote = testRemoteResponse.flatMap { it.products }

            // Cache should be updated properly
            assertThat(allDataInCache).isEqualTo(testRemoteResponse)
            assertThat(productsInCache).isEqualTo(productsFromRemote)
        }


    @Test
    fun GivenDataCachedAndInvalidRemoteResponse_WhenGetAll_ThenReturnCachedData() =
        runBlocking {

            val cachedCategoriesWithProducts = listOf(FakeDataUtil.category1)
            val cachedProducts = listOf(FakeDataUtil.product1)

            fakeCategoryPersistenceDataSource.saveAll(cachedCategoriesWithProducts)
            fakeProductPersistenceDataSource.saveAll(cachedProducts)
            fakeCategoryRemoteDataSource.fake_setBehavior(SHOULD_RETURN_INVALID)


            val getAllOperation = launch {
                categoryRepository.getAllWithProducts(CacheFirstStrategy)
                    .test {

                        //iterate all till valid response is received..
                        var resource = awaitItem()
                        while (resource !is Resource.Valid) {
                            resource = awaitItem()
                        }

                        assertThat(resource.getDataOrNull()).isEqualTo(cachedCategoriesWithProducts)

                        // to finish the parent coroutine and kill the stateFlow
                        this@launch.cancel()
                    }
            }
            getAllOperation.join()

            val allDataInCache = fakeCategoryPersistenceDataSource.fake_getAllWithProducts()
            val productsInCache = fakeProductPersistenceDataSource.fake_getAll()

            // Cache should still be the same
            assertThat(allDataInCache).isEqualTo(cachedCategoriesWithProducts)
            assertThat(productsInCache).isEqualTo(cachedProducts)
        }

    @Test
    fun cleanAllDataAndRepopulate() = runBlocking {

        // given
        val cachedCategoriesWithProducts = listOf(FakeDataUtil.category1)
        val cachedProducts = listOf(FakeDataUtil.product1)
        fakeCategoryPersistenceDataSource.saveAll(cachedCategoriesWithProducts)
        fakeProductPersistenceDataSource.saveAll(cachedProducts)

        // when
        val newData = FakeDataUtil.getAllFakeCategories()
        categoryRepository.cleanAllDataAndRepopulate(newData)

        // then
        val allDataInCache = fakeCategoryPersistenceDataSource.fake_getAllWithProducts()
        val productsInCache = fakeProductPersistenceDataSource.fake_getAll()
        assertThat(allDataInCache).isEqualTo(newData)
        assertThat(productsInCache).isEqualTo(newData.flatMap { it.products })
    }
}