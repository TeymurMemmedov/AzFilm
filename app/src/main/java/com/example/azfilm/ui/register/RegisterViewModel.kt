package com.example.azfilm.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterViewModel:ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _usernameError = MutableLiveData<String?>(null)
    val usernameError: LiveData<String?> = _usernameError

    private val _emailError = MutableLiveData<String?>(null)
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>(null)
    val passwordError: LiveData<String?> = _passwordError

    fun validateFields(username:String,email:String,password:String):Boolean {
        var isValid = true

        if (username.isBlank()) {
            _usernameError.value = "Email must be entered"
            isValid = false
        } else {
            _emailError.value = null
        }

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

    fun createUserWithEmailAndPassword(
        username: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User created successfully, set username
                    val firebaseUser = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()

                    firebaseUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                onSuccess()
                            } else {
                                onFailure(profileTask.exception?.message ?: "Failed to update profile.")
                            }
                        }
                } else {
                    onFailure(task.exception?.message ?: "Authentication failed.")
                }
            }
    }

}