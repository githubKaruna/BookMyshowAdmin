package com.neatroots.bookymyshowadmin.presentation.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Preview(showSystemUi = true)
@Composable
fun InternetScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Default.WifiOff,
            contentDescription = "Wi-Fi",
            modifier = Modifier.size(120.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Checking Internet Connection!",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "This app requires an internet connection. Please check your internet connection...",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color.Black.copy(alpha = 0.8f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        GradientButton(text = "Retry", textColor = Color.White) {

        }
    }
}
