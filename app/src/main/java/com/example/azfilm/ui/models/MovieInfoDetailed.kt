package com.example.azfilm.ui.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieInfoDetailed(
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genres") val genreIds: List<Genre>,
    @SerializedName("id") val id: Long,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("original_title") val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("runtime") val runtime: Int,
    val overview:String
):Serializable