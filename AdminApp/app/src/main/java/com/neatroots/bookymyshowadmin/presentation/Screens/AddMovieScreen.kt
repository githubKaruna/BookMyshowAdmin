package com.neatroots.bookymyshowadmin.presentation.Screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.storage.internal.Util
import com.neatroots.bookymyshowadmin.R
import com.neatroots.bookymyshowadmin.model.CategoryModel
import com.neatroots.bookymyshowadmin.model.MovieImageUriModel
import com.neatroots.bookymyshowadmin.model.MovieImageUrlModel
import com.neatroots.bookymyshowadmin.presentation.ViewModels.BookMyShowAdminViewModel
import com.neatroots.bookymyshowadmin.utils.Constants
import com.neatroots.bookymyshowadmin.utils.Utils
import java.io.ByteArrayOutputStream
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun AddMovieScreen(navController: NavController,viewModel: BookMyShowAdminViewModel= hiltViewModel()) {
    var title =  remember { mutableStateOf("") }
    var desc =  remember { mutableStateOf("") }
    var price =  remember { mutableIntStateOf(0) }
    var offerPrice =  remember { mutableIntStateOf(0) }
    var rating =  remember { mutableIntStateOf(0) }
    var priceString =  remember { mutableStateOf("") }
    var offerPriceString =  remember { mutableStateOf("") }
    var ratingString =  remember { mutableStateOf("") }
    var category =  remember { mutableStateOf("") }
    var language =  remember { mutableStateOf("") }
    var theaterName =  remember { mutableStateOf("") }

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllCategories()

    }


    val moviesState = viewModel.moviesState
    val movieName = remember { mutableStateOf("") }
    var movieImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var movieImage by remember { mutableStateOf("") }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val uploadImageState = viewModel.uploadMovieImages
    val uploadMovieCoverState = viewModel.uploadMovieCover
    val imageUriList = remember { mutableStateOf<List<MovieImageUriModel>> (emptyList())}
    val imageUrlList = remember { mutableStateOf<List<MovieImageUrlModel>> (emptyList())}

    val getAllCategoryState = viewModel.getAllCategoryState.collectAsStateWithLifecycle()
    var categoryModel=viewModel.categoryModel
    val categories = getAllCategoryState.value.categories ?: emptyList()



    var movieModel=viewModel.movieModel
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }
    val coverOpenDialog = remember { mutableStateOf(false) }
    val imagesOpenDialog = remember { mutableStateOf(false) }


    var selectedItem by remember { mutableStateOf(null as CategoryModel?) } // Default selected item
    var expanded by remember { mutableStateOf(false) } // State for menu visibility


    val galleryLauncherCoverImg = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                movieImageUri = it
                bitmap = null
               // viewModel.uploadCategoryImage(it)
            }
        }
    )


    // Camera Launcher using TakePicturePreview contract
    val cameraLauncherCoverImg = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { newImage ->
            bitmap = newImage // Assign the captured Bitmap to the state variable
            movieImageUri=null
           // viewModel.uploadCategoryImage(bitmap=bitmap)
        }

    )


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUriList.value += MovieImageUriModel(
                    imageUri = it,
                    imageId = UUID.randomUUID().toString()
                )
                // viewModel.uploadCategoryImage(it)
            }
        }
    )


    // Camera Launcher using TakePicturePreview contract
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { newImage ->
            val baos = ByteArrayOutputStream()
            newImage!!.compress(Bitmap.CompressFormat.JPEG, 50, baos)
            val data = baos.toByteArray()
            imageUriList.value += MovieImageUriModel(
                imageId = UUID.randomUUID().toString(),
                imageByteArray = data

            )
        }

    )

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

            if(getAllCategoryState.value.categories.isNotEmpty())
                selectedItem = getAllCategoryState.value.categories[0]
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
                                            if (coverOpenDialog.value)
                                                cameraLauncherCoverImg.launch(null)
                                            if (imagesOpenDialog.value)
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

                                            if (coverOpenDialog.value)
                                                galleryLauncherCoverImg.launch("image/*")
                                            if (imagesOpenDialog.value)
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
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable {
                            openDialog.value = true
                            coverOpenDialog.value = true
                            imagesOpenDialog.value = false

                        },
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)

                ) {
                    if (movieImageUri != null && movieImageUri.toString().isNotEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(model = movieImageUri),
                            contentDescription = "Category Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else if(bitmap!=null){

                        Image(
                            bitmap = bitmap!!.asImageBitmap(),
                            contentDescription = "Category Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }else{
                        Image(
                            painter = painterResource(id = R.drawable.placeholder),
                            contentDescription = "Category Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                GradientButton(
                    text = "Add Movie Image",
                    onClick = {
                        openDialog.value = true
                        imagesOpenDialog.value=true
                        coverOpenDialog.value=false
                    },
                    textColor = Color.White,
                    gradient = Brush.horizontalGradient(
                        0.0f to Color(0xFFC6426E),
                        1.0f to Color(0xFF642B73),
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                if(imageUriList.value.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(206.dp)
                    ) {
                        items(imageUriList.value) { imageRes ->
                            MoviesliderImages(
                                imageUriModel = imageRes,
                                onDeleteClick = {
                                    // imageUriList.value -= it

                                }
                            )
                        }
                        /* items(imageUriList) { imageRes ->
                        MovieItem(
                            imageRes = imageRes,
                            onDeleteClick = { onDeleteClick(imageRes) }
                        )
                    }*/
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = movieName.value, // bind to view model/state
                    onValueChange = {
                        movieName.value = it/* Handle title input */ },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = desc.value, // bind to view model/state
                    onValueChange = { desc.value=it },
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
                        value = priceString.value, // bind to view model/state
                        onValueChange = { priceString.value = it
                            if(!it.isBlank()){price.intValue=it.toInt() }else price.intValue = 0},
                        label = { Text("Price") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)


                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = offerPriceString.value, // bind to view model/state
                        onValueChange = { offerPriceString.value = it
                            if(!it.isBlank()){offerPrice.intValue = it.toInt() }else offerPrice.intValue = 0},
                        label = { Text("Offer Price") },
                        modifier = Modifier.width(180.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)

                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = ratingString.value.toString(), // bind to view model/state
                    onValueChange = { ratingString.value = it
                        if(!it.isBlank()){rating.intValue = it.toInt()}else rating.intValue = 0 },
                    label = { Text("Rating") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
                )

                Spacer(modifier = Modifier.height(8.dp))
                if (getAllCategoryState.value.categories.isNotEmpty()) {

                    // Container for the dropdown menu
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded } // Toggle menu visibility
                    ) {
                        // OutlinedTextField to show the selected item
                        selectedItem?.let {
                            OutlinedTextField(
                                value = it.categoryName,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Select a Category") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier
                                    .menuAnchor() // Anchor for dropdown
                                    .fillMaxWidth()
                                    .clickable { expanded = !expanded } // Make the field clickable
                            )
                        }

                        // Dropdown menu
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categories.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(text = category!!.categoryName) },
                                    onClick = {
                                        selectedItem = category
                                        expanded = false // Close menu on selection
                                    }
                                )
                            }
                        }
                    }
                }


                // List of items for the spinner
                //  var selectedItem by remember { mutableStateOf(categories[0]) } // Default selected item


                Spacer(modifier = Modifier.height(8.dp))

                var selectedLang by remember { mutableStateOf(Constants.LANGUAGE_LIST[0]) } // Default selected item
                var expandedLang by remember { mutableStateOf(false) } // State for menu visibility

                // Container for the dropdown menu
                ExposedDropdownMenuBox(
                    expanded = expandedLang,
                    onExpandedChange = { expandedLang = !expandedLang } // Toggle menu visibility
                ) {
                    // OutlinedTextField to show the selected item
                    selectedLang?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Select a Language") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLang)
                            },
                            modifier = Modifier
                                .menuAnchor() // Anchor for dropdown
                                .fillMaxWidth()
                                .clickable {
                                    expandedLang = !expandedLang
                                } // Make the field clickable
                        )
                    }

                    // Dropdown menu
                    ExposedDropdownMenu(
                        expanded = expandedLang,
                        onDismissRequest = { expandedLang = false }
                    ) {
                        Constants.LANGUAGE_LIST.forEach { language ->
                            DropdownMenuItem(
                                text = { Text(text = language) },
                                onClick = {
                                    selectedLang = language
                                    expandedLang = false // Close menu on selection
                                }
                            )
                        }
                    }


                       

                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = theaterName.value, // bind to view model/state
                    onValueChange = { theaterName.value=it },
                    label = { Text("Enter Theater Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                GradientButton(text = "Create a Movie", textColor = Color.White, onClick = {


                  // Function to validate fields
                    var errorMessage = ""

                    fun validate(): Boolean {
                      return when {
                          movieName.value.isEmpty() -> {
                              errorMessage = "Movie name cannot be empty"
                              Utils.showMessage(context,errorMessage)

                              false
                          }
                          rating.intValue == 0 -> {
                              errorMessage = "Rating must be a valid number"
                              Utils.showMessage(context,errorMessage)

                              false
                          }
                          desc.value.isEmpty() -> {
                              errorMessage = "Description cannot be empty"
                              Utils.showMessage(context,errorMessage)

                              false
                          }
                          price.intValue == 0 -> {
                              errorMessage = "Price must be a valid number"
                              Utils.showMessage(context,errorMessage)

                              false
                          }
                          offerPrice.intValue == 0 -> {
                              errorMessage = "Full price must be a valid number"
                              Utils.showMessage(context,errorMessage)

                              false
                          }
                           selectedItem ==null -> {
                               errorMessage = "Category must be selected"
                               Utils.showMessage(context,errorMessage)

                               false
                           }/*
                           language.text.isEmpty() -> {
                               errorMessage = "Language cannot be empty"
                               false
                           }*/
                          theaterName.value.isEmpty() -> {
                              errorMessage = "Theater name cannot be empty"
                              Utils.showMessage(context,errorMessage)

                              false
                          }
                          bitmap==null && movieImageUri.toString().isEmpty() -> {
                              errorMessage = "Please select cover image"
                              Utils.showMessage(context,errorMessage)

                              false
                          }
                          imageUriList.value.isEmpty() -> {
                              errorMessage = "Please select at least one service image"
                              Utils.showMessage(context,errorMessage)

                              false
                          }
                          else -> {
                              errorMessage = ""
                             // Utils.showMessage(context,errorMessage)

                              true

                          }

                      }
                  }
                    if(validate()) {
                        movieModel.value.title = movieName.value
                        movieModel.value.rating = rating.intValue.toString()
                        movieModel.value.description = desc.value
                        movieModel.value.price = price.intValue.toString()
                        movieModel.value.fullPrice = offerPrice.intValue.toString()
                        movieModel.value.selectCategory = selectedItem!!.categoryName
                        movieModel.value.language = selectedLang
                        movieModel.value.theater = theaterName.value
                        var data:ByteArray? = null
                        if(bitmap!=null) {
                            val baos = ByteArrayOutputStream()
                            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, baos)
                            data = baos.toByteArray()
                        }
                            viewModel.uploadMovieCover(data = data, imageUri = movieImageUri, onCompleted = {
                                if (uploadMovieCoverState.value.success != null) {
                                    var index = 1
                                  //  movieModel.value.coverImg = uploadMovieCoverState.value.success

                                    fun uploadImages(): Unit {
                                        if (imageUriList.value != null) {
                                            var i = imageUriList.value[index]
                                            viewModel.uploadMovieImages(
                                                imageUri = i.imageUri,
                                                data = i.imageByteArray,
                                                onCompleted = { url, isError ->
                                                    if (!isError!!) {
                                                        imageUrlList.value += MovieImageUrlModel(
                                                            imageUrl = url,
                                                            imageId = i.imageId
                                                        )
                                                    }
                                                    index++
                                                    if (index < imageUriList.value.size)
                                                        uploadImages()
                                                    else {
                                                        val arrayList = imageUrlList.value.toCollection(ArrayList())

                                                        movieModel.value.images = arrayList
                                                        viewModel.addMovie()
                                                    }



                                                })
                                        }

                                    }

                                    uploadImages()



                                }
                            }
                            )



                    }
                })
            }


            when{
                getAllCategoryState.value.isLoading || uploadMovieCoverState.value.isLoading || uploadImageState.value.isLoading || moviesState.value.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }



        }
    }

}

@Composable
fun MoviesliderImages(
    imageUriModel: MovieImageUriModel,
    onDeleteClick: (imageUriModel: MovieImageUriModel) -> Unit,
    cornerRadius: Dp = 8.dp
) {
    Card(
        modifier = Modifier
            .size(width = 300.dp, height = 190.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(cornerRadius),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Main Image
            if (imageUriModel.imageUri != null && imageUriModel.imageUri.toString().isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUriModel.imageUri),
                    contentDescription = "Category Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else if(imageUriModel.imageByteArray!=null){
                val bitmap = BitmapFactory.decodeByteArray(imageUriModel.imageByteArray, 0, imageUriModel.imageByteArray.size)

                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = "Category Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Category Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Delete Button
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .background(Color.Blue)
                    .clip(RoundedCornerShape(50))
                    .padding(10.dp)
                    .clickable { onDeleteClick(imageUriModel) },
                tint = Color.LightGray
            )

        }
    }
}


