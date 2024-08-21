package com.neatroots.bookymyshowadmin.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.navigation.compose.rememberNavController
import com.neatroots.bookymyshowadmin.R
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CategoryScreen(navController: NavController= rememberNavController()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        CommonHeader("Create Category")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            CategoryImage()
            Spacer(modifier = Modifier.height(16.dp))
            CategoryTextField()
            Spacer(modifier = Modifier.height(8.dp))
            CreateCategoryButton()
            Spacer(modifier = Modifier.height(16.dp))
            CategoryList()
            Spacer(modifier = Modifier.height(16.dp))
            StatusText()
        }
    }
}

@Composable
fun CommonHeader(title:String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color(0xFFC6426E)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = null, tint = Color.White, modifier = Modifier.size(33.dp))

            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun CategoryImage() {
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
fun CategoryTextField() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Category Name") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CreateCategoryButton() {
    Button(
        onClick = { /* Handle Create Category */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(text = "Create category", fontSize = 16.sp)
    }
}

@Composable
fun CategoryList() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(10) { index -> // Dummy list size
            CategoryItem(
                categoryName = "Category $index",
                imageResId = R.drawable.placeholder,
                onEditClick = { /* Handle Edit Click */ }
            )
        }
    }
}

@Composable
fun StatusText() {
    Text(
        text = "Loading..",
        color = Color.Gray,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun CategoryItem(
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
