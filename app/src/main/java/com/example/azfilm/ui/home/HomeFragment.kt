package com.example.azfilm.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.azfilm.R
import com.example.azfilm.databinding.FragmentHomeBinding
import com.example.azfilm.databinding.RvMovieWithPosterBinding
import com.example.azfilm.ui.activities.MainActivity.Companion.navGraphTracker
import com.example.azfilm.ui.adapters.GenericRvAdapter
import com.example.azfilm.data.models.MovieInfoMinimalistic
import com.example.azfilm.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomeFragment: BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    lateinit var auth: FirebaseAuth
    val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )
    var user: FirebaseUser? = null

    @RequiresApi(Build.VERSION_CODES.O)
    val homeFilmClickListener:(MovieInfoMinimalistic)->Unit = {
        homeViewModel.getMovieById(it.id)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        Log.d("HomeFragment","onCreate")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        user = auth.currentUser
        Log.d("HomeFragment","onCreateView")

        homeViewModel.setNullToSelectedFilm()

        viewBinding.tvGreeting.text = "Hello, ${user?.displayName}"

        val bind:(RvMovieWithPosterBinding, MovieInfoMinimalistic, Int)->Unit =  { binding, movie, position ->
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

        val recentsAdapter = GenericRvAdapter<MovieInfoMinimalistic,RvMovieWithPosterBinding>(
            RvMovieWithPosterBinding::inflate,
            bind
        )


        val classicsAdapter = GenericRvAdapter<MovieInfoMinimalistic,RvMovieWithPosterBinding>(
            RvMovieWithPosterBinding::inflate,
            bind
        )

        val modernsAdapter = GenericRvAdapter<MovieInfoMinimalistic,RvMovieWithPosterBinding>(
            RvMovieWithPosterBinding::inflate,
            bind
        )

        val animationsAdapter = GenericRvAdapter<MovieInfoMinimalistic,RvMovieWithPosterBinding>(
            RvMovieWithPosterBinding::inflate,
            bind
        )
        viewBinding.apply {
            val commonLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

            rvRecents.apply { adapter = recentsAdapter; layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)  }
            rvClassics.apply { adapter = classicsAdapter; layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)  }
            rvModerns.apply { adapter = modernsAdapter; layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)  }
            rvAnimations.apply { adapter = animationsAdapter; layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)  }
        }




        homeViewModel.recentFilms.observe(viewLifecycleOwner){
            if (it != null) {
                recentsAdapter.sendListToAdapter(it)
            }
        }
        homeViewModel.classics.observe(viewLifecycleOwner){
            if (it != null) {
                classicsAdapter.sendListToAdapter(it)
            }
        }

        homeViewModel.moderns.observe(viewLifecycleOwner){
            if (it != null) {
                modernsAdapter.sendListToAdapter(it)
            }
        }
        homeViewModel.animations.observe(viewLifecycleOwner){
            if (it != null) {
                animationsAdapter.sendListToAdapter(it)
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


