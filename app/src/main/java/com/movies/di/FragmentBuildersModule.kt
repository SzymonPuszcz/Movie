package com.movies.di

import com.movies.view.fragment.NowPlayingListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    internal abstract fun contributeNowPlayingListFragment(): NowPlayingListFragment
}