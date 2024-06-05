package com.example.azfilm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class FavoriteMovie (
    @PrimaryKey val id:Int,
    val backdropPath:String?,
    val title : String,
    val addedDate: String
)