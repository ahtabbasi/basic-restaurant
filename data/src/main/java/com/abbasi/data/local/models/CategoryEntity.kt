package com.abbasi.data.local.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abbasi.data.utils.Transformable
import com.abbasi.domain.models.Category
import com.abbasi.domain.models.Product

@Entity
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
) {

    fun toDomainModel(products: List<Product>) = Category(
        id = id,
        name = name,
        description = description,
        products = products
    )

    companion object : Transformable<CategoryEntity, Category> {
        override fun fromDomain(domainObj: Category) = CategoryEntity(
            id = domainObj.id,
            name = domainObj.name,
            description = domainObj.description,
        )
    }

}

fun List<Category>.fromDomain() = this.map { CategoryEntity.fromDomain(it) }