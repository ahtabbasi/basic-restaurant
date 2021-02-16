package com.abbasi.basicrestaurant.navigation

import android.view.View
import androidx.navigation.findNavController
import com.abbasi.basicrestaurant.models.CategoryUIModel
import com.abbasi.basicrestaurant.models.ProductUIModel
import com.abbasi.basicrestaurant.presentation.home.HomeFragmentDirections

object CategoryAndProductsNavigation {

    fun navigateToProductsList(view: View, category: CategoryUIModel) {
        val action = HomeFragmentDirections.actionHomeFragmentToCategoryDetailFragment(
            category.id,
            category.name
        )
        view.findNavController().navigate(action)
    }

    fun navigateToProductsDetails(view: View, product: ProductUIModel) {
        val action = HomeFragmentDirections.actionGlobalProductDetailFragment(
            product.id,
            product.categoryId,
        )
        view.findNavController().navigate(action)
    }
}