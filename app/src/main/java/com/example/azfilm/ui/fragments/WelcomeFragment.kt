package com.example.azfilm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.azfilm.R
import com.example.azfilm.databinding.FragmentWelcomeBinding

class WelcomeFragment:BaseFragment<FragmentWelcomeBinding>(
    FragmentWelcomeBinding::inflate
) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewBinding.btnToCustomLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        return viewBinding.root
    }
}