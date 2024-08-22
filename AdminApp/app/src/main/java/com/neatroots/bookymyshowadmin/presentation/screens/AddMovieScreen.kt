package com.neatroots.bookymyshowadmin.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.neatroots.bookymyshowadmin.R

@Preview(showSystemUi = true)
@Composable
fun AddMovieScreen(navController: NavController) {
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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = "Cover Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                GradientButton(text = "Add Movie Image",
                    onClick = { },
                    textColor = Color.White,
                    gradient = Brush.horizontalGradient(
                        0.0f to Color(0xFFC6426E),
                        1.0f to Color(0xFF642B73),
                    ) )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = "", // bind to view model/state
                    onValueChange = { /* Handle title input */ },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = "", // bind to view model/state
                    onValueChange = { /* Handle description input */ },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = "", // bind to view model/state
                        onValueChange = { /* Handle price input */ },
                        label = { Text("Price") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = "", // bind to view model/state
                        onValueChange = { /* Handle offer price input */ },
                        label = { Text("Offer Price") },
                        modifier = Modifier.width(180.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = "", // bind to view model/state
                    onValueChange = { /* Handle rating input */ },
                    label = { Text("Rating") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = "Select Category", // bind to view model/state
                    onValueChange = { /* Handle city selection */ },
                    label = { Text("Select Category") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = "Language", // bind to view model/state
                    onValueChange = { /* Handle language selection */ },
                    label = { Text("Select Language") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = "", // bind to view model/state
                    onValueChange = { /* Handle theater name input */ },
                    label = { Text("Enter Theater Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Handle add movie image */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Movie Image")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // RecyclerView equivalent in Compose is LazyRow, but omitted here for simplicity.
                // Adjust as needed to include your images or any other content.

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Handle create movie */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create Movie")
                }
            }
        }
    }
}
