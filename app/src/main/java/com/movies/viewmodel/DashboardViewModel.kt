package com.movies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.movies.model.Movie
import com.movies.repository.MovieRepository
import javax.inject.Inject

class DashboardViewModel @Inject
constructor(
    private val discoverRepository: MovieRepository,
    application: Application
) : AndroidViewModel(application) {

    val movieListLiveData: LiveData<List<Movie>>

    init {
        movieListLiveData = discoverRepository.loadNowPlayingMovies()
    }
}
