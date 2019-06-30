package com.movies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movies.model.NowPlayingMovie
import com.movies.repository.NowPlayingRepository
import com.movies.repository.Resource
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


class DashboardViewModel @Inject
constructor(
    private val discoverRepository: NowPlayingRepository,
    application: Application
) : AndroidViewModel(application) {

    val movies: LiveData<Resource<List<NowPlayingMovie>>> = discoverRepository.loadNowPlayingMovies()
    val filteredMovies: MutableLiveData<List<NowPlayingMovie>> = MutableLiveData()
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
        return Observable.just(filterMovies(query)?.size != movies.value?.data?.size)
    }

    fun search(query: String) {
        querySearch.onNext(query)
    }

    private fun filter(query: String): Observable<List<NowPlayingMovie>?>? = Observable.just(
        filterMovies(query)
    )

    private fun filterMovies(query: String) =
        movies.value?.data?.filter { it.title.contains(query, ignoreCase = true) }?.toList()
}
