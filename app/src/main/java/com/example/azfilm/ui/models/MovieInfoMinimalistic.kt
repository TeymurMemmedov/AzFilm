package com.example.azfilm.ui.models

data class MovieInfoMinimalistic(
    val id: Int,
    val original_title: String,
    val poster_path: String,
    val genre_ids: List<Int>,
    var genre_names:MutableList<String>
)

