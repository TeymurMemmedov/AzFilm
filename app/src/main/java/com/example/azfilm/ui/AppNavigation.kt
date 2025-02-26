package com.example.azfilm.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.azfilm.ui.auth.LoginScreen
import com.example.azfilm.ui.home.FavoritesScreen
import com.example.azfilm.ui.home.HomeScreen
import com.example.azfilm.ui.home.ProfileScreen
import com.example.azfilm.ui.home.SearchScreen
import com.example.azfilm.ui.welcome.WelcomeScreen

sealed class MainRoutes(val route:String){
    data object Home:MainRoutes("Home")
    data object Favorites:MainRoutes("Favorites")
    data object Search:MainRoutes("Search")
    data object Profile:MainRoutes("Profile")
}

sealed class AuthRoutes(val route:String){
    data object Welcome:AuthRoutes("Welcome")
    data object Login:AuthRoutes("Home")
    data object Register:AuthRoutes("Favorites")
    data object CheckYourEmail:AuthRoutes("Search")


}


fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(startDestination = "welcome", route = "auth") {
        composable(AuthRoutes.Welcome.route) { WelcomeScreen(navController = navController) }
        composable(AuthRoutes.Login.route) { LoginScreen(navController = navController) }
    }
}

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation(startDestination = "home", route = "main") {
        composable(MainRoutes.Home.route) { HomeScreen(navController) }
        composable(MainRoutes.Favorites.route) { FavoritesScreen(navController) }
        composable(MainRoutes.Search.route) { SearchScreen(navController) }
        composable(MainRoutes.Profile.route) { ProfileScreen(navController) }
    }
}

@Composable
fun AppNavigation(
    userIsLoggedIn:Boolean
) {
    val navController = rememberNavController()
    val startDestination = if (userIsLoggedIn) "main" else "auth"

    NavHost(navController = navController, startDestination = startDestination) {
        authNavGraph(navController)
        mainNavGraph(navController)
    }
}


