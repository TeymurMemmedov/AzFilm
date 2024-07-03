package com.example.azfilm.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth
) {

    private var favoritesListener: ValueEventListener? = null

    private fun getUserFavoritesReference(): DatabaseReference {
        val userId = auth.currentUser?.uid ?: throw IllegalStateException("User is not authenticated")
        return database.reference.child("users").child(userId).child("favorites")
    }

    fun getFavoriteMovies(callback: (List<FavoriteMovie>) -> Unit) {
        val reference = getUserFavoritesReference()
        favoritesListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favorites = snapshot.children.mapNotNull { it.getValue(FavoriteMovie::class.java) }
                callback(favorites)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        }
        reference.addValueEventListener(favoritesListener!!)
    }

    fun removeFavoritesListener() {
        favoritesListener?.let {
            getUserFavoritesReference().removeEventListener(it)
        }
        favoritesListener = null
    }

    fun addMovieToFavorites(movie: FavoriteMovie) {
        getUserFavoritesReference().child(movie.id.toString()).setValue(movie)
    }

    fun removeMovieFromFavorites(movie: FavoriteMovie) {
        getUserFavoritesReference().child(movie.id.toString()).removeValue()
    }

    fun isFavoriteMovie(movieId: Int, callback: (Boolean) -> Unit) {
        getUserFavoritesReference().child(movieId.toString()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot.exists())
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })
    }
}

