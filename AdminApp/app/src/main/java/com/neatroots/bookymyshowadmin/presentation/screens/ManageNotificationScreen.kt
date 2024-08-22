package com.neatroots.bookymyshowadmin.presentation.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.neatroots.bookymyshowadmin.R

@Preview(showBackground = true)
@Composable
fun ManageNotificationScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CommonHeader(title = "Manage Notifications")
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
            onValueChange = { /* Handle title change */ },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = "", // State variable for Description
            onValueChange = { /* Handle description change */ },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = "", // State variable for URL
            onValueChange = { /* Handle URL change */ },
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
