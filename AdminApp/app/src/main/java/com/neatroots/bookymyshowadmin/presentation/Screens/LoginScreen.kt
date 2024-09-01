package com.neatroots.bookymyshowadmin.presentation.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.neatroots.bookymyshowadmin.R
import com.neatroots.bookymyshowadmin.model.LoginModel
import com.neatroots.bookymyshowadmin.presentation.Navigation.Routes
import com.neatroots.bookymyshowadmin.presentation.ViewModels.BookMyShowAdminViewModel
import com.neatroots.bookymyshowadmin.utils.Constants
import com.neatroots.bookymyshowadmin.utils.SharedPref
import com.neatroots.bookymyshowadmin.utils.Utils

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreen(navController: NavHostController=rememberNavController() ,viewModel: BookMyShowAdminViewModel) {
    // State variables for email and password input fields
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val btnName = remember { mutableStateOf("Login") }
    val checkLoginstate = viewModel.checkLoginstate
    val adminRegistrationState = viewModel.adminRegistrationState
    val  context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.checkLoginRegistration()

    }

    Scaffold { paddingValues ->

        if(checkLoginstate.value.success!=null) {
            Utils.showMessage(context, "Login Successfully")
            SharedPref.saveUserData(LocalContext.current, checkLoginstate.value.success!!)
        } else{
            btnName.value = "Add New"
            Utils.showMessage(context,"Please register ")

        }

        if(checkLoginstate.value.success!=null){


            }

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

                // Email Input Field
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Enter Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Password Input Field
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Enter Password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().clickable {
                        if(email.value.isBlank()) {
                            Utils.showMessage(context, "Please Enter Email")
                            return@clickable
                        }
                        else{
                                if (password.value.isBlank()) {
                                    Utils.showMessage(context, "Please Enter Password")
                                    return@clickable

                                }

                        }
                        if(btnName.value.equals("Login"))
                            viewModel.checkLoginRegistration()
                        else if(btnName.value.equals("Add New")) {
                            var loginModel = LoginModel(Constants.LOGIN_REF,email = email.value, password = password.value)
                            viewModel.adminRegistration(loginModel)
                        }



                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),

                )

                Spacer(modifier = Modifier.height(16.dp))

                // Gradient Button
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(),
                    onClick = { navController?.navigate(Routes.Home) }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(
                                Brush.horizontalGradient(
                                    0.0f to Color(0xFFC6426E),
                                    1.0f to Color(0xFF642B73),
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${btnName.value}",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
            if(checkLoginstate.value.isLoading || adminRegistrationState.value.isLoading)
            Box(modifier = Modifier.fillMaxSize())
            {
                CircularProgressIndicator()
            }

        }
    }
}
