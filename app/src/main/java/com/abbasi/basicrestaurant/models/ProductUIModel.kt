package com.abbasi.basicrestaurant.models

import android.view.View
import com.abbasi.basicrestaurant.BR
import com.abbasi.basicrestaurant.R
import com.abbasi.basicrestaurant.navigation.CategoryAndProductsNavigation
import com.abbasi.basicrestaurant.utils.genericrecyclerview.GenericRecyclerItem
import com.abbasi.domain.models.Product


data class ProductUIModel(
    val id: String,
    val categoryId: String,
    val name: String,
    val url: String
) {

    fun onClick(view: View) {
        CategoryAndProductsNavigation.navigateToProductsDetails(view, this)
    }

    fun toGenericRecyclerItem() = GenericRecyclerItem(
        data = this,
        layoutId = R.layout.item_product,
        variableId = BR.product
    )

    companion object {
        fun fromDomain(domainObj: Product) = ProductUIModel(
            id = domainObj.id,
            categoryId = domainObj.categoryId,
            name = domainObj.name,
            url = domainObj.url,
        )
    }
}

fun List<ProductUIModel>.toGenericRecyclerItems() = this.map { it.toGenericRecyclerItem() }

fun List<Product>.toGenericRecyclerItemsFromDomain() =
    this.map { ProductUIModel.fromDomain(it) }.toGenericRecyclerItems()