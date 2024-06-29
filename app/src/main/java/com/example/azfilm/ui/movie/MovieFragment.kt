package com.example.azfilm.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.azfilm.R
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.data.mapper.mapMovieDetailUIModelToFavoriteMovie
import com.example.azfilm.data.mapper.mapMovieDetailsResponseItemToMovieDetailUIModel
import com.example.azfilm.databinding.FragmentMovieBinding
import com.example.azfilm.ui.favorites.FavoritesViewModel
import com.example.azfilm.utils.MovieHelper.Companion.generateImageFullPath
import com.example.azfilm.utils.ResultWrapper

class MovieFragment: BaseFragment<FragmentMovieBinding>
    (FragmentMovieBinding::inflate){

        val favoritesViewModel: FavoritesViewModel by activityViewModels()
        val movieViewModel: MovieViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)


        movieViewModel.selectedFilm.observe(viewLifecycleOwner){ result->
            Log.d("SELECTED_MOVIE_CHANGE","SELECTED_MOVIE_CHANGE occured : ${result!!::class.java}")
            when (result) {
                is ResultWrapper.Loading -> binding.progressBar.visibility = View.VISIBLE
                is ResultWrapper.Success -> {
                    val movieResultBody = result.value.body()
                    movieViewModel.setSelectedFilmUIModel(movieResultBody)
                    binding.progressBar.visibility = View.GONE
                }
                is ResultWrapper.GenericError -> result.error?.let { Log.d("GenericError", it) }
                is ResultWrapper.NetworkError -> Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show()
                else-> Log.d("else","else")
            }
        }


        movieViewModel.selectedFilmUIModel.observe(viewLifecycleOwner){ movieUIModel->
            Log.d("SELECTED_MOVIE_IS_FAVORITE","${movieViewModel.selectedFilmUIModel.value?.isFavorite}")
            binding.apply {
                imgMovie.load(
                    if (movieUIModel?.backdropPath?.isBlank() == true)
                        R.drawable.img_not_found_backdrop
                    else
                        movieUIModel?.backdropPath?.generateImageFullPath()
                )
                tvMovieName.text = movieUIModel?.title
                tvMovieGenres.text = movieUIModel?.genres?.joinToString()
                tvOverview.text = movieUIModel?.overview
                tvReleaseDate.text = movieUIModel?.releaseDate
                tvDuration.text = movieUIModel?.duration
                btnAddOrRemoveToFavorites.setOnClickListener {
                    movieViewModel.changeFavoritStateOfSelectedFilm()
                }

            }


//            Log.d("SELECTED_MOVIE_IS_FAVORITE","${movieViewModel.selectedFilmUIModel.value?.isFavorite}")

            if(movieUIModel?.isFavorite==true){
                binding.btnAddOrRemoveToFavorites.setImageResource(
                    R.drawable.icon_favorite_filled
                )
            }
            else{
                binding.btnAddOrRemoveToFavorites.setImageResource(
                    R.drawable.icon_not_favorite_filled
                )

            }

        }

        binding.btnNavigateToHome.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }


        return  binding.root
    }


}