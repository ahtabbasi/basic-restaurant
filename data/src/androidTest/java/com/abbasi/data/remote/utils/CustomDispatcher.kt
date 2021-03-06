package com.abbasi.data.remote.utils

import com.abbasi.data.FakeDataUtil.Remote.getValidJsonResponse
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

/**
 * Routes the request based on the path
 */
internal class CustomDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            MockNetworkConfig.baseUrl -> {
                MockResponse().setResponseCode(MockNetworkConfig.status)
                    .setBody(getValidJsonResponse())
            }

            else -> throw IllegalArgumentException("Unable to find ${request.path}")
        }
    }
}

