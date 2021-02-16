package com.abbasi.basicrestaurant.presentation.productdetail.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.abbasi.basicrestaurant.utils.Utils
import com.abbasi.basicrestaurant.utils.Utils.getAbsoluteUrl
import com.abbasi.domain.models.Product
import com.abbasi.domain.models.Resource
import com.abbasi.domain.models.getDataOrNull


@BindingAdapter("app:productImageUrl")
fun populateImageViewFromProductResource(
    imageView: ImageView,
    productResource: Resource<Product>?
) {
    productResource?.getDataOrNull()?.let {
        Utils.loadImageViewWithGlide(imageView, getAbsoluteUrl(it.url))
    }
}


@BindingAdapter("app:textProductName")
fun setProductNameFromResource(
    textView: TextView,
    productResource: Resource<Product>?
) {
    productResource?.getDataOrNull()?.let {
        textView.text = it.name
    }
}


@BindingAdapter("app:textProductPrice")
fun setProductPriceFromResource(
    textView: TextView,
    productResource: Resource<Product>?
) {

    productResource?.getDataOrNull()?.let {
        val price = it.price + " " + it.currency
        textView.text = price
    }
}