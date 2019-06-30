package com.movies.di

import android.app.Application
import androidx.room.Room
import com.movies.dao.NowPlayingDao
import com.movies.model.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "movie_app_db")
            .build()
    }


    @Singleton
    @Provides
    fun provideNowPlayingDao(appDatabase: AppDatabase): NowPlayingDao =
            appDatabase.nowPlayingDao()
}