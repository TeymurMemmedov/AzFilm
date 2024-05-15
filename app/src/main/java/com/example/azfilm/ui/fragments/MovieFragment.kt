package com.example.azfilm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.azfilm.databinding.FragmentMovieBinding
import com.example.azfilm.ui.models.MovieInfoDetailed
import com.example.azfilm.ui.utils.MovieHelper
import com.example.azfilm.ui.utils.PosterSizes

class MovieFragment:BaseFragment<FragmentMovieBinding>
    (FragmentMovieBinding::inflate){

        lateinit var movie:MovieInfoDetailed
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        movie = arguments?.getSerializable("movie") as MovieInfoDetailed
        val hour = movie.runtime/60
        val minute = movie.runtime - (hour*60)

        viewBinding.apply {

            imgMovie.load("${MovieHelper.tmdbImageBaseUrl}${PosterSizes.W_500.printableName}${movie.backdropPath}")
            tvMovieName.text = movie.title
            tvMovieGenres.text = movie.genreIds.joinToString { it.name }
            tvOverview.text = movie.overview
            tvReleaseDate.text = movie.releaseDate
            tvDuration.text = "$hour hour $minute minute"
        }

        return  viewBinding.root
    }
}