package com.example.azfilm.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.azfilm.api.serviceModels.MovieDetailsResponseItem
import com.example.azfilm.api.serviceModels.MovieResponseItem
import com.example.azfilm.data.FavoriteMovie
import com.example.azfilm.ui.uiModels.MovieDetailUIModel
import com.example.azfilm.ui.uiModels.MovieUIModel
import com.example.azfilm.utils.MovieHelper.Companion.genresMap
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun mapMovieResponseItemToMovieUIModel(responseItem: MovieResponseItem): MovieUIModel {
    return MovieUIModel(
        id = responseItem.id!!, // Use Elvis operator to ensure id is not null
        title = responseItem.title ?: "", // Use Elvis operator to handle null title
        posterPath = responseItem.poster_path ?: "", // Use Elvis operator to handle null posterPath
        genres = responseItem.genre_ids?.map { genreId ->
            genresMap[genreId]
        } ?: emptyList<String>() // If genre_ids is null, return empty list
    )
}

fun mapMovieDetailsResponseItemToMovieDetailUIModel(responseItem: MovieDetailsResponseItem): MovieDetailUIModel {
    return MovieDetailUIModel(
        id = responseItem.id!!, // Ensure id is not null
        title = responseItem.title ?: "", // Handle null title
        backdropPath = responseItem.backdrop_path ?: "", // Handle null backdropPath
        genres = responseItem.genres?.map { genre -> genre.name ?: "" } ?: emptyList(), // Handle null genres or genre names
        overview = responseItem.overview ?: "", // Handle null overview
        releaseDate = LocalDate.parse(responseItem.release_date).year.toString()  ?: "", // Handle null release date
        duration = convertRuntimeToString(responseItem.runtime) // Handle null runtime
    )
}

// Helper function to convert runtime (in minutes) to a string (e.g., "1h 30m")
private fun convertRuntimeToString(runtimeInMinutes: Int?): String {
    if (runtimeInMinutes == null) {
        return ""
    }
    val hours = runtimeInMinutes / 60
    val minutes = runtimeInMinutes % 60
    return if (hours > 0) {
        "$hours h $minutes m"
    } else {
        "$minutes m"
    }
}


fun mapMovieDetailsResponseItemToFavoriteMovie(responseItem: MovieDetailsResponseItem): FavoriteMovie {
    return FavoriteMovie(
        id = responseItem.id!!, // Ensure id is not null (primary key)
        backdropPath = responseItem.backdrop_path,
        title = responseItem.title ?: "", // Handle null title
        addedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) // Set current date as added date
    )
}

fun mapMovieDetailUIModelToFavoriteMovie(movieDetail: MovieDetailUIModel): FavoriteMovie {
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) // Format date as YYYY-MM-DD for Room

    return FavoriteMovie(
        id = movieDetail.id,
        backdropPath = movieDetail.backdropPath,
        title = movieDetail.title,
        addedDate = currentDate
    )
}

