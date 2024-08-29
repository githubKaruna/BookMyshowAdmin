package com.neatroots.bookymyshowadmin.presentation.Screens

              import android.content.Context
              import android.graphics.Bitmap
              import android.net.Uri
              import android.util.Log
              import android.widget.Toast
              import androidx.activity.compose.rememberLauncherForActivityResult
              import androidx.activity.result.PickVisualMediaRequest
              import androidx.activity.result.contract.ActivityResultContracts
              import androidx.compose.foundation.Image
              import androidx.compose.foundation.background
              import androidx.compose.foundation.border
              import androidx.compose.foundation.clickable
              import androidx.compose.foundation.layout.*
              import androidx.compose.foundation.lazy.grid.GridCells
              import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
              import androidx.compose.foundation.lazy.grid.items
              import androidx.compose.foundation.shape.CircleShape
              import androidx.compose.foundation.shape.RoundedCornerShape
              import androidx.compose.material.icons.Icons
              import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
              import androidx.compose.material.icons.filled.BrowseGallery
              import androidx.compose.material.icons.filled.CameraAlt
              import androidx.compose.material.icons.filled.Edit
              import androidx.compose.material.icons.filled.Image
              import androidx.compose.material.icons.filled.KeyboardArrowLeft
              import androidx.compose.material.icons.filled.Search

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
              import androidx.compose.ui.graphics.asImageBitmap
              import androidx.compose.ui.text.font.FontWeight
              import androidx.lifecycle.compose.collectAsStateWithLifecycle
              import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
              @Composable
              fun CategoryScreen(navController: NavController,viewModel: BookMyShowAdminViewModel = hiltViewModel()) {


                  val categoryState = viewModel._categoryState.value
                  val categoryName = remember { mutableStateOf("") }
                  var categoryImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
                  var categoryImage by remember { mutableStateOf("") }

                  var categoryModel=viewModel.categoryModel
                  val uploadImageState = viewModel.uploadCategoryImageState.value
                  val getAllCategoryState = viewModel.getAllCategoryState.collectAsStateWithLifecycle()
                  val categories = getAllCategoryState.value.categories ?: emptyList()

                  val context = LocalContext.current
                  val openDialog = remember { mutableStateOf(false) }

                    LaunchedEffect(key1 = Unit) {
                        viewModel.getAllCategories()

                    }

                  val galleryLauncher = rememberLauncherForActivityResult(
                      contract = ActivityResultContracts.GetContent(),
                      onResult = { uri ->
                          uri?.let {
                              categoryImageUri = it
                              viewModel.uploadCategoryImage(it)
                          }
                      }
                  )

                  var bitmap by remember { mutableStateOf<Bitmap?>(null) }

                  // Camera Launcher using TakePicturePreview contract
                  val cameraLauncher = rememberLauncherForActivityResult(
                      contract = ActivityResultContracts.TakePicturePreview(),
                      onResult = { newImage ->
                          bitmap = newImage // Assign the captured Bitmap to the state variable
                          viewModel.uploadCategoryImage(bitmap=bitmap)
                      }

                  )
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



                              Column(
                                  modifier = Modifier.fillMaxWidth(),
                                  horizontalAlignment = Alignment.CenterHorizontally
                              ) {
                                  Box(
                                      modifier = Modifier
                                          .size(120.dp)
                                          .clip(CircleShape)
                                          .clickable {
                                              openDialog.value = true
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

                              Column(
                                  modifier = Modifier
                                      .fillMaxSize()
                              ) {

                                  when {
                                      getAllCategoryState.value.isLoading -> {
                                          Box(
                                              modifier = Modifier.fillMaxSize(),
                                              contentAlignment = Alignment.Center
                                          ) {
                                              CircularProgressIndicator()
                                          }
                                      }
                                      getAllCategoryState.value.errorMessage != null -> {
                                          Box(
                                              modifier = Modifier.fillMaxSize(),
                                              contentAlignment = Alignment.Center
                                          ) {
                                              Text("Sorry, Unable to Get Information")
                                          }
                                      }
                                      categories.isEmpty() -> {
                                          Box(
                                              modifier = Modifier.fillMaxSize(),
                                              contentAlignment = Alignment.Center
                                          ) {
                                              Text("No Categories Available")
                                          }
                                      }
                                      else -> {
                                          LazyVerticalGrid(
                                              columns = GridCells.Fixed(2),
                                              horizontalArrangement = Arrangement.spacedBy(10.dp),
                                              verticalArrangement = Arrangement.spacedBy(10.dp)
                                          ) {
                                            items(categories){
                                                category->
                                                CategoryItem(
                                                    category = category!!,
                                                   // onEditClick = { /* Handle edit click */ }
                                                )
                                            }

                                          }
                                      }
                                  }
                              }

                              Spacer(modifier = Modifier.height(16.dp))


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
              @Preview(showSystemUi = true)
              @Composable
              fun CategoryItem(
                  category:CategoryModel=CategoryModel(),
              ) {
                  Box(
                      modifier = Modifier
                          .fillMaxWidth()
                          .clip(RoundedCornerShape(8.dp))
                          .background(Color.White)
                          .border(0.75.dp, Color.LightGray, RoundedCornerShape(8.dp))
                          .padding(8.dp)
                  ) {

                      Column {
                          Card(
                              modifier = Modifier
                                  .height(160.dp)
                                  .fillMaxWidth(),
                              shape = RoundedCornerShape(8.dp),

                              ) {
                              if(category.imgUrl.isNotBlank()) {
                                  AsyncImage(
                                      model = category.imgUrl,
                                      contentDescription = null,
                                      modifier = Modifier
                                          .fillMaxWidth()
                                          .background(color = Color.White)
                                          .fillMaxHeight()
                                          .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                                      contentScale = ContentScale.Crop,
                                      placeholder = painterResource(id = R.drawable.placeholder)
                                  )
                              }else
                              {
                                  Image(
                                      painter = painterResource(id = R.drawable.placeholder),
                                      contentDescription = "Category Image",
                                      contentScale = ContentScale.Crop,
                                      modifier = Modifier.fillMaxSize()
                                  )
                              }
                          }

                          Text(
                              text = category.categoryName,
                              style = MaterialTheme.typography.bodySmall,
                              fontSize = 14.sp,
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .padding(top = 4.dp),
                              textAlign = TextAlign.Center
                          )
                      }

                      Box(
                          modifier = Modifier
                              .padding(end = 8.dp, top = 8.dp)
                              .align(Alignment.TopEnd)
                              .size(40.dp)
                              .background(Color.White, shape = RoundedCornerShape(8.dp))
                              .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                              // Replace with your drawable resource
                              .padding(horizontal = 8.dp, vertical = 8.dp),


                          ) {
                          Icon(imageVector = Icons.Default.Edit, contentDescription = "", tint = Color.Gray )
                      }
                  }
              }

