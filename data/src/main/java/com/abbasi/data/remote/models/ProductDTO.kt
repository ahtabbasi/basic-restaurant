package com.abbasi.data.remote.models


import com.abbasi.data.utils.Transformable
import com.abbasi.domain.models.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    @SerialName("id")
    val id: String,
    @SerialName("categoryId")
    val categoryId: String,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
    @SerialName("description")
    val description: String,
    @SerialName("salePrice")
    val salePrice: SalePriceDTO
) {

    fun toDomainModel() = Product(
        id = id,
        categoryId = categoryId,
        name = name,
        url = url,
        description = description,
        price = salePrice.amount,
        currency = salePrice.currency
    )

    companion object : Transformable<ProductDTO, Product> {
        override fun fromDomain(domainObj: Product) = ProductDTO(
            id = domainObj.id,
            categoryId = domainObj.categoryId,
            name = domainObj.name,
            url = domainObj.url,
            description = domainObj.description,
            salePrice = SalePriceDTO(
                amount = domainObj.price,
                currency = domainObj.currency
            )
        )
    }
}