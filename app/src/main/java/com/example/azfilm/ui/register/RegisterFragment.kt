package com.example.azfilm.ui.register

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.azfilm.R
import com.example.azfilm.databinding.FragmentRegisterBinding
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.ui.activities.MainActivity
import com.example.azfilm.ui.activities.MainActivity.Companion.navGraphTracker
import com.example.azfilm.utils.UIHelper

class RegisterFragment: BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {

    lateinit var registerViewModel: RegisterViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]


        viewBinding.btnHideOrShowPassword.setOnClickListener {
            val thisButton = it as ImageButton
            UIHelper.hideShowPassword(viewBinding.evPassword,it)
        }

        viewBinding.apply {
            btnSubmit.setOnClickListener {
                val username = evUsername.text.toString()
                val email = evEmail.text.toString()
                val password = evPassword.text.toString()


                if(registerViewModel.validateFields(username,email,password)){
                    registerViewModel.createUserWithEmailAndPassword(
                        username,email,password,
                        onSuccess = { navGraphTracker.setNavGraph(R.navigation.main_nav_graph)},
                        onFailure = {str->Log.d("registerError",str)}
                    )
                }

            }
        }

        viewBinding.btnGotoLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }
}