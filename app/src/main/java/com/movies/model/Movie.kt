package com.movies.model

//@Entity(primaryKeys = [("id")])
data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String?
)

