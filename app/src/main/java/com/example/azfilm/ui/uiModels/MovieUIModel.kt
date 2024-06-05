package com.example.azfilm.ui.uiModels

data class MovieUIModel(
    val id: Int,
    val title:String,
    val posterPath:String,
    val genres: List<String?>
)

