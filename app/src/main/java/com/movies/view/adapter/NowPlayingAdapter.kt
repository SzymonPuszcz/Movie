package com.movies.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.movies.databinding.NowPlayingItemRowBinding
import com.movies.model.NowPlayingMovie
import com.movies.view.viewholder.NowPlayingViewHolder
import java.io.Serializable


class NowPlayingAdapter : RecyclerView.Adapter<NowPlayingViewHolder>(), Serializable {

    private var nowPlayingMovies: List<NowPlayingMovie>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<NowPlayingItemRowBinding>(
            inflater,
            com.movies.R.layout.now_playing_item_row,
            parent,
            false
        )
        return NowPlayingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return nowPlayingMovies?.size ?: 0
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        holder.binding.nowPlayingMovie = nowPlayingMovies?.get(position)
        holder.binding.executePendingBindings()
    }

    fun setMovieList(newMoviesList: List<NowPlayingMovie>) {
        nowPlayingMovies = newMoviesList
        notifyDataSetChanged()
        calculateMoviesDiff(newMoviesList)
    }

    private fun calculateMoviesDiff(newMoviesList: List<NowPlayingMovie>) {
        this.nowPlayingMovies?.let {
            val diffResult = DiffUtil.calculateDiff(MovieDiffUtilCallback(newMoviesList, it))
            diffResult.dispatchUpdatesTo(this)
        }
    }
}