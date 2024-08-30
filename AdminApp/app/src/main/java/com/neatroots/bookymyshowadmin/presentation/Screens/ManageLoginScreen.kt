package com.neatroots.bookymyshowadmin.presentation.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.neatroots.bookymyshowadmin.R
import com.neatroots.bookymyshowadmin.ui.theme.PurpleGrey40
import com.neatroots.bookymyshowadmin.ui.theme.c10

@Preview(showSystemUi = true)
@Composable
fun ManageAdminLoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    ) {
        // Back button and image row
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        ) {
            IconButton(
                onClick = { /* TODO: Handle back button click */ },
                modifier = Modifier
                    .size(48.dp)
                    .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .background(
                        color = Color.Transparent,
                        shape = CircleShape
                    )
                    .padding(8.dp)
                    .clickable { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }



            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = null,
                modifier = Modifier
                    .height(220.dp)

            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Title text
        Text(
            text = "Manage Admin Login",
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.inter_bold)),
            color = c10, // Replace with the actual color resource
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        // Description text
        Text(
            text = "Please enter Email and Password to \nRegister as Admin Login",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.inter_semibold)), // Replace with the actual font resource
            color =PurpleGrey40,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )

        // Email input field
        OutlinedTextField(
            value = "",
            onValueChange = { /* TODO: Handle email input */ },
            label = { Text(text = "Enter Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )

        // Password input field
        OutlinedTextField(
            value = "",
            onValueChange = { /* TODO: Handle password input */ },
            label = { Text(text = "Enter Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )

        // Confirm Password input field
        OutlinedTextField(
            value = "",
            onValueChange = { /* TODO: Handle confirm password input */ },
            label = { Text(text = "Confirm Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )

        // Save button
        Spacer(modifier = Modifier.height(20.dp))

        GradientButton(text = "Save",
            onClick = { },
            textColor = Color.White,
            gradient = Brush.horizontalGradient(
                0.0f to Color(0xFFC6426E),
                1.0f to Color(0xFF642B73),
            ) )
        // Logout button
        OutlinedButton(
            onClick = { /* TODO: Handle logout button click */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
         border = BorderStroke(2.dp, c10)

        ) {
            Text(text = "Logout", color = c10)
        }
    }
}
