package com.example.homeontime.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeScreen (letsGoButton: () -> Unit) {

    Column {

        Text(
            text = "Welcome to",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 80.dp, 0.dp, 0.dp),
            fontSize = 20.sp

        )

//        Image(
//            painter = painterResource(id = R.drawable.logo),
//            contentDescription ="Home on time logo",
//            modifier = Modifier
//                .width(200.dp)
//                .align(Alignment.CenterHorizontally)
//        )

        Text(
            text= "Home",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset((-30).dp),
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFC2185B)
        )
        Text(
            text= "on",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset((-100).dp, (-50).dp),
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFC2185B),
            fontSize = 60.sp

        )
        Text(
            text= "Time",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(65.dp, (-170).dp),
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFC2185B)
        )

        Text(
            text= "Safe\nand\nalways on time!",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { letsGoButton() },
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)
                .align(Alignment.CenterHorizontally)
            ) {
            Text(text = "Let's go home!")
        }
    }
}