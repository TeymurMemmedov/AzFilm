package com.example.azfilm.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MarginHorizontal(size:Int){
    Spacer(modifier = Modifier.width(size.dp))
}

@Composable
fun SpaceVertical(size:Int){
    Spacer(modifier = Modifier.height(size.dp))
}

