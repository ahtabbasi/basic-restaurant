package com.abbasi.data.local.models

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithProducts(

    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val products: List<ProductEntity>
)

fun CategoryWithProducts.toDomainModel() = this.let {
    this.category.toDomainModel(this.products.toDomainModel())
}
fun List<CategoryWithProducts>.toDomainModel() = this.map {
    it.toDomainModel()
}