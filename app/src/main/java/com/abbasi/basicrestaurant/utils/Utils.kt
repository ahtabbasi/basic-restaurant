package com.abbasi.basicrestaurant.utils

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.abbasi.basicrestaurant.R
import com.abbasi.data.BuildConfig
import com.bumptech.glide.Glide

object Utils {

    fun getAbsoluteUrl(relativeUrl: String) = BuildConfig.BASE_URL + relativeUrl

    @Suppress("MagicNumber")
    fun loadImageViewWithGlide(iv: ImageView, url: String) {
        iv.apply {

            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(context)
                .load(url)
                .placeholder(circularProgressDrawable)
                .error(R.drawable.placeholder_image)
                .centerCrop()
                .into(this)
        }
    }
}

