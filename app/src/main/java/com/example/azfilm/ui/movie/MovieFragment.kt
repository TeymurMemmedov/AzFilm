package com.example.azfilm.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.azfilm.R
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.data.mapper.mapMovieDetailUIModelToFavoriteMovie
import com.example.azfilm.databinding.FragmentMovieBinding
import com.example.azfilm.ui.favorites.FavoritesViewModel
import com.example.azfilm.ui.uiModels.MovieDetailUIModel
import com.example.azfilm.utils.MovieHelper.Companion.generateImageFullPath

class MovieFragment: BaseFragment<FragmentMovieBinding>
    (FragmentMovieBinding::inflate){

        lateinit var movie: MovieDetailUIModel

        val favoritesViewModel: FavoritesViewModel by activityViewModels()
        val movieViewModel: MovieViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        movie = arguments?.getSerializable("movie") as MovieDetailUIModel


        binding.apply {


            imgMovie.load(
                if(movie.backdropPath.isBlank())
                    R.drawable.img_not_found_backdrop
                else
                    movie.backdropPath.generateImageFullPath())
            tvMovieName.text = movie.title
            tvMovieGenres.text = movie.genres.joinToString()
            tvOverview.text = movie.overview
            tvReleaseDate.text = movie.releaseDate
            tvDuration.text = movie.duration


            btnAddOrRemoveToFavorites.setOnClickListener {
                movieViewModel.changeFavoritStateOfSelectedFilm()
            }

        }

        binding.btnAddOrRemoveToFavorites.setOnClickListener {
            movieViewModel.changeFavoritStateOfSelectedFilm()
        }


        movieViewModel.selectedFilm.observe(viewLifecycleOwner){ movie ->
            if (movie != null) {
                if(movie.isFavorite){

                    favoritesViewModel.addMovieToFavorites(
                        mapMovieDetailUIModelToFavoriteMovie(movie)
                    )

                    binding.btnAddOrRemoveToFavorites.setImageResource(
                        R.drawable.icon_favorite_filled
                    )

                }
                else{
                    favoritesViewModel.removeMovieFromFavorites(
                        mapMovieDetailUIModelToFavoriteMovie(movie)
                    )


                    binding.btnAddOrRemoveToFavorites.setImageResource(
                        R.drawable.icon_not_favorite_filled
                    )

                }

            }
        }

        binding.btnNavigateToHome.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
        return  binding.root
    }


}