package com.example.azfilm.data

import com.example.azfilm.utils.ResultWrapper
import com.example.azfilm.utils.safeApiCall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
) {

    suspend fun registerWithEmail(
        username: String,
        email: String,
        password: String
    ): ResultWrapper<Unit> {
        return safeApiCall(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = auth.currentUser
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build()
            firebaseUser?.updateProfile(profileUpdates)?.await()
        }
    }

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): ResultWrapper<Unit> {
        return safeApiCall(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, password).await()
        }
    }


    fun signOut(){
        auth.signOut()
    }
}
