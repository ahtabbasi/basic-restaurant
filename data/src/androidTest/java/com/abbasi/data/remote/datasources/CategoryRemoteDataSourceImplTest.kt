package com.abbasi.data.remote.datasources

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.abbasi.data.MainCoroutineRule
import com.abbasi.data.remote.NetworkHelper
import com.abbasi.data.remote.api.ApiClient
import com.abbasi.data.remote.utils.CustomDispatcher
import com.abbasi.domain.models.Resource
import com.abbasi.domain.models.getDataOrNull
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@ExperimentalTime
@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class CategoryRemoteDataSourceImplTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var mockServer: MockWebServer
    private lateinit var apiClient: ApiClient
    private lateinit var categoryRDS: CategoryRemoteDataSourceImpl

    @Before
    fun initService() {
        mockServer = MockWebServer()
        mockServer.dispatcher = CustomDispatcher()
        mockServer.start()
        apiClient = NetworkHelper.getMockApiClient(mockServer)

        categoryRDS = CategoryRemoteDataSourceImpl(apiClient)
    }

    @Test
    fun getAllValid() = runBlocking {

        val output = categoryRDS.getAll()
        output.test(timeout = 30.seconds) {
            // first emits loading
            awaitItem().let {
                Truth.assertThat(it).isInstanceOf(Resource.Loading::class.java)
                Truth.assertThat(it.getDataOrNull()).isEqualTo(null)
            }

            // then emits valid
            awaitItem().let {
                Truth.assertThat(it).isInstanceOf(Resource.Valid::class.java)
                Truth.assertThat(it.getDataOrNull()?.size).isGreaterThan(0)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun after() {
        mockServer.shutdown()
    }


}