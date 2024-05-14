package com.example.azfilm.ui.fragments

import LoginViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.azfilm.R
import com.example.azfilm.databinding.FragmentLoginBinding
import com.example.azfilm.ui.activities.MainActivity.Companion.navGraphTracker
import com.example.azfilm.ui.utils.UIHelper
import com.google.firebase.auth.FirebaseAuth

class LoginFragment:BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    lateinit var auth: FirebaseAuth
    lateinit var viewModel: LoginViewModel

    var isPasswordHided = true


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        viewBinding.apply {

            //hideShowPassword
            btnHideOrShowPassword.setOnClickListener {
                val thisButton = it as ImageButton
                UIHelper.hideShowPassword(evPassword,thisButton)
            }

            //Go to register
            btnGotoRegister.setOnClickListener {
                findNavController().navigate(R.id.registerFragment)
            }
        }


        viewModel.emailError.observe(viewLifecycleOwner) { error ->
            viewBinding.evEmail.error = error
        }

        viewModel.passwordError.observe(viewLifecycleOwner) { error ->
            viewBinding.evPassword.error = error
        }

        viewBinding.btnSubmit.setOnClickListener {
            val email = viewBinding.evEmail.text.toString()
            val password = viewBinding.evPassword.text.toString()

            if (viewModel.validateFields(email, password)) {
                // Validation passed, proceed with authentication
                viewModel.signInWithEmailAndPassword(
                    email,
                    password,
                    onSuccess = {
                        navGraphTracker.setNavGraph(R.navigation.main_nav_graph)
                    },
                    onFailure = { errorMessage ->
                        Log.d("loginResult", "signInWithEmail:failure: $errorMessage")
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    })
            }
        }
    }
}