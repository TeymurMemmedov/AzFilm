package com.example.azfilm.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.azfilm.data.UserRepository
import com.example.azfilm.utils.AuthResultWrapper
import com.example.azfilm.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private  val userRepository: UserRepository

): ViewModel() {

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


    fun validateFields(username:String?,email:String?,password:String?):Boolean {
        var isValid = true

        if (username?.isBlank() == true) {
            _usernameError.value = "Email must be entered"
            isValid = false
        }

        if (email?.isBlank()==true) {
            _emailError.value = "Email must be entered"
            isValid = false
        }

        if (password?.isBlank()==true) {
            _passwordError.value = "Password must be entered"
            isValid = false
        }

        return  isValid
    }


    fun resetErrors(){
        _emailError.value = null
        _passwordError.value = null
        _usernameError.value = null
    }

    fun registerUser(username: String, email: String, password: String) {
        viewModelScope.launch {
            _registrationResult.postValue(AuthResultWrapper.Loading)
            val result = userRepository.registerWithEmail(username, email, password)
            _registrationResult.postValue(result)
        }
    }

    fun signInUser(email: String, password: String) {
        viewModelScope.launch {
            _registrationResult.postValue(AuthResultWrapper.Loading)
            val result = userRepository.signInWithEmailAndPassword(email, password)
            _loginResult.postValue(result)
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _loginResult.postValue(AuthResultWrapper.Loading)
            val result = userRepository.signInWithGoogle(idToken)
            _loginResult.postValue(result)
        }
    }

    fun signOut(){
       userRepository.signOut()
        _loginResult.postValue(AuthResultWrapper.Logout)
        _registrationResult.postValue(AuthResultWrapper.Logout)
    }

}


