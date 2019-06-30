package com.movies.repository

import com.movies.api.model.Status
import com.movies.api.model.Status.SUCCESS
import com.movies.api.model.Status.LOADING
import com.movies.api.model.Status.ERROR

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}