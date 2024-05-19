package com.example.azfilm.ui.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.azfilm.R
import com.example.azfilm.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var auth : FirebaseAuth

    companion object {
       lateinit var navGraphTracker: NavGraphTrackerViewModel
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivityyy","onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navGraphTracker = ViewModelProvider(this)[NavGraphTrackerViewModel::class.java]

        auth = FirebaseAuth.getInstance()
        val navHost = supportFragmentManager.findFragmentById(R.id.main_fragment_navhost) as NavHostFragment
        navController = navHost.navController

        binding.bottomNavigationView.apply {
//            isItemActiveIndicatorEnabled = false

        }

//        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
//            val selectedColor = if (menuItem.isChecked) {
//                ContextCompat.getColorStateList(this,R.color.white)
//            } else {
//                ContextCompat.getColorStateList(this,R.color.black)
//            }
//            binding.bottomNavigationView.itemIconTintList = selectedColor
//            true
//        }

//       binding.bottomNavigationView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener{
//           override fun onNavigationItemSelected(item: MenuItem): Boolean {
//              item.iconTintList = ContextCompat.getColorStateList(this@MainActivity, R.color.white)
//               return true
//           }
//
//       })

        navGraphTracker.navGraphId.observe(this){
            navController.setGraph(it)
            if(it==R.navigation.main_nav_graph)
                binding.bottomNavigationView.visibility = View.VISIBLE
            else
                binding.bottomNavigationView.visibility = View.GONE

        }

        binding.bottomNavigationView.setupWithNavController(navController)

    }

    override fun onStart() {
        super.onStart()
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