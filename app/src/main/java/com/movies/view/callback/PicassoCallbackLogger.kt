package com.movies.view.callback

import android.util.Log
import com.squareup.picasso.Callback
import java.lang.Exception

class PicassoCallbackLogger: Callback {
    override fun onSuccess() {
        Log.d(TAG, "Image loading with Picasso success")
    }

    override fun onError(e: Exception?) {
        Log.e(TAG, "Error during loading image with Picasso", e)
    }

    companion object {
        var TAG: String = PicassoCallbackLogger::class.java.simpleName
    }
}