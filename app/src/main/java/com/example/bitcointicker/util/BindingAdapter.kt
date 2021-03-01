package com.example.bitcointicker.util



import android.webkit.URLUtil
import android.widget.ImageView

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


object BindingAdapter {

    @BindingAdapter("setImageUrlWithRadius")
    @JvmStatic
    fun setImageUrlWithRadius(imageView: ImageView, url: String?) {
        if (URLUtil.isValidUrl(url)) {
            Glide.with(imageView).load(url).centerInside().apply(RequestOptions.bitmapTransform(RoundedCorners(12))).into(imageView)
        }
    }

    @BindingAdapter("setImageUrl")
    @JvmStatic
    fun setImageUrl(imageView: ImageView, url: String?) {
        if (URLUtil.isValidUrl(url)) {
            Glide.with(imageView).load(url).centerCrop().into(imageView)
        }
    }
}