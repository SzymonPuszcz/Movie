package com.movies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movies.model.Movie
import com.movies.repository.MovieRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


class DashboardViewModel @Inject
constructor(
    private val discoverRepository: MovieRepository,
    application: Application
) : AndroidViewModel(application) {

    val movies: LiveData<List<Movie>> = discoverRepository.loadNowPlayingMovies()
    val filteredMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    private val querySearch: Subject<String> = PublishSubject.create()
    private val restoreSearch: Subject<String> = PublishSubject.create()
    private val subscriptions = CompositeDisposable()

    init {
        subscriptions.addAll(
            querySearch.switchMap { query ->
                filter(query)
            }
                .subscribe { newFiltered ->
                    filteredMovies.value = newFiltered
                },
            restoreSearch.flatMap { query ->
                shouldRestoreSearch(query)
                    .filter { restoreSearch -> restoreSearch }
                    .map { query }
            }
                .subscribe { query -> search(query) }
        )

    }

    fun restoreFiltering(query: String) {
        restoreSearch.onNext(query)
    }

    private fun shouldRestoreSearch(query: String): Observable<Boolean> {
        return Observable.just(filterMovies(query)?.size != movies.value?.size)
    }

    fun search(query: String) {
        querySearch.onNext(query)
    }

    private fun filter(query: String): Observable<List<Movie>?>? = Observable.just(
        filterMovies(query)
    )

    private fun filterMovies(query: String) =
        movies.value?.filter { it.title.contains(query, ignoreCase = true) }?.toList()
}
