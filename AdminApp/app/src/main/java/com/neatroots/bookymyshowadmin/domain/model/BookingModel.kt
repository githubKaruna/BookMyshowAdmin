package com.neatroots.bookymyshowadmin.model



data class BookingModel(
    val bookingId: String = "",
    val productId: String = "",
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val message: String = "",
    val price: String = "",
    val date: String = "",
    val timeSlot: String = "",
    val timestamp: String = "",
    val status: String = ""
)
