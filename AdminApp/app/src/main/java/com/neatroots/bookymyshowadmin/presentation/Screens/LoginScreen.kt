package com.neatroots.bookymyshowadmin.presentation.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.neatroots.bookymyshowadmin.R
import com.neatroots.bookymyshowadmin.presentation.Navigation.Routes

@Preview(showSystemUi = true, showBackground = true)
@Composable
//fun LoginScreen(navController: NavHostController){
fun LoginScreen(navController: NavHostController) {

    Scaffold() { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
                    .align(Alignment.Center)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = null,
                    modifier = Modifier
                        .height(260.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Admin Login",
                    color = colorResource(id = R.color.c10),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Please Enter Admin email & password to login access to Admin app",
                    color = Color.Gray.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextInputField(
                    label = "Enter Email",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                TextInputField(
                    label = "Enter Password",
                    onValueChange = {},
                    isPasswordField = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                GradientButton(text = "Login",
                    onClick = { navController.navigate(Routes.Home)},
                    textColor = Color.White,
                    gradient = Brush.horizontalGradient(
                        0.0f to Color(0xFFC6426E),
                        1.0f to Color(0xFF642B73),
                    ) )


            }
        }
    }
}

@Composable
fun TextInputField(
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPasswordField: Boolean = false
) {
    var text  = remember { mutableStateOf("") }

    OutlinedTextField(
        value = text.value,
        onValueChange = onValueChange,

        label = { Text(label) },
        singleLine = true,

        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = if (isPasswordField) {
                androidx.compose.ui.text.input.KeyboardType.Password
            } else {
                androidx.compose.ui.text.input.KeyboardType.Text
            }
        ),
        visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush= Brush.horizontalGradient(
        0.0f to Color(0xFFC6426E),
        1.0f to Color(0xFF642B73),
    ),
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            Color.Transparent
        ),
        contentPadding = PaddingValues(),
        onClick = { onClick() })
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(gradient)
               // .padding(horizontal = 16.dp, vertical = 16.dp),
            ,contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = textColor, fontSize = 16.sp)
        }
    }
}