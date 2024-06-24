package com.example.azfilm.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.azfilm.R
import com.example.azfilm.databinding.FragmentWelcomeBinding
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.ui.MainActivity
import com.example.azfilm.ui.auth.AuthViewModel
import com.example.azfilm.utils.AuthResultWrapper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment: BaseFragment<FragmentWelcomeBinding>(
    FragmentWelcomeBinding::inflate
) {

    companion object {
        const val GOOGLE_SIGN_IN = 1903
    }

    @Inject
    lateinit var auth:FirebaseAuth

    @Inject
    lateinit var gsc : GoogleSignInClient

    val authViewModel:AuthViewModel by activityViewModels()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnToGoogleLogin.setOnClickListener { signIn() }
    }

    private fun signIn() {
        val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                authViewModel.signInWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                //handle error
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.btnToCustomLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        authViewModel.loginResult.observe(viewLifecycleOwner){
            when (it) {
                is AuthResultWrapper.Loading->{
                    Toast.makeText(requireContext(), "Login Processing", Toast.LENGTH_SHORT).show()
                }
                is AuthResultWrapper.Success -> {
                    MainActivity.navGraphTracker.setNavGraph(R.navigation.main_nav_graph)
                }
                is AuthResultWrapper.GenericError -> {
                    Toast.makeText(requireContext(), it.error ?: "Login failed", Toast.LENGTH_SHORT).show()
                }
                is AuthResultWrapper.NetworkError -> {
                    Toast.makeText(requireContext(), "Network error, please try again", Toast.LENGTH_SHORT).show()
                }
                is AuthResultWrapper.Logout->{
                    MainActivity.navGraphTracker.setNavGraph(R.navigation.auth_nav_graph)
                }

            }
        }
        return binding.root
    }
}