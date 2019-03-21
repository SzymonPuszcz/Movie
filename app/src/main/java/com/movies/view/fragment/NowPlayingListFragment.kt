package com.movies.view.fragment

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.movies.R
import com.movies.databinding.FragmentNowPlayingListBinding
import com.movies.model.Movie
import com.movies.view.adapter.NowPlayingAdapter
import com.movies.viewmodel.DashboardViewModel
import com.movies.viewmodel.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class NowPlayingListFragment : Fragment(), SearchView.OnQueryTextListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)
    }

    private lateinit var adapter: NowPlayingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = getDataBinding(inflater, container)
        configureNowPlayingList(binding, savedInstanceState)
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        observeLiveData(viewModel.movies) { e -> updateMovies(e) }
        observeLiveData(viewModel.filteredMovies) { e -> updateMovies(e) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    private fun getDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNowPlayingListBinding {
        return DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_now_playing_list,
            container,
            false
        )
    }

    private fun configureNowPlayingList(
        binding: FragmentNowPlayingListBinding,
        savedInstanceState: Bundle?
    ) {
        configureAdapter(savedInstanceState)
        binding.nowPlayingList.adapter = adapter
        binding.nowPlayingList.layoutManager = getRecyclerLayoutManager()
    }

    private fun configureAdapter(savedInstanceState: Bundle?) {
        adapter = if (savedInstanceState == null) {
            NowPlayingAdapter()
        } else {
            savedInstanceState.getSerializable(ADAPTER) as NowPlayingAdapter
        }
    }

    private fun getRecyclerLayoutManager(): GridLayoutManager {
        val currentOrientation = resources.configuration.orientation
        return if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(activity, LANDSCAPE_NUM_OF_COLUMNS)
        } else {
            GridLayoutManager(activity, PORTRAIT_NUM_OF_COLUMNS)
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(ADAPTER, adapter)
    }

    private fun updateMovies(movies: List<Movie>) {
        adapter.setMovieList(movies)
    }

    override fun onQueryTextSubmit(input: String?): Boolean {
        filterMovies(input)
        return false
    }

    override fun onQueryTextChange(input: String?): Boolean {
        filterMovies(input)
        return false
    }

    private fun filterMovies(input: String?) {
        input?.let { viewModel.search(it) }
    }

    companion object {
        var TAG: String = NowPlayingListFragment::class.java.simpleName
        const val ADAPTER = "ADAPTER"
        const val LANDSCAPE_NUM_OF_COLUMNS = 4
        const val PORTRAIT_NUM_OF_COLUMNS = 2
    }
}

inline fun <T> LifecycleOwner.observeLiveData(data: LiveData<T>, crossinline onChanged: (T) -> Unit) {
    data.observe(this, Observer {
        it?.let { value -> onChanged(value) }
    })
}