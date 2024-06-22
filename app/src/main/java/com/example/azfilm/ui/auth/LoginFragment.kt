package com.example.azfilm.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.azfilm.R
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.databinding.FragmentLoginBinding
import com.example.azfilm.ui.MainActivity.Companion.navGraphTracker
import com.example.azfilm.utils.ResultWrapper
import com.example.azfilm.utils.UIHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    private val loginViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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



        binding.btnSubmit.setOnClickListener {
            val email = binding.evEmail.text.toString()
            val password = binding.evPassword.text.toString()
            loginViewModel.resetErrors()
            if (loginViewModel.validateFields(null, email, password)) {
                loginViewModel.signInUser(email, password)
            }
        }

        loginViewModel.emailError.observe(viewLifecycleOwner) { error ->
            binding.evEmail.error = error
        }

        loginViewModel.passwordError.observe(viewLifecycleOwner) { error ->
            binding.evPassword.error = error
        }



        loginViewModel.loginResult.observe(viewLifecycleOwner){
            when (it) {
                is ResultWrapper.Loading->{
                    Toast.makeText(requireContext(), "Login Processing", Toast.LENGTH_SHORT).show()
                }
                is ResultWrapper.Success -> {
                    navGraphTracker.setNavGraph(R.navigation.main_nav_graph)
                }
                is ResultWrapper.GenericError -> {
                    Toast.makeText(requireContext(), it.error ?: "Login failed", Toast.LENGTH_SHORT).show()
                }
                is ResultWrapper.NetworkError -> {
                    Toast.makeText(requireContext(), "Network error, please try again", Toast.LENGTH_SHORT).show()
                }

            }
        }


    }
}