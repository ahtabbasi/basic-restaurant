package com.abbasi.domain.models

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ResourceTest {

    @Test
    fun GivenResourceIsValid_WhenTransformed_Successful() {
        val input = Resource.Valid("test")
        val output = input.transform { "test" + "test" }
        assertThat((output as Resource.Valid).data).isEqualTo("testtest")
    }

    @Test
    fun GivenResourceIsInvalid_WhenTransformed_Successful() {
        val input = Resource.Invalid("message", "test")
        val output = input.transform { "test" + "test" }
        assertThat((output as Resource.Invalid).data).isEqualTo("testtest")
    }

    @Test
    fun GivenResourceIsLoading_WhenTransformed_Successful() {
        val input = Resource.Loading("test")
        val output = input.transform { "test" + "test" }
        assertThat((output as Resource.Loading).data).isEqualTo("testtest")
    }

    @Test
    fun GivenResourceIsValid_WhenGetDataOrNull_Successful() {
        val input = Resource.Valid("test")
        assertThat(input.getDataOrNull()).isEqualTo("test")
    }

    @Test
    fun GivenResourceIsInvalid_WhenGetDataOrNull_Successful() {
        val input = Resource.Invalid("message", "test")
        assertThat(input.getDataOrNull()).isEqualTo("test")

    }

    @Test
    fun GivenResourceIsLoading_WhenGetDataOrNull_Successful() {
        val input = Resource.Loading("test")
        assertThat(input.getDataOrNull()).isEqualTo("test")
    }
}