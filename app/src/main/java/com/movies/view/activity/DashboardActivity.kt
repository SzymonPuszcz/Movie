package com.movies.view.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.movies.R
import com.movies.view.fragment.NowPlayingListFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class DashboardActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    lateinit var nowPlayingFragment: NowPlayingListFragment

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initializeUI()
    }

    private fun initializeUI() {
        setContentView(R.layout.activity_dashboard)
        configureFragments()
    }

    private fun configureFragments() {
        configureNowPlayingFragment()
    }

    private fun configureNowPlayingFragment() {
         addNewNowPlayingFragment()
    }

    private fun addNewNowPlayingFragment() {
        nowPlayingFragment = NowPlayingListFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, nowPlayingFragment, NowPlayingListFragment.TAG)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        initializeSearchView(menu)
        searchView.setOnQueryTextListener(nowPlayingFragment)
        return true
    }

    private fun initializeSearchView(menu: Menu?) {
        val menuItem = menu?.findItem(R.id.action_search)
        searchView = menuItem?.actionView as SearchView
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    companion object {
        var TAG: String = DashboardActivity::class.java.simpleName
    }
}
