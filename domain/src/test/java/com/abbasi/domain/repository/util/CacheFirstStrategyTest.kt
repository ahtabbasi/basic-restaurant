package com.abbasi.domain.repository.util

import app.cash.turbine.test
import com.abbasi.domain.models.Resource
import com.abbasi.domain.models.getDataOrNull
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class CacheFirstStrategyTest {

    private var cacheData = MutableStateFlow<String?>(null) // null means empty
    private var remoteData: String? = null // null means invalid remote result

    @Test
    fun GivenEmptyCacheAndValidRemote_WhenGet_ThenResponseShouldBeLoadingLoadingValidAndCacheUpdated() =
        runBlocking {

            val testRemoteResponse = "remoteData"

            fake_setEmptyCacheData()
            fake_setRemoteResponse(testRemoteResponse)

            val getOperation = launch {
                CacheFirstStrategy.performGetOperation(
                    fake_getFromCache,
                    fake_getFromRemote,
                    fake_updateCache
                ).test {

                    // first, it will emit loading with data from cache
                    expectItem().let {
                        assertThat(it).isInstanceOf(Resource.Loading::class.java)
                        assertThat(it.getDataOrNull()).isEqualTo(null)
                    }

                    // then, it will emit valid with data from remote
                    expectItem().let {
                        assertThat(it).isInstanceOf(Resource.Valid::class.java)
                        assertThat(it.getDataOrNull()).isEqualTo(remoteData)
                    }

                    // to finish the parent coroutine and kill the stateflow
                    this@launch.cancel()
                }
            }
            getOperation.join()
            assertThat(cacheData.value).isEqualTo(testRemoteResponse)
        }


    @Test
    fun GivenEmptyCacheAndInvalidRemote_WhenGet_ThenResponseShouldBeLoadingLoadingInvalidAndEmptyCache() =
        runBlocking {

            fake_setEmptyCacheData()
            fake_setInvalidRemoteResponse()

            val getOperation = launch {
                CacheFirstStrategy.performGetOperation(
                    fake_getFromCache,
                    fake_getFromRemote,
                    fake_updateCache
                ).test {

                    expectItem().let {
                        assertThat(it).isInstanceOf(Resource.Loading::class.java)
                        assertThat(it.getDataOrNull()).isEqualTo(null)
                    }

                    expectItem().let {
                        assertThat(it).isInstanceOf(Resource.Invalid::class.java)
                    }

                    expectItem().let {
                        assertThat(it).isInstanceOf(Resource.Valid::class.java)
                        assertThat(it.getDataOrNull()).isEqualTo(null)
                    }

                    // to finish the parent coroutine and kill the stateflow
                    this@launch.cancel()
                }
            }
            getOperation.join()
            assertThat(cacheData.value).isEqualTo(null)
        }

    @Test
    fun GivenPopulatedCacheAndValidRemote_WhenGet_ThenResponseShouldBeLoadingLoadingValidAndCacheUpdated() =
        runBlocking {

            val testRemoteResponse = "updatedData"
            val testOutdatedCacheData = "OutdatedCacheData"

            fake_setCacheData(testOutdatedCacheData)
            fake_setRemoteResponse(testRemoteResponse)

            val getOperation = launch {
                CacheFirstStrategy.performGetOperation(
                    fake_getFromCache,
                    fake_getFromRemote,
                    fake_updateCache
                ).test {
                    expectItem().let {
                        assertThat(it).isInstanceOf(Resource.Loading::class.java)
                        assertThat(it.getDataOrNull()).isEqualTo(null)
                    }

                    expectItem().let {
                        assertThat(it).isInstanceOf(Resource.Loading::class.java)
                        assertThat(it.getDataOrNull()).isEqualTo(testOutdatedCacheData)
                    }

                    expectItem().let {
                        assertThat(it).isInstanceOf(Resource.Valid::class.java)
                        assertThat(it.getDataOrNull()).isEqualTo(testRemoteResponse)
                    }

                    // to finish the parent coroutine and kill the stateflow
                    this@launch.cancel()
                }
            }
            getOperation.join()
            assertThat(cacheData.value).isEqualTo(testRemoteResponse)
        }

    @Test
    fun GivenPopulatedCacheAndInvalidRemote_WhenGet_ThenResponseShouldBeLoadingLoadingInvalidValidAndCacheSame() =
        runBlocking {

            val testOutdatedCacheData = "OutdatedCacheData"

            fake_setCacheData(testOutdatedCacheData)
            fake_setInvalidRemoteResponse()

            val getOperation = launch {
                CacheFirstStrategy.performGetOperation(
                    fake_getFromCache,
                    fake_getFromRemote,
                    fake_updateCache
                ).test {

                    expectItem().let {
                        assertThat(it).isInstanceOf(Resource.Loading::class.java)
                        assertThat(it.getDataOrNull()).isEqualTo(null)
                    }

                    expectItem().let {
                        assertThat(it).isInstanceOf(Resource.Loading::class.java)
                        assertThat(it.getDataOrNull()).isEqualTo(testOutdatedCacheData)
                    }

                    expectItem().let {
                        assertThat(it).isInstanceOf(Resource.Invalid::class.java)
                        assertThat(it.getDataOrNull()).isEqualTo(null)
                    }

                    expectItem().let {
                        assertThat(it).isInstanceOf(Resource.Valid::class.java)
                        assertThat(it.getDataOrNull()).isEqualTo(testOutdatedCacheData)
                    }

                    // to finish the parent coroutine and kill the stateflow
                    this@launch.cancel()
                }
            }
            getOperation.join()
            assertThat(cacheData.value).isEqualTo(testOutdatedCacheData)
        }

    private val fake_getFromCache: (suspend () -> StateFlow<String?>) = {
        println("fake_getFromCache: ${cacheData.value}")
        cacheData
    }

    private val fake_getFromRemote: (() -> Flow<Resource<String?>>) = {
        flow {
            println("fake_getFromRemote: ${remoteData}")
            if (remoteData == null) emit(Resource.Invalid<String?>("Error: Invalid response "))
            else emit(Resource.Valid(remoteData))
        }
    }

    private val fake_updateCache: (suspend (String?) -> Unit) = {
        cacheData.value = (it)
    }

    private fun fake_setCacheData(testCacheData: String) {
        cacheData = MutableStateFlow(testCacheData)
    }

    private fun fake_setRemoteResponse(testRemoteData: String) {
        remoteData = testRemoteData
    }

    private fun fake_setEmptyCacheData() {
        cacheData = MutableStateFlow(null)
    }

    private fun fake_setInvalidRemoteResponse() {
        remoteData = null
    }


}