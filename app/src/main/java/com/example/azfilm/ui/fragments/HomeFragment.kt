package com.example.azfilm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.azfilm.R
import com.example.azfilm.databinding.FragmentHomeBinding
import com.example.azfilm.databinding.RvItemClassicsBinding
import com.example.azfilm.ui.adapters.GenericRvAdapter
import com.example.azfilm.ui.models.MoviesRepository
import com.example.azfilm.ui.models.MovieService
import com.example.azfilm.ui.models.MovieInfoMinimalistic
import com.example.azfilm.ui.viewmodels.HomeViewModel
import com.example.azfilm.ui.viewmodels.HomeViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment:BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    lateinit var auth: FirebaseAuth
    var user: FirebaseUser? = null

    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val movieApiService = retrofit.create(MovieService::class.java)
        val repository = MoviesRepository(movieApiService)
        val viewModelFactory = HomeViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        viewModel.fetchRecentMovies()

        val adapter = GenericRvAdapter<MovieInfoMinimalistic,RvItemClassicsBinding>(
            RvItemClassicsBinding::inflate
        )
        { binding, movie, position ->
            binding.apply {
                tvFilmName.text = movie.original_title
                tvGenres.text = movie.genre_names.joinToString()
                imgPoster.load(
                    if(!movie.poster_path.isNullOrBlank()) "https://image.tmdb.org/t/p/w500${movie.poster_path}"
                    else  R.drawable.not_found_img
                )
            }
        }

        viewBinding.rvRecents.apply {
            this.adapter  = adapter
            layoutManager  = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }



        viewModel.films.observe(viewLifecycleOwner){
           adapter.sendListToAdapter(it)
        }


//        viewBinding.tvUserDetails.text = "${user?.email}"
//        viewBinding.btnLogout.setOnClickListener {
//            auth.signOut()
//
//
//            navGraphTracker.setNavGraph(R.navigation.auth_nav_graph)
//
//        }



        return  viewBinding.root
    }
}