package com.example.azfilm.data.models

data class MovieInfoMinimalistic(
    val id: Long,
    val backdrop_path : String,
    val original_title: String,
    val poster_path: String,
    val genre_ids: List<Int>,
    var genre_names:MutableList<String>
)

