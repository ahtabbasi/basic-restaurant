package com.abbasi.domain.usecases

import com.abbasi.domain.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
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
        runBlockingTest {
            launch {

                val productId = "productId"
                val categoryId = "categoryId"

                productUseCase.get(productId, categoryId)
                Mockito.verify(mockRepository).get(productId, categoryId)
                this.cancel()
            }
            return@runBlockingTest
        }
    }

    @Test
    fun getByCategoryId() {
        runBlockingTest {
            launch {

                val categoryId = "categoryId"

                productUseCase.getByCategoryId(categoryId)
                Mockito.verify(mockRepository).getByCategoryId(categoryId)
                this.cancel()
            }
            return@runBlockingTest
        }
    }
}