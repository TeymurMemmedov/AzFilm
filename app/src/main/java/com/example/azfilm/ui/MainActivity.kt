package com.example.azfilm.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.azfilm.R
import com.example.azfilm.databinding.ActivityMainBinding
import com.example.azfilm.ui.auth.AuthViewModel
import com.example.azfilm.ui.favorites.FavoritesViewModel
import com.example.azfilm.ui.movie.MovieViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    @Inject
    lateinit var auth: FirebaseAuth


    val authViewModel: AuthViewModel by viewModels()
    val favoritesViewModel: FavoritesViewModel by viewModels()
    val movieViewModel :MovieViewModel by viewModels()

    companion object {
       lateinit var navGraphTracker: NavGraphTrackerViewModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivityyy","onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navGraphTracker = ViewModelProvider(this)[NavGraphTrackerViewModel::class.java]

        val navHost = supportFragmentManager.findFragmentById(R.id.main_fragment_navhost) as NavHostFragment
        navController = navHost.navController

        navGraphTracker.navGraphId.observe(this){
            navController.setGraph(it)
            if(it== R.navigation.main_nav_graph) {
                binding.bottomNavigationView.visibility = View.VISIBLE
                binding.appBar.visibility = View.VISIBLE

            }
            else{
                binding.bottomNavigationView.visibility = View.GONE
                binding.appBar.visibility = View.GONE

            }
        }

        binding.bottomNavigationView.setupWithNavController(navController)
        



    }

    override fun onStart() {
        super.onStart()

        binding.btnLogout.setOnClickListener {
            authViewModel.signOut()
            navGraphTracker.setNavGraph(R.navigation.auth_nav_graph)

        }

        Log.d("MainActivityyy","onStart")
        Log.d("is user null?Start","${auth.currentUser?.email}")
        if(auth.currentUser!=null){
            navGraphTracker.setNavGraph(R.navigation.main_nav_graph)
        }
        else{
            navGraphTracker.setNavGraph(R.navigation.auth_nav_graph)
        }
    }



    override fun onResume() {
        super.onResume()
        Log.d("is user null?Resume","${auth.currentUser?.email}")
        Log.d("MainActivityyy", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("is user null?Pause","${auth.currentUser?.email}")
        Log.d("MainActivityyy", "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivityyy", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivityyy", "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("is user null? Restart","${auth.currentUser?.email}")
        Log.d("MainActivityyy", "onRestart")
    }

}