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
        val restoredFragment = findNowPlayingFragment()
        if (restoredFragment != null) {
            nowPlayingFragment = restoredFragment as NowPlayingListFragment
        } else {
            addNowPlayingFragment()
        }
    }

    private fun findNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingListFragment.TAG)

    private fun addNowPlayingFragment() {
        nowPlayingFragment = NowPlayingListFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, nowPlayingFragment, NowPlayingListFragment.TAG)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(nowPlayingFragment)
        return true
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    companion object {
        var TAG: String = DashboardActivity::class.java.simpleName
    }
}
