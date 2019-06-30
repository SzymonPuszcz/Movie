package com.movies.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movies.model.NowPlayingMovie

@Dao
interface NowPlayingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(nowPlayingNowPlayingMovie: NowPlayingMovie)

    @Query("SELECT * FROM now_playing_movie")
    fun all(): LiveData<List<NowPlayingMovie>>
}