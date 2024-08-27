package com.neatroots.bookymyshowadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.neatroots.bookymyshowadmin.presentation.Navigation.App
import com.neatroots.bookymyshowadmin.ui.theme.AdminAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            AdminAppTheme {
                App()

            }
        }
    }
}




