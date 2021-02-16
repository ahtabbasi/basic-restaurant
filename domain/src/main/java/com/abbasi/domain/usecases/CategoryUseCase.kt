package com.abbasi.domain.usecases

import com.abbasi.domain.repository.CategoryRepository
import com.abbasi.domain.repository.util.CacheFirstStrategy
import javax.inject.Inject

class CategoryUseCase  @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    suspend fun getAllWithProducts() = categoryRepository.getAllWithProducts(CacheFirstStrategy)
}