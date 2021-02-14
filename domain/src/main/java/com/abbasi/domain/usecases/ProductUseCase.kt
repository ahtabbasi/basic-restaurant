package com.abbasi.domain.usecases

import com.abbasi.domain.repository.ProductRepository

class ProductUseCase constructor(
    private val productRepository: ProductRepository
) {

    fun get(productId: String, categoryId: String) {
        TODO("Not yet implemented")
    }

    fun getByCategoryId(categoryId: String) {
        TODO("Not yet implemented")
    }

}