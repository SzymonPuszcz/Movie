package com.movies.service

import com.movies.api.model.NowPlayingMovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieService {

    @GET("movie/now_playing")
    fun fetchNowPlayingMovies(): Call<NowPlayingMovieResponse>

}