package com.abbasi.data.remote.models


import com.abbasi.data.utils.Transformable
import com.abbasi.domain.models.Category
import com.abbasi.domain.models.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDTO(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("products")
    val products: List<ProductDTO>
) {

    fun toDomainModel(products: List<Product>) = Category(
        id = id,
        name = name,
        description = description,
        products = products
    )

    companion object : Transformable<CategoryDTO, Category> {
        override fun fromDomain(domainObj: Category) = CategoryDTO(
            id = domainObj.id,
            name = domainObj.name,
            description = domainObj.description,
            products = domainObj.products.map { ProductDTO.fromDomain(it) }
        )
    }
}

fun List<CategoryDTO>.toDomainModel() = this.map { it.toDomainModel(it.products.toDomainModel()) }