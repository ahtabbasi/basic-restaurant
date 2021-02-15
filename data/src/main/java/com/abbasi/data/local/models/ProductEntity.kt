package com.abbasi.data.local.models


import androidx.room.Entity
import com.abbasi.data.utils.Transformable
import com.abbasi.domain.models.Product

@Entity(primaryKeys = ["id", "categoryId"])
data class ProductEntity(
    val id: String,
    val categoryId: String,
    val name: String,
    val url: String,
    val description: String,
    val price: String,
    val currency: String,
) {

    fun toDomainModel() = Product(
        id = id,
        categoryId = categoryId,
        name = name,
        url = url,
        description = description,
        price = price,
        currency = currency
    )

    companion object : Transformable<ProductEntity, Product> {
        override fun fromDomain(domainObj: Product) = ProductEntity(
            id = domainObj.id,
            categoryId = domainObj.categoryId,
            name = domainObj.name,
            url = domainObj.url,
            description = domainObj.description,
            price = domainObj.price,
            currency = domainObj.currency
        )
    }
}

fun List<ProductEntity>.toDomainModel() = this.map { it.toDomainModel() }
fun List<Product>.fromDomain() = this.map { ProductEntity.fromDomain(it) }