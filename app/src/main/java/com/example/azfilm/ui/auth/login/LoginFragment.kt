package com.example.azfilm.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.azfilm.R
import com.example.azfilm.databinding.FragmentLoginBinding
import com.example.azfilm.ui.MainActivity.Companion.navGraphTracker
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.utils.UIHelper
import com.google.firebase.auth.FirebaseAuth

class LoginFragment: BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    lateinit var auth: FirebaseAuth
    lateinit var viewModel: LoginViewModel

    var isPasswordHided = true


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        binding.apply {

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
            binding.evEmail.error = error
        }

        viewModel.passwordError.observe(viewLifecycleOwner) { error ->
            binding.evPassword.error = error
        }

        binding.btnSubmit.setOnClickListener {
            val email = binding.evEmail.text.toString()
            val password = binding.evPassword.text.toString()

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