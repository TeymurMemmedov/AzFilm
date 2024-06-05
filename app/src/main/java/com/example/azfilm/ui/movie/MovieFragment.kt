package com.example.azfilm.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.azfilm.R
import com.example.azfilm.databinding.FragmentMovieBinding
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.data.mapper.mapMovieDetailUIModelToFavoriteMovie
import com.example.azfilm.data.mapper.mapMovieDetailsResponseItemToFavoriteMovie
import com.example.azfilm.data.mapper.mapMovieDetailsResponseItemToMovieDetailUIModel
import com.example.azfilm.ui.favorites.FavoritesViewModel
import com.example.azfilm.ui.uiModels.MovieDetailUIModel
import com.example.azfilm.utils.MovieHelper
import com.example.azfilm.utils.PosterSizes

class MovieFragment: BaseFragment<FragmentMovieBinding>
    (FragmentMovieBinding::inflate){

        lateinit var movie: MovieDetailUIModel

        lateinit var favoritesViewModel: FavoritesViewModel
        lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesViewModel = ViewModelProvider(requireActivity()).get(FavoritesViewModel::class.java)
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        movie = arguments?.getSerializable("movie") as MovieDetailUIModel


        binding.apply {

            imgMovie.load("${MovieHelper.tmdbImageBaseUrl}${PosterSizes.W_500.printableName}${movie.backdropPath}")
            tvMovieName.text = movie.title
//            tvMovieGenres.text = movie.genreIds.joinToString { it.name }
            tvOverview.text = movie.overview
            tvReleaseDate.text = movie.releaseDate
            tvDuration.text = movie.duration


            btnAddOrRemoveToFavorites.setOnClickListener {
                movieViewModel.changeFavoritStateOfSelectedFilm()
            }



//            btnAddOrRemoveToFavorites.setImageResource(
//                if(movie.isFavorite) R.drawable.icon_favorite_filled
//                else R.drawable.icon_favorite
//            )
//
//            btnAddOrRemoveToFavorites.setOnClickListener {
//                favoritesViewModel.addMovieToFavorites(
//                    mapMovieDetailUIModelToFavoriteMovie(movie)
//                )
//            }


        }


        movieViewModel.selectedFilm.observe(viewLifecycleOwner){
            if (it != null) {
                if(it.isFavorite){
                    favoritesViewModel.addMovieToFavorites(
                        mapMovieDetailUIModelToFavoriteMovie(it)
                    )

                    binding.btnAddOrRemoveToFavorites.setImageResource(
                        R.drawable.icon_favorite_filled
                    )
                }
                else{
                    favoritesViewModel.removeMovieFromFavorites(
                        mapMovieDetailUIModelToFavoriteMovie(it)
                    )
                    binding.btnAddOrRemoveToFavorites.setImageResource(
                        R.drawable.icon_not_favorite_filled
                    )
                }

            }
        }

        return  binding.root
    }
}