package com.movies.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movies.BuildConfig
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
        movieService.fetchNowPlayingMovies().enqueue(fetchNowPlayingMoviesCallback(data))
        return data
    }

    private fun fetchNowPlayingMoviesCallback(data: MutableLiveData<List<Movie>>): Callback<NowPlayingMovieResponse> {
        return object : Callback<NowPlayingMovieResponse> {
            override fun onResponse(call: Call<NowPlayingMovieResponse>, response: Response<NowPlayingMovieResponse>) {
                val movies = getMoviesFromResponse(response)
                preprocessMovies(movies)
                data.value = movies
            }

            override fun onFailure(call: Call<NowPlayingMovieResponse>, t: Throwable) {
                data.value = null
            }
        }
    }

    private fun preprocessMovies(movies: List<Movie>?) {
        movies?.forEach { e->  updatePosterPath(e)}
    }

    private fun updatePosterPath(e: Movie) {
        if(e.poster_path?.startsWith(BuildConfig.MOVIEDB_IMAGE_ADDRESS) == false)
            e.poster_path =  BuildConfig.MOVIEDB_IMAGE_ADDRESS + e.poster_path
    }

    private fun getMoviesFromResponse(response: Response<NowPlayingMovieResponse>): List<Movie>? {
        return if (response.isSuccessful)
            response.body()?.results
        else
            null
    }

}