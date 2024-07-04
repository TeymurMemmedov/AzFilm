package com.example.azfilm.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.azfilm.data.MovieRepository
import com.example.azfilm.data.AuthOperations
import com.example.azfilm.utils.AuthResultWrapper
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authOperations: AuthOperations,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _usernameError = MutableLiveData<String?>(null)
    val usernameError: LiveData<String?> = _usernameError

    private val _emailError = MutableLiveData<String?>(null)
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>(null)
    val passwordError: LiveData<String?> = _passwordError

    private val _registrationResult = MutableLiveData<AuthResultWrapper<Unit>>()
    val registrationResult: LiveData<AuthResultWrapper<Unit>> = _registrationResult

    private val _loginResult = MutableLiveData<AuthResultWrapper<Unit>>()
    val loginResult: LiveData<AuthResultWrapper<Unit>> = _loginResult

    private val _deleteAccountResult = MutableLiveData<AuthResultWrapper<Unit>>()
    val deleteAccountResult: LiveData<AuthResultWrapper<Unit>> = _deleteAccountResult

    fun validateFields(username: String?, email: String?, password: String?): Boolean {
        var isValid = true

        if (username?.isBlank() == true) {
            _usernameError.value = "Email must be entered"
            isValid = false
        }

        if (email?.isBlank() == true) {
            _emailError.value = "Email must be entered"
            isValid = false
        }

        if (password?.isBlank() == true) {
            _passwordError.value = "Password must be entered"
            isValid = false
        }

        return isValid
    }

    fun resetErrors() {
        _emailError.value = null
        _passwordError.value = null
        _usernameError.value = null
    }

    fun registerUser(username: String, email: String, password: String) {
        viewModelScope.launch {
            _registrationResult.postValue(AuthResultWrapper.Loading)
            val result = authOperations.registerWithEmail(username, email, password)
            _registrationResult.postValue(result)
        }
    }

    fun signInUser(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.postValue(AuthResultWrapper.Loading)
            val result = authOperations.signInWithEmailAndPassword(email, password)

            _loginResult.postValue(result)
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _loginResult.postValue(AuthResultWrapper.Loading)
            val result = authOperations.signInWithGoogle(idToken)
            _loginResult.postValue(result)
        }
    }

    fun signOut() {
        authOperations.signOut()
        _loginResult.postValue(AuthResultWrapper.Logout)
        _registrationResult.postValue(AuthResultWrapper.Logout)

    }

    fun deleteUserAccount(user: FirebaseUser) {
        viewModelScope.launch {
            _deleteAccountResult.postValue(AuthResultWrapper.Loading)
            val result = authOperations.deleteUserAccount(user)
            _deleteAccountResult.postValue(result)
        }
    }

    fun reauthenticateAndDeleteUser(user: FirebaseUser, credential: AuthCredential) {
        viewModelScope.launch {
            val reAuthResult = authOperations.reauthenticateUser(user, credential)
            if (reAuthResult is AuthResultWrapper.Success) {
                deleteUserAccount(user)
            } else {
                _deleteAccountResult.postValue(reAuthResult)
            }
        }
    }
}
