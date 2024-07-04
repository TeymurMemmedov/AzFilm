package com.example.azfilm.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.azfilm.R
import com.example.azfilm.base.BaseFragment
import com.example.azfilm.databinding.FragmentCheckYourEmailBinding

class CheckYourEmailFragment:BaseFragment<FragmentCheckYourEmailBinding>(
    FragmentCheckYourEmailBinding::inflate
) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.btnGotoEmail.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        return  binding.root
    }
}