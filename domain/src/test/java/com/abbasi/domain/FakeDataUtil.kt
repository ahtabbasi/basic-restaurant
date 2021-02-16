package com.abbasi.domain

import com.abbasi.domain.models.Product
import com.abbasi.domain.models.Category

object FakeDataUtil {

    val product1 = Product(
        "1", "1", "Prod1", "url", "desc", "5", "EUR"
    )
    private val product2 = Product(
        "2", "1", "Prod2", "url", "desc", "6", "EUR"
    )
    private val product3 = Product(
        "1", "2", "Prod3", "url", "desc", "4", "EUR"
    )


    val category1 = Category(
        "1", "Cat1", "Desc",
        listOf(product1, product2)
    )
    private val category2 = Category(
        "2", "Cat2", "Desc",
        listOf(product3)
    )


    fun getAllFakeProducts() = listOf(product1, product2, product3)
    fun getAllFakeCategories() = listOf(category1, category2)
}
