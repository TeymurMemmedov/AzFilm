package com.example.azfilm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.azfilm.R
import com.example.azfilm.ui.ui.theme.Gray100
import com.example.azfilm.ui.ui.theme.Gray200
import com.example.azfilm.ui.ui.theme.Gray800
import com.example.azfilm.ui.ui.theme.Red500



@Composable
fun AuthTextField(
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    isPasswordHidden: Boolean = true, // Pass state from parent
    onPasswordToggle: (() -> Unit)? = null // Callback to toggle password visibility
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = Color.White
        ),
        label = { Text(hint, color = Gray200) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Gray100,
            unfocusedIndicatorColor = Gray100
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth(0.9f),
        visualTransformation = if (isPassword && isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (isPassword) {
            {
                val image = if (isPasswordHidden) {
                    painterResource(id = R.drawable.icon_visibility_off) // Closed eye icon
                } else {
                    painterResource(id = R.drawable.icon_visibility) // Open eye icon
                }

                IconButton(onClick = { onPasswordToggle?.invoke() }) {
                    Icon(painter = image, contentDescription = if (isPasswordHidden) "Show Password" else "Hide Password")
                }
            }
        } else null
    )
}



