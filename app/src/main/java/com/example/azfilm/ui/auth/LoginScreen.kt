package com.example.azfilm.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.azfilm.R
import com.example.azfilm.ui.components.AppNameText

import com.example.azfilm.ui.components.AuthOperationsText
import com.example.azfilm.ui.components.AuthTextField
import com.example.azfilm.ui.ui.theme.AzFilmTheme
import com.example.azfilm.utils.SpaceVertical

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordHidden by remember { mutableStateOf(true) } // Lifted state

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpaceVertical(size = 80)
        AppNameText()
        SpaceVertical(size = 64)
        AuthOperationsText(text = "Log in")
        SpaceVertical(size = 48)

        // Email Field
        AuthTextField(
            value = email,
            hint = "Email address",
            onValueChange = { email = it }
        )

        SpaceVertical(size = 20)

        // Password Field with Controlled Visibility
        AuthTextField(
            value = password,
            hint = "Password",
            onValueChange = { password = it },
            isPassword = true,
            isPasswordHidden = isPasswordHidden,
            onPasswordToggle = { isPasswordHidden = !isPasswordHidden } // Toggle visibility
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLogin(){
    AzFilmTheme {
        LoginScreen(navController = rememberNavController())
    }
}