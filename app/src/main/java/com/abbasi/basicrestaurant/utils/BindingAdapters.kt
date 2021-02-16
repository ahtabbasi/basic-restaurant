package com.abbasi.basicrestaurant.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.abbasi.basicrestaurant.utils.Utils.loadImageViewWithGlide
import com.abbasi.domain.models.Resource
import com.google.android.material.snackbar.Snackbar


@BindingAdapter("app:imageUrl")
fun populateImageViewFromUrl(
    imageView: ImageView,
    url: String
) = loadImageViewWithGlide(imageView, url)


/**
 * If the resource passed is an instance of class [Resource.Loading], view will be set to visible,
 * otherwise, it will be se to gone.
 */
@BindingAdapter("app:showLoadingOn")
fun showLoadingBasedOnResource(
    view: View,
    res: Resource<Any>?
) {
    view.visibility = if (res is Resource.Loading) View.VISIBLE else View.GONE
}


/**
 * If the resource passed is an instance of class [Resource.Invalid], a snack bar is shown with
 * the error message
 */
@BindingAdapter("app:showErrorOn")
fun showErrorBasedOnResource(
    view: View,
    res: Resource<Any>?
) {
    if (res is Resource.Invalid) {
        Snackbar.make(view, res.message, Snackbar.LENGTH_LONG).show()
    }
}