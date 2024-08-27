package com.neatroots.bookymyshowadmin.presentation.Screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.BrowseGallery
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.KeyboardArrowLeft

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.neatroots.bookymyshowadmin.R
import com.neatroots.bookymyshowadmin.model.CategoryModel
import com.neatroots.bookymyshowadmin.presentation.ViewModels.BookMyShowAdminViewModel
import com.neatroots.bookymyshowadmin.presentation.ViewModels.CategoryState
import com.neatroots.bookymyshowadmin.ui.theme.c10
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.text.font.FontWeight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(navController: NavController,viewModel: BookMyShowAdminViewModel = hiltViewModel()) {


    val categoryState = viewModel._categoryState.value
    val categoryName = remember { mutableStateOf("") }
    var categoryImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var categoryImage by remember { mutableStateOf("") }

    var categoryModel=viewModel.categoryModel
    val uploadImageState = viewModel.uploadCategoryImageState.value
    val context = LocalContext.current

    // when i was using separate state for uploading image and adding category then it will giving too much toast
    // now this totaly file
    when {

        categoryState.error.isNotBlank() -> {
            Toast.makeText(LocalContext.current, categoryState.error, Toast.LENGTH_SHORT).show()
        }
        uploadImageState.error.isNotBlank() -> {
            Toast.makeText(LocalContext.current, uploadImageState.error, Toast.LENGTH_SHORT).show()

        }
        categoryState.data.isNotBlank() -> {
            Toast.makeText(LocalContext.current, categoryState.data, Toast.LENGTH_SHORT).show()
            categoryName.value = ""
            categoryImageUri = null
            categoryImage = ""
            viewModel._categoryState.value = CategoryState()


        }
        uploadImageState.success.isNotBlank() -> {
            categoryImage = uploadImageState.success
            uploadImageState.success = ""
            uploadImageState.error=""
            uploadImageState.loading=false

        }
    }
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier.padding(30.dp)) {

                    Text(text = "Choose", modifier =Modifier.align(Alignment.Start ), fontWeight = FontWeight.Bold)
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 30.dp), horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(imageVector =Icons.Default.CameraAlt, contentDescription = "camera", tint = Color.Gray, modifier = Modifier.size(50.dp))
                        Icon(imageVector =Icons.Default.Image, contentDescription = "gallary", tint = Color.Gray, modifier = Modifier.size(50.dp))


                    }
                    Text(text = "Cancel", modifier =Modifier.align(Alignment.End ), color = Color.Red)

                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                val galleryLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = { uri ->
                        uri?.let {
                            categoryImageUri = it
                            viewModel.uploadCategoryImage(it)
                        }
                    }
                )



                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .clickable {
                                galleryLauncher.launch("image/*")

                            }
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        if (categoryImageUri != null && categoryImageUri.toString().isNotEmpty()) {
                            Image(
                                painter = rememberAsyncImagePainter(model = categoryImageUri),
                                contentDescription = "Category Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.placeholder),
                                contentDescription = "Category Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = categoryName.value,
                    onValueChange = { categoryName.value = it
                        categoryModel.value.categoryName = categoryName.value },
                    label = { Text("Category Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (categoryName.value.isNotBlank()) {
                            viewModel.addCategory()
                        } else {
                            Toast.makeText(context, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = c10)
                ) {
                    Text(text = "Create category", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

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

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Loading..",
                    color = Color.Gray,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        // CircularProgressIndicator in the center of the Box
        if (categoryState.isLoading || uploadImageState.loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)

            )

        }
    }


}


@Composable
fun CommonHeader(title: String, navController: NavController) {
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
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(33.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
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

