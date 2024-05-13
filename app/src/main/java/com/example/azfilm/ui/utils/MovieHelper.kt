package com.example.azfilm.ui.utils

import android.graphics.Movie
import com.example.azfilm.ui.models.MovieInfoMinimalistic

class MovieHelper {

    companion object{
        val genresMap = mapOf(
            28 to "Döyüş",
            12 to "Macəra",
            16 to "Animasiya",
            35 to "Komediya",
            80 to "Detektiv",
            99 to "Sənədli film",
            18 to "Dram",
            10751 to "Ailə",
            14 to "Fantastika",
            36 to "Tarixi",
            27 to "Qorxu",
            10402 to "Musiqili",
            9648 to "Müəmma",
            10749 to "Romantik",
            878 to "Elmi fantastika",
            10770 to "TV filmi",
            53 to "Triller",
            10752 to "Müharibə",
        )

        fun initializeGenreNames(movies: List<MovieInfoMinimalistic>) {
            for (movie in movies) {
                // Ensure movie.genre_names is initialized
                if (movie.genre_names == null) {
                    movie.genre_names = mutableListOf()
                }

                for (genre_id in movie.genre_ids) {
                    MovieHelper.genresMap[genre_id]?.let {
                        movie.genre_names.add(it)
                    }
                }
            }
        }




    }
}