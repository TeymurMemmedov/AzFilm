package com.example.azfilm.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.azfilm.R
import com.example.azfilm.databinding.ActivityMainBinding
import com.example.azfilm.ui.viewmodels.NavGraphTrackerViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var auth : FirebaseAuth
    var user: FirebaseUser? = null

    companion object {
       lateinit var navGraphTracker: NavGraphTrackerViewModel
    }

    override fun onStart() {
        super.onStart()
        Log.d("is user null?","${user?.email}")
        if(user!=null){
            navGraphTracker.setNavGraph(R.navigation.main_nav_graph)
        }
        else{
            navGraphTracker.setNavGraph(R.navigation.auth_nav_graph)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("mesaj","qaqa men yarandim")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navGraphTracker = ViewModelProvider(this)[NavGraphTrackerViewModel::class.java]

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
        val navHost = supportFragmentManager.findFragmentById(R.id.main_fragment_navhost) as NavHostFragment
        navController = navHost.navController


        navGraphTracker.navGraphId.observe(this){
            navController.setGraph(it)
            if(it==R.navigation.main_nav_graph)
                binding.bottomNavigationView.visibility = View.VISIBLE
            else
                binding.bottomNavigationView.visibility = View.GONE

        }

    }

    override fun onRestart() {
        super.onRestart()
        Log.d("mesaj","qaqa men yeniden yarandim")
    }



}