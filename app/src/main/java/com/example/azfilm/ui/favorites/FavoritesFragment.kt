package com.example.azfilm.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.azfilm.AzFilmApplication
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.data.FavoriteMovie
import com.example.azfilm.databinding.FragmentFavoritesBinding
import com.example.azfilm.databinding.RvItemFavoritesBinding
import com.example.azfilm.ui.adapters.GenericRvAdapter
import com.example.azfilm.utils.MovieHelper
import com.example.azfilm.utils.MovieHelper.Companion.generateImageFullPath
import com.example.azfilm.utils.PosterSizes

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private  val favoritesViewModel: FavoritesViewModel by activityViewModels()
    private lateinit var favoritesAdapter: GenericRvAdapter<FavoriteMovie,RvItemFavoritesBinding>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Setup RecyclerView and adapter
        favoritesAdapter = GenericRvAdapter<FavoriteMovie,RvItemFavoritesBinding>(
            RvItemFavoritesBinding::inflate
        ) {
            binding,movie,position->
            binding.apply {
                tvFavoriteFilmName.text = movie.title
                tvAddedTime.text = "Added at ${movie.addedDate} "
                imgFavoriteFilm.load(movie.backdropPath?.generateImageFullPath())
                println("image_path"+movie.backdropPath?.generateImageFullPath())

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
