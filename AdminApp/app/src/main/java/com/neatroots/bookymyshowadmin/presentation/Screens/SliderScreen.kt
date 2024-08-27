package com.neatroots.bookymyshowadmin.presentation.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.neatroots.bookymyshowadmin.R
import com.neatroots.bookymyshowadmin.ui.theme.c10

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SliderScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        CommonHeader("Create Category",navController)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SliderImage()
            Spacer(modifier = Modifier.height(16.dp))
            SliderTextField()
            Spacer(modifier = Modifier.height(8.dp))
            CreateSliderButton()
            Spacer(modifier = Modifier.height(16.dp))
            SliderList()
            Spacer(modifier = Modifier.height(16.dp))
            SliderStatusText()
        }
    }
}



@Composable
fun SliderImage() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)

                .background(Color.Gray),

            contentAlignment = Alignment.Center,


            ) {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "Category Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun SliderTextField() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Enter Notes") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CreateSliderButton() {
    Button(
        onClick = { /* Handle Create Category */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(top = 10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = c10)

    ) {
        Text(text = "Create slider", fontSize = 16.sp)
    }
}

@Composable
fun SliderList() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(10) { index -> // Dummy list size
            SliderCard()
        }
    }
}

@Composable
fun SliderStatusText() {
    Text(
        text = "Loading..",
        color = Color.Gray,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun SliderItem(
    categoryName: String,
    imageResId: Int,
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        Column {
            Card(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),

                ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Category Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(
                text = categoryName,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
