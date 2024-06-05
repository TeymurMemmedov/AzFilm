package com.example.azfilm.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _emailError = MutableLiveData<String?>(null)
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>(null)
    val passwordError: LiveData<String?> = _passwordError




    fun validateFields(email: String, password: String): Boolean {
        var isValid = true

        if (email.isBlank()) {
            _emailError.value = "Email must be entered"
            isValid = false
        } else {
            _emailError.value = null
        }

        if (password.isBlank()) {
            _passwordError.value = "Password must be entered"
            isValid = false
        } else {
            _passwordError.value = null
        }
        return  isValid
    }

    fun signInWithEmailAndPassword(
            email: String,
            password: String,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onFailure(task.exception?.message ?: "Authentication failed.")
                    }
                }
    }


}

