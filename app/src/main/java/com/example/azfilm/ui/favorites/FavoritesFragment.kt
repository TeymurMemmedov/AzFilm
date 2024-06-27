package com.example.azfilm.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.azfilm.R
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.data.FavoriteMovie
import com.example.azfilm.databinding.FragmentFavoritesBinding
import com.example.azfilm.databinding.RvItemFavoritesBinding
import com.example.azfilm.ui.adapters.GenericRvAdapter
import com.example.azfilm.ui.movie.MovieViewModel
import com.example.azfilm.utils.MovieHelper.Companion.generateImageFullPath

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val favoritesViewModel: FavoritesViewModel by activityViewModels()
    private val movieViewModel: MovieViewModel by activityViewModels()
    private lateinit var favoritesAdapter: GenericRvAdapter<FavoriteMovie, RvItemFavoritesBinding>

    private val favoriteClickListener: (FavoriteMovie) -> Unit = {
        movieViewModel.getMovieById(it.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        movieViewModel.setNullToSelectedFilm()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView and adapter
        favoritesAdapter = GenericRvAdapter(RvItemFavoritesBinding::inflate) { binding, movie, _ ->
            binding.apply {
                tvFavoriteFilmName.text = movie.title
                tvAddedTime.text = "Added at ${movie.addedDate}"
                imgFavoriteFilm.load(
                    if (movie.backdropPath.isNullOrBlank()) R.drawable.img_not_found_backdrop
                    else movie.backdropPath.generateImageFullPath()
                )
                btnRemoveFromFavorites.setOnClickListener {
                    favoritesViewModel.removeMovieFromFavorites(movie)
                }

                btnGoToMovie.setOnClickListener {
                    Log.d("Hey_salam","clicked")
                   favoriteClickListener(movie)
                }

            }
        }

        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }

        // Observe favorite movies
        favoritesViewModel.favoriteMovies.observe(viewLifecycleOwner) {
            favoritesAdapter.sendListToAdapter(it)
        }

        movieViewModel.selectedFilm.distinctUntilChanged().observe(viewLifecycleOwner) { film ->
            Log.d("Movie_Fragment", "Movie changed")
            if (film != null) {
                Log.d("Movie_Fragment", "MovieFragment navigated")
                movieViewModel.setInitialFavoriteState()
                val bundle = Bundle().apply {
                    putSerializable("movie", film)
                }
                findNavController().navigate(R.id.movieFragment, bundle)
            }
        }
    }
}
