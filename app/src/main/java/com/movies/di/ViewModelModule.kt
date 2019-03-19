package com.movies.di

import androidx.lifecycle.ViewModel
import com.movies.viewmodel.DashboardViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    internal abstract fun bindDashboardActivityViewModels(dashboardActivityViewModel: DashboardViewModel): ViewModel
}
