package com.neatroots.bookymyshowadmin.model

data class NotificationModel(
    var notificationId: String = "",
    var notificationImg: String = "",
    val notificationTitle: String = "",
    val notificationDescription: String = "",
    val notificationUrl: String = ""
)
