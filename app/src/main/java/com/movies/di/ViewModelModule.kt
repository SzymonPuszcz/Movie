package com.movies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movies.viewmodel.DashboardViewModel
import com.movies.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    internal abstract fun bindDashboardActivityViewModels(dashboardActivityViewModel: DashboardViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
