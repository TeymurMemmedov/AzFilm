package com.example.azfilm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.azfilm.R
import com.example.azfilm.databinding.FragmentRegisterBinding
import com.example.azfilm.ui.activities.MainActivity.Companion.navGraphTracker
import com.example.azfilm.ui.utils.UIHelper
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment:BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {

    lateinit var auth:FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        viewBinding.btnHideOrShowPassword.setOnClickListener {
            val thisButton = it as ImageButton
            UIHelper.hideShowPassword(viewBinding.evPassword,it)
        }

        viewBinding.apply {
            btnSubmit.setOnClickListener {
                val email = evEmail.text.toString()
                val password = evPassword.text.toString()



                if (email.isNullOrBlank()) Toast.makeText(requireContext(),"Email must be entered",Toast.LENGTH_LONG).show()
                else if (password.isNullOrBlank()) Toast.makeText(requireContext(),"Password must be entered",Toast.LENGTH_LONG)
                else{
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("registerResult", "createUserWithEmail:success")
                                val user = auth.currentUser

                                Toast.makeText(
                                    requireContext(),
                                    "Register Success.",
                                    Toast.LENGTH_SHORT,
                                ).show()

                                navGraphTracker.setNavGraph(R.navigation.main_nav_graph)

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("registerResult", "createUserWithEmail:failure", task.exception)
                                Toast.makeText(
                                    requireContext(),
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                }

            }
        }

        viewBinding.btnGotoLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }
}