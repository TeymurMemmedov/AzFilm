package com.example.azfilm.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.azfilm.R
import com.example.azfilm.databinding.FragmentProfileBinding
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.ui.MainActivity
import com.example.azfilm.ui.auth.AuthViewModel
import com.example.azfilm.ui.favorites.FavoritesViewModel
import com.example.azfilm.utils.AuthResultWrapper
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {

    private val authViewModel: AuthViewModel by activityViewModels()
    private val favoritesViewModel:FavoritesViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDeleteUser.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                authViewModel.deleteUserAccount(user)
            }
        }

        authViewModel.deleteAccountResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is AuthResultWrapper.Loading -> {
                    Log.d("ACCOUNT_DELETION","${result::class.java}")
                    Toast.makeText(requireContext(), "Deleting Account...", Toast.LENGTH_SHORT).show()
                }
                is AuthResultWrapper.Success -> {
                    Log.d("ACCOUNT_DELETION","${result::class.java}")
                    Toast.makeText(requireContext(), "Account Deleted", Toast.LENGTH_SHORT).show()

                    authViewModel.signOut()
                    MainActivity.navGraphTracker.setNavGraph(R.navigation.auth_nav_graph)
                    // Navigate to login or other appropriate screen
                }
                is AuthResultWrapper.ReAuthRequired -> {
                    Log.d("ACCOUNT_DELETION","${result::class.java}")
                    showReAuthDialog()
                }
                is AuthResultWrapper.GenericError -> {
                    Log.d("ACCOUNT_DELETION","${result::class.java}")
                    Toast.makeText(requireContext(), result.error ?: "Error Deleting Account", Toast.LENGTH_SHORT).show()
                }
                is AuthResultWrapper.NetworkError -> {
                    Log.d("ACCOUNT_DELETION","${result::class.java}")
                    Toast.makeText(requireContext(), "Network Error, Please Try Again", Toast.LENGTH_SHORT).show()
                }
                else -> Log.d("ACCOUNT_DELETION","${result::class.java}")
            }
        }
    }

    private fun showReAuthDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_reauth, null)
        val passwordInput = dialogView.findViewById<EditText>(R.id.password_input)

        AlertDialog.Builder(requireContext())
            .setTitle("Re-Authenticate")
            .setMessage("Please re-enter your password to continue.")
            .setView(dialogView)
            .setPositiveButton("Confirm") { _, _ ->
                val password = passwordInput.text.toString()
                if (password.isNotEmpty()) {
                    reauthenticateUser(password)
                } else {
                    Toast.makeText(requireContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun reauthenticateUser(password: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email
        if (email != null && user != null) {
            val credential = EmailAuthProvider.getCredential(email, password)
            authViewModel.reauthenticateAndDeleteUser(user, credential)
        }
    }
}
