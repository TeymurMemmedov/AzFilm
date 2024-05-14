package com.example.azfilm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.azfilm.R
import com.example.azfilm.databinding.FragmentHomeBinding
import com.example.azfilm.databinding.RvItemClassicsBinding
import com.example.azfilm.ui.activities.MainActivity.Companion.navGraphTracker
import com.example.azfilm.ui.adapters.GenericRvAdapter
import com.example.azfilm.ui.models.MovieInfoDetailed
import com.example.azfilm.ui.models.MoviesRepository
import com.example.azfilm.ui.models.MovieInfoMinimalistic
import com.example.azfilm.ui.models.RetrofitInstance
import com.example.azfilm.ui.viewmodels.HomeViewModel
import com.example.azfilm.ui.viewmodels.HomeViewModelFactory
import com.google.android.play.integrity.internal.i
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

class HomeFragment:BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    lateinit var auth: FirebaseAuth
    lateinit var homeViewModel: HomeViewModel
    var user: FirebaseUser? = null


    val homeFilmClickListener:(MovieInfoMinimalistic)->Unit = {
        homeViewModel.getMovieById(it.id)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        auth = FirebaseAuth.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        user = auth.currentUser
        homeViewModel.setNullToSelectedFilm()

        viewBinding.tvGreeting.text = "Salam, ${user?.displayName}"

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
                root.setOnClickListener{
                    homeFilmClickListener(movie)
                }
            }
        }

        viewBinding.rvRecents.apply {
            this.adapter  = adapter
            layoutManager  = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }



        homeViewModel.films.observe(viewLifecycleOwner){
            if (it != null) {
                adapter.sendListToAdapter(it)
            }
        }

        homeViewModel.selectedFilm.observe(viewLifecycleOwner){
            if(it!=null) {
                val bundle = Bundle()
                bundle.putSerializable("movie", it)
                findNavController().navigate(R.id.movieFragment, bundle)
            }
        }



        viewBinding.btnLogout.setOnClickListener {
            auth.signOut()
            navGraphTracker.setNavGraph(R.navigation.auth_nav_graph)

        }

        return  viewBinding.root
    }
}