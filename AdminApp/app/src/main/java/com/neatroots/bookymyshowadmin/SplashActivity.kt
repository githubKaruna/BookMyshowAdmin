package com.neatroots.bookymyshowadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.neatroots.bookymyshowadmin.presentation.Screens.SplashScreen
import com.neatroots.bookymyshowadmin.ui.theme.AdminAppTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         enableEdgeToEdge()
        setContent {
            AdminAppTheme {
                val navController = rememberNavController()

                SplashScreen(navController = navController)
            }

        }
    }
}