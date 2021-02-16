package com.abbasi.data.remote.utils

import com.abbasi.data.BuildConfig
import java.net.HttpURLConnection

object MockNetworkConfig {

    var baseUrl = BuildConfig.BASE_URL

    /**
     * This is the status of the call that is expected from server
     */
    var status = HttpURLConnection.HTTP_OK


}