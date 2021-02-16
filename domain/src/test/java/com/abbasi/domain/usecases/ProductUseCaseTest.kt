package com.abbasi.domain.usecases

import com.abbasi.domain.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class ProductUseCaseTest {

    private lateinit var productUseCase: ProductUseCase
    private val mockRepository = Mockito.mock(ProductRepository::class.java)

    @Before
    fun setup() {
        productUseCase = ProductUseCase(mockRepository)
    }

    @Test
    fun get() {
        runBlocking {
            launch {

                val productId = "productId"
                val categoryId = "categoryId"

                productUseCase.get(productId, categoryId)
                Mockito.verify(mockRepository).get(productId, categoryId)
                this.cancel()
            }
            return@runBlocking
        }
    }

    @Test
    fun getByCategoryId() {
        runBlocking {
            launch {

                val categoryId = "categoryId"

                productUseCase.getByCategoryId(categoryId)
                Mockito.verify(mockRepository).getByCategoryId(categoryId)
                this.cancel()
            }
            return@runBlocking
        }
    }
}