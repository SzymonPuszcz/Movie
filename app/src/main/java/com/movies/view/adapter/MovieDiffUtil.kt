package com.movies.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.movies.model.Movie

class MovieDiffUtil(private val movies: List<Movie>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return movies[oldItemPosition] == movies[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return movies.size
    }

    override fun getNewListSize(): Int {
        return movies.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return movies[oldItemPosition] == movies[newItemPosition]
    }
}