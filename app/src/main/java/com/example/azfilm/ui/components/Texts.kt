package com.example.azfilm.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.azfilm.ui.ui.theme.Red500

@Composable
fun AppNameText(){
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color= Color.White)){
                append("Az")
            }

            withStyle(style = SpanStyle(color= Red500)){
                append("Film")
            }
        },
        fontSize = 60.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun AuthOperationsText(
    text:String
){
    Text(
        text = text,
        fontSize = 30.sp,
        fontWeight = FontWeight.W700
    )

}