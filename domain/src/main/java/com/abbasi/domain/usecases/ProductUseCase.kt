package com.abbasi.domain.usecases

import com.abbasi.domain.repository.ProductRepository

class ProductUseCase constructor(
    private val productRepository: ProductRepository
) {

    suspend fun get(productId: String, categoryId: String) =
        productRepository.get(productId, categoryId)

    suspend fun getByCategoryId(categoryId: String) = productRepository.getByCategoryId(categoryId)
}