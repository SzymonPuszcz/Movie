package com.movies.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "now_playing_movie")
data class NowPlayingMovie(
    @PrimaryKey val id: Int,
    val title: String,
    var poster_path: String?
)

