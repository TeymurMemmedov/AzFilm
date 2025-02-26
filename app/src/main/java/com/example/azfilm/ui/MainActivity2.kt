package com.example.azfilm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.azfilm.R
import com.example.azfilm.ui.MainActivity.Companion
import com.example.azfilm.ui.auth.AuthViewModel
import com.example.azfilm.ui.favorites.FavoritesViewModel
import com.example.azfilm.ui.home.FavoritesScreen
import com.example.azfilm.ui.home.HomeScreen
import com.example.azfilm.ui.home.ProfileScreen
import com.example.azfilm.ui.home.SearchScreen
import com.example.azfilm.ui.movie.MovieViewModel
import com.example.azfilm.ui.search.SearchViewModel
import com.example.azfilm.ui.ui.theme.AzFilmTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity2 : ComponentActivity() {
    @Inject
    lateinit var auth: FirebaseAuth

    private val authViewModel: AuthViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private val movieViewModel: MovieViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    companion object {
        lateinit var navGraphTracker: NavGraphTrackerViewModel
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navGraphTracker = ViewModelProvider(this)[NavGraphTrackerViewModel::class.java]
//
//        MainActivity.navGraphTracker.navGraphId.observe(this){
//            navController.setGraph(it)
//            if(it== R.navigation.main_nav_graph) {
//                binding.bottomNavigationView.visibility = View.VISIBLE
//            }
//            else{
//                binding.bottomNavigationView.visibility = View.GONE
//
//
//            }
//        }

        val isUserLoggedIn = false

        setContent {
            AzFilmTheme {
                if(isUserLoggedIn)
                    MainScreen()
                else
                    AuthScreen()
            }

        }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AzFilmTheme {
        MainScreen()
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
) {
    val homeTab = TabBarItem(
        title = MainRoutes.Home.route, // ✅ Correct usage
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
    val favoritesTab = TabBarItem(
        title = MainRoutes.Favorites.route, // ✅ Use MainRoutes.Favorites.route
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.Favorite,
        badgeAmount = 7
    )
    val searchTab = TabBarItem(
        title = MainRoutes.Search.route, // ✅ Use MainRoutes.Search.route
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    )
    val profileTab = TabBarItem(
        title = MainRoutes.Profile.route, // ✅ Use MainRoutes.Profile.route
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle
    )


    val tabBarItems = listOf(homeTab, favoritesTab, searchTab, profileTab)

    val navController = rememberNavController()


    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = { TabView(tabBarItems, navController) }
        ) {   // ✅ innerPadding is received here
            NavHost(
                navController = navController,
                startDestination = "main",
                modifier = Modifier.padding(8.dp) // ✅ Apply padding
            ) {
                mainNavGraph(navController)
            }
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScreen(){

    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold {
            NavHost(navController = navController, startDestination = "auth") { // ✅ Change to "main"
                authNavGraph(navController) // ✅ This now properly registers "main" as the start
            }
        }
    }
}


