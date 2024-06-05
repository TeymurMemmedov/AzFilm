package com.example.azfilm.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.azfilm.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class AuthViewModel(
    private  val userRepository: UserRepository

): ViewModel() {

    suspend fun register(
        username: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        userRepository.registerWithEmail(username,email,password,onSuccess,onFailure)
    }

    suspend fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ){
        userRepository.signInWithEmailAndPassword(email,password,onSuccess,onFailure)
    }

}


class AuthViewModelFactory(
    private val userRepository: UserRepository
):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(userRepository) as T
    }
}
