package com.neatroots.bookymyshowadmin.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Preview(showSystemUi = true)
@Composable
fun BookingScreen(navController: NavController= rememberNavController()) {
    Scaffold(
        topBar = {
            CommonHeader(title = "All Bookings", navController = navController)
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Bookings Loading..",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )

                    // Booking List
                    BookingList(modifier = Modifier.fillMaxSize())
                }
            }
        }
    )
}

@Composable
fun BookingList(modifier: Modifier = Modifier) {
    // Sample data to show how RecyclerView equivalent works in Jetpack Compose
    val bookings = listOf("Booking 1", "Booking 2", "Booking 3")

    if (bookings.isEmpty()) {
        Text(
            text = "No bookings available",
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    } else {
        LazyColumn(modifier = modifier) {
            items(10) {
                BookingListItem()
            }
        }
    }
}

