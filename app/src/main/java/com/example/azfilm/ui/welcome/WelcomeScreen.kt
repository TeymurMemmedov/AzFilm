package com.example.azfilm.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.azfilm.R
import com.example.azfilm.ui.AuthRoutes
import com.example.azfilm.ui.components.AppNameText
import com.example.azfilm.ui.components.CustomButtonWithDrawableIcon

import com.example.azfilm.ui.components.CustomButtonWithText
import com.example.azfilm.ui.ui.theme.AzFilmTheme
import com.example.azfilm.ui.ui.theme.Gray800
import com.example.azfilm.ui.ui.theme.Red500
import com.example.azfilm.utils.SpaceVertical

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
){
    val painter = painterResource(id = R.drawable.img_welcome)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        AppNameText()


        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.img_welcome),
            contentDescription = "Welcome image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    drawContent() // Draw the image first
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Gray800, Color.Transparent, Gray800),
                            startY = 0f,
                            endY = size.height
                        )
                    ) // Draw the gradient overlay
                }
        )

        SpaceVertical(8)
        
        Text(
            modifier = Modifier.padding(20.dp,0.dp),
            text = stringResource(id = R.string.app_slogan),
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center,

        )
        
        SpaceVertical(size = 16)

        Spacer(modifier = Modifier.weight(1f))
        CustomButtonWithText(
            text = stringResource(id = R.string.link_to_custom_login),
            color = Red500) {

            navController.navigate(AuthRoutes.Login.route)
        }

        Spacer(modifier = Modifier.weight(1f))

        CustomButtonWithDrawableIcon(
            onClick = { /* Handle Click */ },
            iconResId = R.drawable.icon_google, // Your drawable icon
            contentDescription = "Custom Icon",
            Modifier.border(2.dp,Color.White, RoundedCornerShape(16.dp)),
            Color.Transparent

        )



    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    AzFilmTheme {
        WelcomeScreen(Modifier, rememberNavController())
    }
}

