package com.movies.api.model

import com.movies.model.NowPlayingMovie

data class NowPlayingMovieResponse(
    val page: Int,
    val results: List<NowPlayingMovie>,
    val total_results: Int,
    val total_pages: Int
)