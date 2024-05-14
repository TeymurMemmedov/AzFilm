package com.example.azfilm.ui.models

data class DiscoverMovieResponse(
    val page: Int,
    val results: List<MovieInfoMinimalistic>,
    val total_pages:Int
)
