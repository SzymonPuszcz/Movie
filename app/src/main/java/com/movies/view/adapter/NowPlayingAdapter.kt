package com.movies.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.movies.databinding.NowPlayingItemRowBinding
import com.movies.model.Movie
import com.movies.view.viewholder.NowPlayingViewHolder
import java.io.Serializable


class NowPlayingAdapter : RecyclerView.Adapter<NowPlayingViewHolder>(), Filterable, Serializable {

    private var movies: List<Movie>? = null
    private var recyclerElements: List<Movie>? = null


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
        return recyclerElements?.size ?: 0
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        holder.binding.movie = recyclerElements?.get(position)
        holder.binding.executePendingBindings()
    }

    fun setMovieList(movies: List<Movie>?) {
        this.movies = movies
        recyclerElements = movies
        notifyItemRangeChanged(0, recyclerElements?.size ?: 0)
        calculateMoviesDiff()
    }

    private fun calculateMoviesDiff() {
        this.recyclerElements?.let {
            val diffResult = DiffUtil.calculateDiff(MovieDiffUtil(it))
            diffResult.dispatchUpdatesTo(this)
        }
    }

    private val titleFilter = object : Filter() {
        override fun performFiltering(text: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            val query = convertToQuery(text) ?: return filterResults
            filterResults.values = filterMovies(query)
            return filterResults
        }

        override fun publishResults(text: CharSequence?, filterResults: FilterResults?) {
            recyclerElements = filterResults?.values as List<Movie>
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return titleFilter
    }

    private fun filterMovies(query: String): List<Movie>? {
        return if (query.isBlank()) {
            movies
        } else {
            filterMoviesByTitle(query)
        }
    }

    private fun filterMoviesByTitle(query: String): List<Movie>? {
        return movies?.filter { e -> e.title.toLowerCase().contains(query) }
    }

    private fun convertToQuery(query: CharSequence?): String? {
        return query?.toString()?.toLowerCase()
    }

}