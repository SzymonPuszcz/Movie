package com.movies.api.model

import com.movies.model.Movie

data class NowPlayingMovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int
)