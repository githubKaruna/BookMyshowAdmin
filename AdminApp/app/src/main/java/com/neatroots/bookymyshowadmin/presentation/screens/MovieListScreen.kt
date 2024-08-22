package com.neatroots.bookymyshowadmin.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.neatroots.bookymyshowadmin.R
import com.neatroots.bookymyshowadmin.presentation.navigation.Routes
import com.neatroots.bookymyshowadmin.ui.theme.c10

@Preview(showSystemUi = true)
@Composable
fun MovieListScreen(navController: NavController= rememberNavController()) {
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(10) { // Replace 10 with your actual data source
                  MovieListItem(navController = navController)
                    }
                }

                // Status text
                Text(
                    text = "Loading..",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray,

                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        }
    )
}

@Composable
fun CategoryItem(navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { navController.navigate(Routes.EditMovie) },
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation()
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                modifier = Modifier
                    .height(190.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Movie Name",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
