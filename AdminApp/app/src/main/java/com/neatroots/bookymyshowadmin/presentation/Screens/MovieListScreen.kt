package com.neatroots.bookymyshowadmin.presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.neatroots.bookymyshowadmin.presentation.Navigation.Routes
import com.neatroots.bookymyshowadmin.presentation.ViewModels.BookMyShowAdminViewModel
import com.neatroots.bookymyshowadmin.ui.theme.c10

@Preview(showSystemUi = true)
@Composable
fun MovieListScreen(navController: NavController,viewModel: BookMyShowAdminViewModel = hiltViewModel()) {

    val getAllMoviesState = viewModel.getAllMoviesState.collectAsStateWithLifecycle()
    val movies = getAllMoviesState.value.movies ?: emptyList()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllMovies()

    }
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                CommonHeader(title = "All Movies", navController = navController)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = c10,
                onClick = { navController.navigate(Routes.AddMovie) },
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // RecyclerView replacement using LazyVerticalGrid

                when{
                    getAllMoviesState.value.errorMessage !=null -> {
                        Text(
                            text = "${getAllMoviesState.value.errorMessage}",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,

                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                    getAllMoviesState.value.isLoading -> {
                        Text(
                            text = "Loading..",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,

                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                    getAllMoviesState.value.movies.isNotEmpty() -> {

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                          items(movies){
                            MovieListItem(navController = navController,movie = it)
                        }

                    }

                    }
                }

                // Status text

            }
        }
    )
}



