package com.example.azfilm.api.serviceModels

data class Credits(
    val cast: List<Cast?>?,
    val crew: List<Crew?>?
)