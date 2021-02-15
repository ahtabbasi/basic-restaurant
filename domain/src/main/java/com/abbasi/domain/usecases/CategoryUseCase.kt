package com.abbasi.domain.usecases

import com.abbasi.domain.repository.CategoryRepository
import com.abbasi.domain.repository.util.CacheFirstStrategy

class CategoryUseCase constructor(
    private val categoryRepository: CategoryRepository
) {

    suspend fun getAllWithProducts() = categoryRepository.getAllWithProducts(CacheFirstStrategy)
}