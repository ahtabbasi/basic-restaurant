package com.abbasi.domain.usecases

import com.abbasi.domain.repository.CategoryRepository

class CategoryUseCase constructor(
    private val categoryRepository: CategoryRepository
) {

    fun getAllProducts() {
        TODO("Not yet implemented")
    }

    fun getFilteredProducts(query: String) {
        TODO("Not yet implemented")
    }
}