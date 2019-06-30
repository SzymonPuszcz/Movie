package com.movies.repository

import androidx.lifecycle.LiveData
import com.movies.BuildConfig
import com.movies.api.model.ApiResponse
import com.movies.api.model.NowPlayingMovieResponse
import com.movies.dao.NowPlayingDao
import com.movies.model.NowPlayingMovie
import com.movies.service.MovieService
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NowPlayingRepository @Inject
constructor(
    private val appExecutors: AppExecutors,
    private val nowPlayingDao: NowPlayingDao,
    private val movieService: MovieService
) {

    fun loadNowPlayingMovies(): LiveData<Resource<List<NowPlayingMovie>>> =
        object : NetworkBoundResource<List<NowPlayingMovie>, NowPlayingMovieResponse>(appExecutors) {
            override fun saveCallResult(item: NowPlayingMovieResponse) {
                item.results.forEach {
                    updatePosterPath(it)
                    nowPlayingDao.save(it)
                }
            }

            override fun shouldFetch(data: List<NowPlayingMovie>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<NowPlayingMovie>> {
                Timber.e("loadFromDb()")
                return nowPlayingDao.all()
            }

            override fun createCall(): LiveData<ApiResponse<NowPlayingMovieResponse>> {
                Timber.e("movieService.fetchNowPlayingMovies()")
                return movieService.fetchNowPlayingMovies()
            }
        }.asLiveData()

    private fun updatePosterPath(e: NowPlayingMovie) {
        if(e.poster_path?.startsWith(BuildConfig.MOVIEDB_IMAGE_ADDRESS) == false)
            e.poster_path =  BuildConfig.MOVIEDB_IMAGE_ADDRESS + e.poster_path
    }
}