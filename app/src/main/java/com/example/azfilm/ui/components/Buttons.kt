package com.example.azfilm.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButtonWithText(text:String, color:Color, onClick:()->Unit){
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonColors(color, Color.White,Color.Gray,Color.Gray),
        contentPadding = PaddingValues(0.dp,16.dp)
        ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.W600
        )

    }
}


@Composable
fun CustomButtonWithDrawableIcon(
    onClick: () -> Unit,
    iconResId: Int, // Drawable resource ID
    contentDescription: String? = null,
    modifier: Modifier,
    color:Color
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp), // Rounded corners
        modifier = modifier.size(56.dp), // Square button
        contentPadding = PaddingValues(0.dp), // Remove extra padding
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )

    ) {
        Icon(
            painter = painterResource(id = iconResId), // Load drawable
            contentDescription = contentDescription,
            tint = Color.White, // Set tint (remove for original color)
            modifier = Modifier.size(24.dp) // Adjust icon size
        )
    }
}

