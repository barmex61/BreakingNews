package com.fatih.newsapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.fatih.newsapp.R

@BindingAdapter("android:downloadImage")
fun ImageView.downloadImage(url:String?){

    Glide.with(this).setDefaultRequestOptions(RequestOptions().centerCrop().diskCacheStrategy(
        DiskCacheStrategy.DATA).placeholder(CircularProgressDrawable(this.context).apply {
            centerRadius=40f
            strokeWidth=8f
            start()
    }).error(R.drawable.error_image)).load(url).into(this)
}