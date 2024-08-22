package com.neatroots.bookymyshowadmin.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.neatroots.bookymyshowadmin.presentation.screens.AddMovieScreen
import com.neatroots.bookymyshowadmin.presentation.screens.CategoryScreen
import com.neatroots.bookymyshowadmin.presentation.screens.EditMovieScreen
import com.neatroots.bookymyshowadmin.presentation.screens.HomeScreen
import com.neatroots.bookymyshowadmin.presentation.screens.ManageAdminLoginScreen
import com.neatroots.bookymyshowadmin.presentation.screens.ManageNotificationScreen
import com.neatroots.bookymyshowadmin.presentation.screens.MovieListScreen
import com.neatroots.bookymyshowadmin.presentation.screens.SliderList
import com.neatroots.bookymyshowadmin.presentation.screens.SliderScreen
import com.neatroots.bookymyshowadmin.presentation.screens.SplashScreen

@Composable
fun App() {

    val navController = rememberNavController()
    var startScreen = Routes.SpalshScreen

    NavHost(navController = navController, startDestination = startScreen) {
        navigation<SubNavigation.LoginSingUpScreen>(startDestination = startScreen) {
         
            composable<Routes.Login> { 
                LoginSingUpScreen(navController = navController)
            }
            composable<Routes.Register> {
                
            }
            composable<Routes.SpalshScreen> {

                SplashScreen(navController)
            }
        }
        composable<Routes.SpalshScreen> {

            SplashScreen(navController)
        }
        navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.Home)
        {
            composable<Routes.Home> { 
                HomeScreen(navController = navController)
            }
            composable<Routes.AddCategory> {
                CategoryScreen(navController = navController)
            }
            composable<Routes.AddMovie> {
                AddMovieScreen(navController = navController)
            }
            composable<Routes.EditMovie> {
                EditMovieScreen(navController = navController)
            }
            composable<Routes.ManageAdminLogin> {
                ManageAdminLoginScreen(navController = navController)
            }
            composable<Routes.ManageNotificationScreen> {
                ManageNotificationScreen(navController = navController)
            }
            composable<Routes.AllMovies> {
                MovieListScreen(navController = navController)
            }
            composable<Routes.AddSlider> {
                SliderScreen(navController = navController)
            }
        }
    

    }

}

@Composable
fun LoginSingUpScreen(navController: NavController) {

}
