package com.example.azfilm.ui.uiModels

import java.io.Serializable

data class MovieDetailUIModel(
    val id : Int,
    val title:String,
    val backdropPath:String,
    val genres:List<String>,
    val overview:String,
    val releaseDate:String,
    val duration:String,
    var isFavorite:Boolean = false
    ):Serializable
