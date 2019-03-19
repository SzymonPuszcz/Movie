package com.movies.view.binding

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.movies.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Callback





@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    Picasso.get()
        .load("https://image.tmdb.org/t/p/original/$url")
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_foreground)
        .fit()
        .centerInside()
        .into(this, object : Callback {
            override fun onSuccess() {}

            override fun onError(e: Exception) {
                Log.e("PICASSO","ERROR", e)
            }
        })
}