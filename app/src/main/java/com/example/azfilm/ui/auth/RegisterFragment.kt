package com.example.azfilm.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.azfilm.R
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.databinding.FragmentRegisterBinding
import com.example.azfilm.ui.MainActivity.Companion.navGraphTracker
import com.example.azfilm.utils.ResultWrapper
import com.example.azfilm.utils.UIHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {

     val registerViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnHideOrShowPassword.setOnClickListener {
            val thisButton = it as ImageButton
            UIHelper.hideShowPassword(binding.evPassword,it)
        }


        binding.btnGotoLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.btnSubmit.setOnClickListener {
            val username = binding.evUsername.text.toString()
            val email = binding.evEmail.text.toString()
            val password = binding.evPassword.text.toString()

            if(registerViewModel.validateFields(username,email,password)){
                registerViewModel.registerUser(username, email, password)
            }

        }

        registerViewModel.emailError.observe(viewLifecycleOwner) { error ->
            binding.evEmail.error = error
        }

        registerViewModel.passwordError.observe(viewLifecycleOwner) { error ->
            binding.evPassword.error = error
        }

        registerViewModel.usernameError.observe(viewLifecycleOwner) { error ->
            binding.evUsername.error = error
        }


        registerViewModel.registrationResult.observe(viewLifecycleOwner){
            when (it) {
                is ResultWrapper.Loading->{
                    Toast.makeText(requireContext(), "Registration loading", Toast.LENGTH_SHORT).show()
                }
                is ResultWrapper.Success -> {
                    navGraphTracker.setNavGraph(R.navigation.main_nav_graph)
                }
                is ResultWrapper.GenericError -> {
                    Toast.makeText(requireContext(), it.error ?: "Registration failed", Toast.LENGTH_SHORT).show()
                }
                is ResultWrapper.NetworkError -> {
                    Toast.makeText(requireContext(), "Network error, please try again", Toast.LENGTH_SHORT).show()
                }
                else -> Unit // Handle other states if necessary
            }
        }
    }
}