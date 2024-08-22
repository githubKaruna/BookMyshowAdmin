package com.neatroots.bookymyshowadmin.presentation.navigation

import kotlinx.serialization.Serializable

sealed class SubNavigation {
    @Serializable
    object LoginSingUpScreen : SubNavigation()

    @Serializable
    object MainHomeScreen : SubNavigation()



}
sealed class Routes {
   @Serializable
   object SpalshScreen

    @Serializable
    object Login

    @Serializable
    object Register

    @Serializable
    object Home

    @Serializable
    object AddCategory

    @Serializable
    object ManageNotificationScreen

    @Serializable
    object AddSlider

    @Serializable
    object AddMovie

    @Serializable
    object EditMovie

    @Serializable
    object AllMovies

    @Serializable
    object ViewBookings

    @Serializable
    object ManageTicket

    @Serializable
    object ManageAdminLogin


//2.login screen
//3.register screen
//4.home screen
//5.create category and manage screen
//6.all Movies screen
//7.notification create screen
//8.slider create and manage screen
//9. add movie screen
//10.edit Movie screen
//11.view bookings screen
//12.manage ticket bookings

}