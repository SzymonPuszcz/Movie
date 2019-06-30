package com.movies.service

import androidx.lifecycle.LiveData
import com.movies.api.model.ApiResponse
import com.movies.api.model.NowPlayingMovieResponse
import retrofit2.http.GET

interface MovieService {

    @GET("movie/now_playing")
    fun fetchNowPlayingMovies(): LiveData<ApiResponse<NowPlayingMovieResponse>>

}