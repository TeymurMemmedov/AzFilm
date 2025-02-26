package com.example.azfilm.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController){
    Text(text = "HOME")
}

@Composable
fun FavoritesScreen(navController: NavController){
    Text(text = "FAVORITES")
}

@Composable
fun ProfileScreen(navController: NavController){
    Text(text = "PROFILES")
}



@Composable
fun SearchScreen(navController: NavController){
    Text(text = "search")
}