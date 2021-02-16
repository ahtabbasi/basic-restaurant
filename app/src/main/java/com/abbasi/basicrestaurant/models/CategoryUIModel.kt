package com.abbasi.basicrestaurant.models

import android.view.View
import com.abbasi.basicrestaurant.BR
import com.abbasi.basicrestaurant.R
import com.abbasi.basicrestaurant.navigation.CategoryAndProductsNavigation
import com.abbasi.basicrestaurant.utils.genericrecyclerview.GenericRecyclerItem
import com.abbasi.domain.models.Category

data class CategoryUIModel(
    val id: String,
    val name: String,
    val products: List<ProductUIModel>
) {

    fun onClick(view: View) {
        CategoryAndProductsNavigation.navigateToProductsList(view, this)
    }

    fun toGenericRecyclerItem() = GenericRecyclerItem(
        data = this,
        layoutId = R.layout.item_category_with_products,
        variableId = BR.category
    )

    companion object {
        fun fromDomain(domainObj: Category) = CategoryUIModel(
            id = domainObj.id,
            name = domainObj.name,
            products = domainObj.products.map {
                ProductUIModel.fromDomain(it)
            }
        )
    }
}

fun List<Category>.toGenericRecyclerItems() = this.map {
    CategoryUIModel.fromDomain(it).toGenericRecyclerItem()
}