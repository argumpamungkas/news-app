package com.argumelar.newsapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.argumelar.newsapp.R
import com.bumptech.glide.Glide

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, urlString: String?){
    urlString?.let {
        Glide.with(imageView)
            .load(urlString)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(imageView)
    }
}