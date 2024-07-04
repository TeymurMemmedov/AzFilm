package com.example.azfilm.data

import android.util.Log
import com.example.azfilm.utils.AuthResultWrapper
import com.example.azfilm.utils.safeAuthRequest
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthOperations @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val movieRepository: MovieRepository

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
            firebaseUser?.sendEmailVerification()?.await() // Send verification email
        }
    }

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResultWrapper<Unit> {

      safeAuthRequest(Dispatchers.IO){
            auth.signInWithEmailAndPassword(email, password).await()
        }
        val firebaseUser = auth.currentUser
        return if (firebaseUser?.isEmailVerified == true) {
            Log.d("FIREBASE_USER","SUCCESS")
            AuthResultWrapper.Success(Unit)
        } else {
            auth.signOut()
            AuthResultWrapper.GenericError(null,"Email not verified. Please check your inbox.")
        }
    }


    suspend fun deleteUserAccount(user: FirebaseUser): AuthResultWrapper<Unit> {
        return safeAuthRequest(Dispatchers.IO) {
                user.delete().await()
        }
    }

    suspend fun reauthenticateUser(user: FirebaseUser, credential: AuthCredential): AuthResultWrapper<Unit> {
        return safeAuthRequest(Dispatchers.IO) {
                user.reauthenticate(credential).await()
        }
    }



    suspend fun signInWithGoogle(idToken: String): AuthResultWrapper<Unit> {
        return safeAuthRequest(Dispatchers.IO) {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).await()
        }
    }

    fun signOut() {
        movieRepository.removeFavoritesListener()
        auth.signOut()
        googleSignInClient.signOut()
    }
}
