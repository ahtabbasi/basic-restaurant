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