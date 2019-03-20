package com.movies.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.movies.R
import com.movies.databinding.NowPlayingItemRowBinding
import com.movies.model.Movie
import com.movies.view.viewholder.NowPlayingViewHolder

class NowPlayingAdapter : RecyclerView.Adapter<NowPlayingViewHolder>() {
    private var movies: List<Movie>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<NowPlayingItemRowBinding>(
            inflater,
            R.layout.now_playing_item_row,
            parent,
            false
        )
        return NowPlayingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies?.size ?: 0
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        holder.binding.movie = movies?.get(position)
        holder.binding.executePendingBindings()
    }

    fun setMovieList(movies: List<Movie>) {
        this.movies = movies
        notifyItemRangeChanged(0, movies.size)
    }
}