package com.movies.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movies.api.model.NowPlayingMovieResponse
import com.movies.model.Movie
import com.movies.service.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject
constructor(
    private val movieService: MovieService
) {

    fun loadNowPlayingMovies(): LiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()
        movieService.fetchNowPlayingMovies().enqueue(object : Callback<NowPlayingMovieResponse> {
            override fun onResponse(call: Call<NowPlayingMovieResponse>, response: Response<NowPlayingMovieResponse>) {
                if (response.isSuccessful)
                    data.value = response.body()?.results
            }

            override fun onFailure(call: Call<NowPlayingMovieResponse>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

}