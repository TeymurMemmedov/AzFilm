package com.example.azfilm.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.azfilm.AzFilmApplication
import com.example.azfilm.R
import com.example.azfilm.api.serviceModels.MovieResponseItem
import com.example.azfilm.databinding.FragmentHomeBinding
import com.example.azfilm.databinding.RvMovieWithPosterBinding
import com.example.azfilm.ui.MainActivity.Companion.navGraphTracker
import com.example.azfilm.ui.adapters.GenericRvAdapter
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.base.BasePagingResponse
import com.example.azfilm.data.mapper.mapMovieDetailsResponseItemToMovieDetailUIModel
import com.example.azfilm.data.mapper.mapMovieResponseItemToMovieUIModel
import com.example.azfilm.ui.favorites.FavoritesViewModel
import com.example.azfilm.ui.movie.MovieViewModel
import com.example.azfilm.ui.uiModels.MovieUIModel
import com.example.azfilm.utils.MovieHelper.Companion.generateImageFullPath
import com.example.azfilm.utils.MovieListTypes
import com.example.azfilm.utils.ResultWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomeFragment: BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    lateinit var auth: FirebaseAuth
    lateinit var homeViewModel: HomeViewModel
    lateinit var favoritesViewModel: FavoritesViewModel
    lateinit var movieViewModel: MovieViewModel
    var user: FirebaseUser? = null

    val homeFilmClickListener:(MovieUIModel)->Unit = {
        movieViewModel.getMovieById(it.id)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        Log.d("HomeFragment","onCreate")
        homeViewModel = ViewModelProvider(this,
            HomeViewModelFactory((requireActivity().application as AzFilmApplication).repository))[HomeViewModel::class.java]


        favoritesViewModel = ViewModelProvider(requireActivity()).get(FavoritesViewModel::class.java)

        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        user = auth.currentUser
        Log.d("HomeFragment","onCreateView")

        movieViewModel.setNullToSelectedFilm()

        binding.tvGreeting.text = "Hello, ${user?.displayName}"

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
                    homeFilmClickListener(movie)
                }
            }
        }

        val recentsAdapter = GenericRvAdapter<MovieUIModel,RvMovieWithPosterBinding>(
            RvMovieWithPosterBinding::inflate,
            bind
        )


        val classicsAdapter = GenericRvAdapter<MovieUIModel,RvMovieWithPosterBinding>(
            RvMovieWithPosterBinding::inflate,
            bind
        )

        val modernsAdapter = GenericRvAdapter<MovieUIModel,RvMovieWithPosterBinding>(
            RvMovieWithPosterBinding::inflate,
            bind
        )

        val animationsAdapter = GenericRvAdapter<MovieUIModel,RvMovieWithPosterBinding>(
            RvMovieWithPosterBinding::inflate,
            bind
        )
        binding.apply {
            val commonLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

            rvRecents.apply { adapter = recentsAdapter; layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)  }
            rvClassics.apply { adapter = classicsAdapter; layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)  }
            rvModerns.apply { adapter = modernsAdapter; layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)  }
            rvAnimations.apply { adapter = animationsAdapter; layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)  }
        }

         fun updateUI(result: ResultWrapper<BasePagingResponse<MovieResponseItem>?>, adapter: GenericRvAdapter<MovieUIModel, RvMovieWithPosterBinding>,progressBar:ProgressBar) {
            when (result) {
                is ResultWrapper.Loading -> progressBar.visibility = View.VISIBLE
                is ResultWrapper.Success -> {
                    result.value?.let { adapter.sendListToAdapter(it.results.map {
                        mapMovieResponseItemToMovieUIModel(it)
                    }) }
                    progressBar.visibility = View.GONE
                }
                is ResultWrapper.GenericError -> result.error?.let { Log.d("GenericError", it) }
                is ResultWrapper.NetworkError -> Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show()
            }

        }



        homeViewModel.recentFilms.observe(viewLifecycleOwner) {
            updateUI(it, recentsAdapter,binding.progressBarRecents)
            homeViewModel.updateCurrentAndTotalPages(it,MovieListTypes.RECENTS)
        }

        homeViewModel.classics.observe(viewLifecycleOwner) {
            updateUI(it, classicsAdapter,binding.progressBarClassics)
            homeViewModel.updateCurrentAndTotalPages(it,MovieListTypes.CLASSICS)
        }

        homeViewModel.moderns.observe(viewLifecycleOwner) {
            updateUI(it, modernsAdapter,binding.progressBarModerns)
            homeViewModel.updateCurrentAndTotalPages(it,MovieListTypes.MODERNS)

        }
        homeViewModel.animations.observe(viewLifecycleOwner) {
            updateUI(it, animationsAdapter,binding.progressBarAnimations)
            homeViewModel.updateCurrentAndTotalPages(it,MovieListTypes.ANIMATIONS)
        }


        binding.btnRefresh.setOnClickListener {
            homeViewModel.refreshMovieLists()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.refreshMovieLists()
            binding.swipeRefreshLayout.setRefreshing(false);
        }





        movieViewModel.selectedFilm.observe(viewLifecycleOwner){
            if(it!=null) {
                val bundle = Bundle()
//                val movieDetailObject = mapMovieDetailsResponseItemToMovieDetailUIModel(it)
                it.isFavorite = favoritesViewModel.isMovieFavorite(it.id) == true
                bundle.putSerializable("movie", it)
                findNavController().navigate(R.id.movieFragment, bundle)
            }
        }




        return  binding.root
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("HomeFragment", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("HomeFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("HomeFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("HomeFragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("HomeFragment", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeFragment", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("HomeFragment", "onDetach")
    }
}


