package com.example.homeontime.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homeontime.R

@Composable
fun HomeScreen (letsGoButton: () -> Unit) {

    Column {

        Text(
            text = "Welcome to",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription ="Home on time logo",
            modifier = Modifier
                .width(200.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text= "HomeOnTime",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
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