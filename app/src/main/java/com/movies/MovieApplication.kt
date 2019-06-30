package com.movies

import android.annotation.SuppressLint
import com.movies.di.AppComponent
import com.movies.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

@SuppressLint("unused")
class MovieApplication : DaggerApplication() {

    private val appComponent: AppComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}