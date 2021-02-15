package com.abbasi.domain.usecases

import com.abbasi.domain.repository.CategoryRepository
import com.abbasi.domain.repository.util.CacheFirstStrategy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class CategoryUseCaseTest {

    private lateinit var categoryUseCase: CategoryUseCase
    private val mockRepository = Mockito.mock(CategoryRepository::class.java)

    @Before
    fun setup() {
        categoryUseCase = CategoryUseCase(mockRepository)
    }


    @Test
    fun getAllProducts() {
        runBlocking {
            launch {
                categoryUseCase.getAllWithProducts()
                verify(mockRepository).getAllWithProducts(CacheFirstStrategy)
                this.cancel()
            }
            return@runBlocking
        }
    }
}