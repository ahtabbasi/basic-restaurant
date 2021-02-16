package com.abbasi.data

import com.abbasi.data.local.models.CategoryEntity
import com.abbasi.data.local.models.ProductEntity
import com.abbasi.domain.models.Category
import com.abbasi.domain.models.Product
import com.google.common.io.Resources
import java.io.File

object FakeDataUtil {

    object Local {
        val product1 = ProductEntity(
            "1", "1", "Prod1", "url", "desc", "5", "EUR"
        )
        private val product2 = ProductEntity(
            "2", "1", "Prodd2", "url", "desc", "6", "EUR"
        )
        private val product3 = ProductEntity(
            "1", "2", "Prodd3", "url", "desc", "4", "EUR"
        )


        val category1 = CategoryEntity("1", "Cat1", "Desc")
        private val category2 = CategoryEntity("2", "Cat2", "Desc")


        fun getAllFakeProductEntites() = listOf(product1, product2, product3)
        fun getAllFakeCategoryEntities() = listOf(category1, category2)
    }

    object Domain {

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

    object Remote {
        /**
         * This returns a valid response from server
         */
        fun getValidJsonResponse(): String {
            // Load the JSON response
            val uri = Resources.getResource("validData.json")
            val file = File(uri.path)
            return String(file.readBytes())
        }
    }

}