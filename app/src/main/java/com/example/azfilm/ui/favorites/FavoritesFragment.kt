package com.example.azfilm.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.azfilm.AzFilmApplication
import com.example.azfilm.databinding.FragmentFavoritesBinding
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.data.FavoriteMovie
import com.example.azfilm.databinding.RvItemFavoritesBinding
import com.example.azfilm.ui.adapters.GenericRvAdapter
import com.example.azfilm.utils.MovieHelper
import com.example.azfilm.utils.PosterSizes

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var favoritesAdapter: GenericRvAdapter<FavoriteMovie,RvItemFavoritesBinding>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel
        val context = requireActivity().application as AzFilmApplication
        val factory = FavoritesViewModelFactory(context.repository)
        favoritesViewModel = ViewModelProvider(this, factory).get(FavoritesViewModel::class.java)

        // Setup RecyclerView and adapter
        favoritesAdapter = GenericRvAdapter<FavoriteMovie,RvItemFavoritesBinding>(
            RvItemFavoritesBinding::inflate
        ) {
            binding,movie,position->
            binding.apply {
                tvFavoriteFilmName.text = movie.title
                tvAddedTime.text = "xx.xx.xxxx tarixində əlavə edilmişdir"
                imgFavoriteFilm.load("${ MovieHelper.tmdbImageBaseUrl}${ PosterSizes.W_500.printableName}${movie.backdropPath}")

            }


        }

        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = favoritesAdapter
        }

        // Observe favorite movies
        favoritesViewModel.favoriteMovies.observe(viewLifecycleOwner){
            favoritesAdapter.sendListToAdapter(it)
        }
    }
}
