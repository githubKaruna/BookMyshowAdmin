package com.neatroots.bookymyshowadmin.presentation.Screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neatroots.bookymyshowadmin.R
import com.neatroots.bookymyshowadmin.ui.theme.c10
import com.neatroots.bookymyshowadmin.ui.theme.tvHeading
import com.neatroots.bookymyshowadmin.ui.theme.tvSmall

@Preview(showSystemUi = true)
@Composable
fun BookingListItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()

            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp)
        ) {


            Text(
                text = "Booking Details",

                style = TextStyle(
                    fontSize = 28.sp,
                    color = Color(0xFFC6426E),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.calistoga))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                BookingDetailRow(label = "Movie:", value = "Movie Name")
                BookingDetailRow(label = "Mobile:", value = "********90")
                BookingDetailRow(label = "Email:", value = "dummy@gmail.com")
                BookingDetailRow(label = "Slot:", value = "09:10 AM IMAX")
                BookingDetailRow(label = "Date:", value = "This Event Booked on")

                Text(
                    text = "This slot is booked on 5:40 PM on 4/11/2023",
                    style = tvSmall
                    ,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding( vertical = 4.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$ 00.0",
                        style = tvHeading,
                        color = c10,
                        fontFamily = FontFamily(Font(R.font.autour_one)),
                        modifier = Modifier
                    )
                    Text(
                        text = "11.0",
                        style = tvSmall,
                        fontFamily = FontFamily(Font(R.font.calistoga)),
                        color = c10,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                GradientButton(text = "Cancel", textColor = Color.White) {
                    
                }

            }
        }
    }
}

@Composable
fun BookingDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = tvSmall
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            style = tvSmall
        )
    }
}
