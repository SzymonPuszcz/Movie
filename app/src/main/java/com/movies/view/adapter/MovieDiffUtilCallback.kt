package com.movies.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.movies.model.NowPlayingMovie

class MovieDiffUtilCallback(private val newNowPlayingMovies: List<NowPlayingMovie>,
                            private val oldNowPlayingMovies: List<NowPlayingMovie>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNowPlayingMovies[oldItemPosition] == newNowPlayingMovies[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldNowPlayingMovies.size
    }

    override fun getNewListSize(): Int {
        return newNowPlayingMovies.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNowPlayingMovies[oldItemPosition] == newNowPlayingMovies[newItemPosition]
    }
}