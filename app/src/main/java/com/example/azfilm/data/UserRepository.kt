package com.example.azfilm.data

import com.example.azfilm.utils.AuthResultWrapper
import com.example.azfilm.utils.ResultWrapper
import com.example.azfilm.utils.safeApiCall
import com.example.azfilm.utils.safeAuthRequest
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
) {

    suspend fun registerWithEmail(
        username: String,
        email: String,
        password: String
    ): AuthResultWrapper<Unit> {
        return safeAuthRequest(Dispatchers.IO) {
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
    ): AuthResultWrapper<Unit> {
        return safeAuthRequest(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, password).await()
        }
    }

    suspend fun signInWithGoogle(idToken: String): AuthResultWrapper<Unit> {
        return safeAuthRequest(Dispatchers.IO) {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).await()
        }
    }


    fun signOut(){
        auth.signOut()
        googleSignInClient.signOut()

    }
}
