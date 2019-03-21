package com.movies.view.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.movies.R
import com.movies.view.callback.PicassoCallbackLogger
import com.squareup.picasso.Picasso


@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.progress_animation)
        .error(R.drawable.error_placeholder)
        .fit()
        .centerInside()
        .into(this, PicassoCallbackLogger())
}