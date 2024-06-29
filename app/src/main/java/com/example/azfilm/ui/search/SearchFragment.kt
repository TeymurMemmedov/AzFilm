package com.example.azfilm.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.azfilm.R
import com.example.azfilm.api.serviceModels.MovieResponseItem
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.base.BasePagingResponse
import com.example.azfilm.data.mapper.mapMovieResponseItemToMovieUIModel
import com.example.azfilm.databinding.FragmentSearchBinding
import com.example.azfilm.databinding.RvMovieWithPosterBinding
import com.example.azfilm.ui.adapters.GenericRvAdapter
import com.example.azfilm.ui.movie.MovieViewModel
import com.example.azfilm.ui.uiModels.MovieUIModel
import com.example.azfilm.utils.MovieHelper.Companion.generateImageFullPath
import com.example.azfilm.utils.ResultWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment:BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {


    val movieViewModel:MovieViewModel by activityViewModels()

    val searchViewModel : SearchViewModel by activityViewModels()

    val bind:(RvMovieWithPosterBinding, MovieUIModel, Int)->Unit =  { binding, movie, position ->
        binding.apply {
            tvFilmName.text = movie.title
            tvGenres.text = movie.genres.joinToString()
            imgPoster.load(

                if(movie.posterPath.isNullOrBlank())
                    R.drawable.img_not_found_replacer
                else
                    movie.posterPath.generateImageFullPath()
            )
            root.setOnClickListener{
                searchClickListener(movie)
            }
        }
    }

    val searchClickListener:(MovieUIModel)->Unit = {
        movieViewModel.clearSelectedFilmData()
        movieViewModel.getMovieById(it.id)
        findNavController().navigate(R.id.movieFragment)
    }

    fun updateUI(result: ResultWrapper<BasePagingResponse<MovieResponseItem>?>, adapter: GenericRvAdapter<MovieUIModel, RvMovieWithPosterBinding>, progressBar: ProgressBar) {
        when (result) {
            is ResultWrapper.Loading -> progressBar.visibility = View.VISIBLE
            is ResultWrapper.Success -> {
                result.value?.let { adapter.sendListToAdapter(it.results.
                filter {
                    it.original_language == "az"
                }.map {
                    mapMovieResponseItemToMovieUIModel(it)
                }) }

                progressBar.visibility = View.GONE
            }
            is ResultWrapper.GenericError -> result.error?.let { Log.d("GenericError", it) }
            is ResultWrapper.NetworkError -> Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        val searchAdapter = GenericRvAdapter<MovieUIModel,RvMovieWithPosterBinding>(
            RvMovieWithPosterBinding::inflate,
            bind
        )

        binding.rvSearchResults.apply {
            adapter = searchAdapter;
            layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)  }


        searchViewModel.searchResults.observe(viewLifecycleOwner){
            updateUI(it,searchAdapter,binding.progressBar)
        }


        binding.btSearch.setOnClickListener {
            val query  = binding.evFindMovie.text.toString()
            if(query.isNotBlank()){
                searchViewModel.fetchSearchResults(query)
            }
        }

        return binding.root
    }


}