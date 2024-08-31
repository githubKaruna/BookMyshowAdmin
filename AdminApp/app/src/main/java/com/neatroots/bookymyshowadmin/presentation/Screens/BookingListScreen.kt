package com.neatroots.bookymyshowadmin.presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.neatroots.bookymyshowadmin.presentation.ViewModels.BookMyShowAdminViewModel


@Preview(showSystemUi = true)
@Composable
fun BookingScreen(navController: NavController,viewModel: BookMyShowAdminViewModel = hiltViewModel()) {

    val getAllbookingState = viewModel.getAllBookingsState.collectAsStateWithLifecycle()
    val bookings = getAllbookingState.value.bookings ?: emptyList()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllBookings()

    }
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                CommonHeader(title = "Add Movie", navController = navController)
            }     }
    ) { padding ->
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
                    when{
                        getAllbookingState.value.errorMessage !=null -> {
                            Text(
                                text = "No bookings found",
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp
                            )
                        }
                        getAllbookingState.value.isLoading -> {
                            Text(
                                text = "Bookings Loading..",
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp
                            )
                        }
                        getAllbookingState.value.bookings.isNotEmpty() -> {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                               items(bookings){
                                   BookingListItem(booking = it)
                               }
                        }
                        }
                    }



                }
            }
        }

}

