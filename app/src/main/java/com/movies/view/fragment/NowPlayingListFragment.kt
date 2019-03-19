package com.movies.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.movies.MovieApplication
import com.movies.R
import com.movies.view.adapter.NowPlayingAdapter
import com.movies.viewmodel.DashboardViewModel
import com.movies.viewmodel.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class NowPlayingListFragment : Fragment() {

   @Inject
   lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)
    }

    private lateinit var adapter: NowPlayingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<com.movies.databinding.FragmentNowPlayingListBinding>(
            inflater,
            R.layout.fragment_now_playing_list,
            container,
            false
        )
        adapter = NowPlayingAdapter()
        binding.nowPlayingList.adapter = adapter
        binding.nowPlayingList.layoutManager= GridLayoutManager(activity, 2)
        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.movieListLiveData.observe(this, Observer { it?.let { movies -> adapter.setMovieList(movies) } })
    }

    companion object {
        var TAG: String = NowPlayingListFragment::class.java.simpleName
    }
}
