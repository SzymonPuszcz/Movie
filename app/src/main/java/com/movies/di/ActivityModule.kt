package com.movies.di

import com.movies.view.activity.DashboardActivity
import com.movies.view.fragment.NowPlayingListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    internal abstract fun contributeDashboardActivity(): DashboardActivity
}