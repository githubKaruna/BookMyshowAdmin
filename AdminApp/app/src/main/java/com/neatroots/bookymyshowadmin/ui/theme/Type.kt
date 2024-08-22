package com.neatroots.bookymyshowadmin.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val tvSmall = TextStyle(
    fontFamily = FontFamily.Default, // Replace with your custom font family
    fontSize = 16.sp
)

val tvHeading = TextStyle(
    fontFamily = FontFamily.Default, // Replace with your custom font family
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold // Assuming this is bold based on the font name
)

val tvHomeButtonText = TextStyle(
    fontFamily = FontFamily.Default, // Replace with your custom font family
    fontSize = 18.sp
)

val tvNumber = TextStyle(
    fontFamily = FontFamily.Default, // Replace with your custom font family
    fontSize = 40.sp,
    fontWeight = FontWeight.Bold // Inherited from tvHeading
)



@Composable
fun SolidButton(text: String) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.background(color = Color.Gray) // Replace with your drawable
    ) {
        Text(
            text = text,
            fontFamily = FontFamily.Default, // Replace with your custom font family
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun OutlineButton(text: String) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.background(color = Color.Transparent) // Replace with your drawable
    ) {
        Text(
            text = text,
            fontFamily = FontFamily.Default, // Replace with your custom font family
            color = Color(0xFFc10), // Replace with your color resource
            fontSize = 16.sp
        )
    }
}

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

