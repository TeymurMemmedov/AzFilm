package com.example.azfilm.utils

class MovieHelper {



    companion object{

        val tmdbImageBaseUrl = "https://image.tmdb.org/t/p/"
        fun String.generateImageFullPath(
            size:String = PosterSizes.W_500.printableName,
            posterPath:String = this
        ) = "${tmdbImageBaseUrl}${size}${this}"


        val genresMap = mapOf(
            28 to "Action",
            12 to "Adventure",
            16 to "Animation",
            35 to "Comedy",
            80 to "Detective",
            99 to "Documentary",
            18 to "Drama",
            10751 to "Family",
            14 to "Fantastic",
            36 to "History",
            27 to "Horror",
            10402 to "Musical",
            9648 to "Mystery",
            10749 to "Romantic",
            878 to "Science Fiction",
            10770 to "TV movie",
            53 to "Thriller",
            10752 to "War",
        )

//        fun initializeGenreNames(movies: List<MovieInfoMinimalistic>) {
//            for (movie in movies) {
//                // Ensure movie.genre_names is initialized
//                if (movie.genre_names == null) {
//                    movie.genre_names = mutableListOf()
//                }
//
//                for (genre_id in movie.genre_ids) {
//                    genresMap[genre_id]?.let {
//                        movie.genre_names.add(it)
//                    }
//                }
//            }
//        }




    }
}