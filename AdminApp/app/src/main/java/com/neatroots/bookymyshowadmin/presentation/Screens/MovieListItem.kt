package com.neatroots.bookymyshowadmin.presentation.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.neatroots.bookymyshowadmin.R
import com.neatroots.bookymyshowadmin.ui.theme.c10
import com.neatroots.bookymyshowadmin.ui.theme.tvHeading
import com.neatroots.bookymyshowadmin.ui.theme.tvSmall


@Preview(showSystemUi = true)
@Composable
fun MovieListItem(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)

    ) {
        Column(modifier = Modifier.fillMaxWidth().background(color = Color.White)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {

                Image(
                    painter = painterResource(id = R.drawable.placeholder), // Replace with your image resource
                    contentDescription = null,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, top = 8.dp)
                        .align(Alignment.TopEnd)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Gray,RoundedCornerShape(8.dp))
                         // Replace with your drawable resource
                        .padding(horizontal = 8.dp, vertical = 4.dp),


                ) {
                    Text(
                        text = "City", // Placeholder text
                        fontSize = 14.sp // Adjust according to your style
                    )
                }
            }
            Text(
                text = "Movie Title",
                style = tvSmall,// Placeholder text
                fontSize = 14.sp, // Adjust according to your style
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 4.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Movie Price",
                    style = tvHeading,// Placeholder text
                    fontSize = 20.sp,
                    color = c10,// Adjust according to your style
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Full Price",
                    style = tvSmall,// Placeholder text
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.LineThrough,// Adjust according to your style
                    modifier = Modifier.weight(1f)

                )
            }


        }
    }
}
