
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.neatroots.bookymyshowadmin.R
import com.neatroots.bookymyshowadmin.model.NotificationModel
import com.neatroots.bookymyshowadmin.presentation.ViewModels.BookMyShowAdminViewModel
import com.neatroots.bookymyshowadmin.presentation.ViewModels.GetNotificationListState
import com.neatroots.bookymyshowadmin.utils.Constants
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageNotificationsScreen(navController: NavController,viewModel: BookMyShowAdminViewModel = hiltViewModel()) {

    var text by remember { mutableStateOf("") }
    val notificationAddState = viewModel.addNotificationstate
    var notificationImgUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val uploadImageState = viewModel.uploadImageState
    val getNotificationListState = viewModel.getNotificationListState.collectAsStateWithLifecycle()
    val notificationList = getNotificationListState.value.notification ?: emptyList()

    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }

    // State variables for text fields
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var notificationUrl by remember { mutableStateOf("") }

    // State to control the loading state
    var isLoading by remember { mutableStateOf(false) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getNotificationList()

    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                notificationImgUri = it
                bitmap = null
                // viewModel.uploadCategoryImage(it)
            }
        }
    )


    // Camera Launcher using TakePicturePreview contract
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { newImage ->
            bitmap = newImage // Assign the captured Bitmap to the state variable
            notificationImgUri=null
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
    Scaffold(
        topBar = {
            CommonHeader(title = "Manage Notifications", navController = navController)
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            openDialog.value = true
                        }
                ){
                    if (notificationImgUri != null && notificationImgUri.toString().isNotEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(model = notificationImgUri),
                            contentDescription = "Notification Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else if (bitmap != null) {
                        Image(
                            bitmap = bitmap!!.asImageBitmap(),
                            contentDescription = "Notification Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.placeholder),
                            contentDescription = "Notification Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = notificationUrl,
                    onValueChange = { notificationUrl = it },
                    label = { Text("Notification Url") },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                GradientButton(
                    text = "Create Notification",
                    onClick = {
                        // Handle create notification logic
                        // Simulate a network call or processing
                        // Once done, set isLoading = false
                        var notificationModel = NotificationModel(notificationTitle =title, notificationDescription = description, notificationUrl = notificationUrl, notificationImg = "")

                        if(bitmap==null && notificationImgUri==null){
                            viewModel.createNotification(notificationModel = notificationModel)
                        }
                        else
                        {
                            if (bitmap != null) {
                                val baos = ByteArrayOutputStream()
                                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 10, baos)
                                val data = baos.toByteArray()
                                viewModel.uploadImage(
                                    data = data,
                                    type = Constants.NOTIFICATION_IMAGE_REF,
                                    onCompleted = {
                                        notificationModel.notificationImg=uploadImageState.value.success
                                        viewModel.createNotification(notificationModel = notificationModel)
                                    })
                            } else
                                viewModel.uploadImage(
                                    imageUri = notificationImgUri,
                                    type = Constants.NOTIFICATION_IMAGE_REF,
                                    onCompleted = {
                                        notificationModel.notificationImg=uploadImageState.value.success

                                        viewModel.createNotification(notificationModel = notificationModel)

                                    })

                        }
                    },
                    textColor = Color.White,
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
            if(getNotificationListState.value.notification.isNotEmpty()) {
                items(notificationList) { notification ->
                    NotificationListItem(notificationItem = notification!!)
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))

                if (getNotificationListState.value.isLoading) {
                    Text(
                        text = "Loading..",
                        color = colorResource(id = R.color.gray),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,

                        )
                }
            }
        }
    }
}

@Composable
fun NotificationListItem(notificationItem: NotificationModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
        ) {


            if(notificationItem.notificationImg.isNotEmpty()){

                AsyncImage(
                    model = notificationItem.notificationImg,
                    contentDescription = "Notification Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )

            }
            Text(
                text = notificationItem.notificationTitle,
                fontSize = 18.sp,
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp)
            )
            Text(
                text = notificationItem.notificationDescription,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 16.dp, top = 4.dp)
            )
        }
    }
}

// Sample data class to represent a notification
data class NotificationItem(
    val imageRes: Int,
    val title: String,
    val description: String
)

// Sample list of notifications
val notificationList = listOf(
    NotificationItem(R.drawable.placeholder, "Notification 1", "This is the description for notification 1."),
    NotificationItem(R.drawable.placeholder, "Notification 2", "This is the description for notification 2."),

    NotificationItem(R.drawable.placeholder, "Notification 1", "This is the description for notification 1."),
    NotificationItem(R.drawable.placeholder, "Notification 2", "This is the description for notification 2."),

    NotificationItem(R.drawable.placeholder, "Notification 1", "This is the description for notification 1."),
    NotificationItem(R.drawable.placeholder, "Notification 2", "This is the description for notification 2."),
    // Add more items as needed
)

/*
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ManageNotificationScreen(navController: NavHostController,viewModel: BookMyShowAdminViewModel= hiltViewModel()) {

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
                            imageVector = Icons.Default.CameraAlt,
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
                            imageVector = Icons.Default.Image,
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
    Scaffold(
        topBar = {
            CommonHeader(title = "Manage Notifications",navController)
        }
    ){innerPadding->
        NotificationContent(innerPadding)
    }
}

@Composable
fun NotificationContent(innerrPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerrPadding)
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = "Notification Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "", // State variable for Title
            onValueChange = { */
/* Handle title change *//*
 },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = "", // State variable for Description
            onValueChange = { */
/* Handle description change *//*
 },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = "", // State variable for URL
            onValueChange = { */
/* Handle URL change *//*
 },
            label = { Text("Notification Url") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
       Column(modifier = Modifier
           .padding(horizontal = 16.dp)
           .background(Color.White)) {
           GradientButton(text = "Create Notification",
               onClick = { },
               textColor = Color.White,
           )
       }


        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            items(notificationList) { notification ->
                NotificationListItem(notificationItem =notification)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Loading..",
            color = colorResource(id = R.color.gray),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun NotificationListItem(notificationItem: NotificationItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
        ) {
            Image(
                painter = painterResource(id = notificationItem.imageRes),
                contentDescription = "Notification Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = notificationItem.title,
                fontSize = 18.sp,
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp)
            )
            Text(
                text = notificationItem.description,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 16.dp, top = 4.dp)
            )
        }
    }
}
// Sample data class to represent a notification
data class NotificationItem(
    val imageRes: Int,
    val title: String,
    val description: String
)

// Sample list of notifications
val notificationList = listOf(
    NotificationItem(R.drawable.placeholder, "Notification 1", "This is the description for notification 1."),
    NotificationItem(R.drawable.placeholder, "Notification 2", "This is the description for notification 2."),
    // Add more items as needed
)
*/*/
