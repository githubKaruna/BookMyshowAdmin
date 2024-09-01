package com.neatroots.bookymyshowadmin.presentation.Screens

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.neatroots.bookymyshowadmin.R
import com.neatroots.bookymyshowadmin.model.SliderModel
import com.neatroots.bookymyshowadmin.presentation.ViewModels.BookMyShowAdminViewModel
import com.neatroots.bookymyshowadmin.ui.theme.c10
import com.neatroots.bookymyshowadmin.utils.Constants
import com.neatroots.bookymyshowadmin.utils.Utils
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SliderScreen(navController: NavController=rememberNavController(),viewModel: BookMyShowAdminViewModel = hiltViewModel()) {
    var text by remember { mutableStateOf("") }
    val sliderState = viewModel.addSliderState
    var sliderImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val uploadImageState = viewModel.uploadImageState
    val getSliderState = viewModel.getSliderState.collectAsStateWithLifecycle()
    val sliderList = getSliderState.value.sliders ?: emptyList()

    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllSliderList()

    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                sliderImageUri = it
               // viewModel.uploadCategoryImage(it)
            }
        }
    )

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Camera Launcher using TakePicturePreview contract
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { newImage ->
            bitmap = newImage // Assign the captured Bitmap to the state variable

        }

    )

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
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp), horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            imageVector =Icons.Default.CameraAlt,
                            contentDescription = "camera",
                            tint = Color.Gray,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    cameraLauncher.launch(null)
                                    openDialog.value = false
                                }

                        )


                        Icon(
                            imageVector =Icons.Default.Image,
                            contentDescription = "gallary",
                            tint = Color.Gray,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    galleryLauncher.launch("image/*")
                                    openDialog.value = false
                                })


                    }
                    Text(text = "Cancel", modifier = Modifier
                        .align(Alignment.End)
                        .clickable { openDialog.value = false }, color = Color.Red)

                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()

            .background(Color.White)
    ) {
        CommonHeader("Create Slider", navController)
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()

                    .background(Color.White)
            ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            openDialog.value = true
                        }
                ) {
                    // Image in the background
                    if (sliderImageUri != null && sliderImageUri.toString().isNotEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(model = sliderImageUri),
                            contentDescription = "slider Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else if (bitmap != null) {
                        Image(
                            bitmap = bitmap!!.asImageBitmap(),
                            contentDescription = "slider Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.placeholder),
                            contentDescription = "slider Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Delete button

                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter Notes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (bitmap == null && sliderImageUri == null) {
                        Utils.showMessage(context, "Please Select Image")
                        return@Button
                    }
                    if (text.isEmpty()) {
                        Utils.showMessage(context, "Please Enter Notes")
                        return@Button
                    }
                    if (bitmap != null) {
                        val baos = ByteArrayOutputStream()
                        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 10, baos)
                        val data = baos.toByteArray()
                        viewModel.uploadImage(
                            data = data,
                            type = Constants.SLIDER_DOCUMENT,
                            onCompleted = {
                                viewModel.createslider(text, uploadImageState.value.success)
                            })
                    } else
                        viewModel.uploadImage(
                            imageUri = sliderImageUri,
                            type = Constants.SLIDER_DOCUMENT,
                            onCompleted = {
                                viewModel.createslider(text, uploadImageState.value.success)

                            })


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = c10)

            ) {
                Text(text = "Create slider", fontSize = 16.sp)
            }

            if (sliderList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(sliderList) {
                        SliderCard(sliderModel = it, onDeleteClick = {})
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (getSliderState.value.isLoading) {
                Text(
                    text = "Loading..",
                    color = Color.Gray,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
       if (sliderState.value.isLoading || uploadImageState.value.loading||getSliderState.value.isLoading) {
           Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
               CircularProgressIndicator()
           }
       }

       if(sliderState.value.success!=null){
           Utils.showMessage(context, sliderState.value.success!!)
           text=""
           bitmap=null
           sliderImageUri=null

       }
    }
    }
}



@Composable
fun SliderCard(sliderModel: SliderModel?, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Image in the background
            if (sliderModel != null) {
                if(sliderModel.imageUrl?.isNotEmpty() == true){

                    AsyncImage(
                        model = sliderModel.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp),
                        contentScale = ContentScale.Crop
                    )

                }else
                {
                    Image(
                        painter = painterResource(id = R.drawable.placeholder), // Replace with your image resource
                        contentDescription = null,

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp),
                        contentScale = ContentScale.Crop
                    )

                }

            // Delete button
            IconButton(
                onClick = {  },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .size(40.dp)
                    .clickable { onDeleteClick() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete Slider",
                    tint = Color.Black
                )
            }
        }
    }
}
}



