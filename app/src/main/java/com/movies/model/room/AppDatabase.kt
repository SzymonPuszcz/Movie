package com.movies.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movies.dao.NowPlayingDao
import com.movies.model.NowPlayingMovie

@Database(entities = arrayOf(NowPlayingMovie::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun nowPlayingDao(): NowPlayingDao
}