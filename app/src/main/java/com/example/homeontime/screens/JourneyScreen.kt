package com.example.homeontime.screens

import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.homeontime.NotificationHelper
import com.example.homeontime.sendText


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JourneyScreen (
    context: Context,
    userName: String,
    userPhoneNumber: String,
    userJourneyTimeInMinutes: Int,
    buddyName: String,
    buddyPhoneNumber: String
) {

    val notificationHelper = NotificationHelper(context)

    Column (
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val buttonModifier = Modifier
            .width(300.dp)
            .height(70.dp)
//        .align(Alignment.CenterHorizontally)

        // countdown
        Timer(userJourneyTimeInMinutes)

        //TODO: SOS button ON SWIPE or triple click?
        Button(onClick = {
            println("SOS BUTTON CLICK")
            sendText(
                context,
                buddyPhoneNumber,
                //TODO: location?
                "$buddyName I need help!\nTry to contact me or call 999 now."
            )
            //TODO: final screen? Or popup?
            notificationHelper.showNotification("JOURNEY_SCREEN", buddyPhoneNumber)
        },
            modifier = buttonModifier,
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Swipe for help")
        }

        // I'm home button
        Button(onClick = {
            sendText(
                context,
                buddyPhoneNumber,
                "Hi $buddyName!\nI just got home safely!\nThanks for keeping me safe :)\n$userName",
            )
            //TODO: final screen? Or popup?
            notificationHelper.showNotification("JOURNEY_SCREEN", buddyPhoneNumber)
        },
            modifier = buttonModifier,
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "I'm home!")
        }
    }
}

@Composable
fun Timer(remainingTimeInMinutes: Int) {

    var remainingTimeInMilliseconds = remainingTimeInMinutes * 60 * 1000 * 60
    var timeRemaining by remember { mutableIntStateOf(remainingTimeInMilliseconds) }
    var isTimerRunning by remember { mutableStateOf(true) }
    var displayReminder by remember { mutableStateOf(false) }


    // BUG: minutes does not decrease

    DisposableEffect(timeRemaining) {
        val countDownTimer = object : CountDownTimer(timeRemaining * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining -= 1000
          }
            override fun onFinish() {
                isTimerRunning = false
                displayReminder = true
            }
        }

        if (isTimerRunning) {
            countDownTimer.start()
        }

        onDispose {
            countDownTimer.cancel()
        }
    }

    Column {
        val minutes = timeRemaining / 60000 / 60
        val seconds = (timeRemaining / 100000) % 60

        Text(
            text = "Journey time\nremaining:",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 40.sp,
            modifier = Modifier.padding(0.dp, 90.dp, 0.dp, 0.dp)
        )
        Text(
            text = "$minutes : $seconds",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 150.sp,
            modifier = Modifier.padding(0.dp, 90.dp, 0.dp, 0.dp)
            )

        Button(
            onClick = { timeRemaining += 15000000},
            modifier = Modifier.width(100.dp).height(100.dp),
            shape = RoundedCornerShape(10.dp)

        ) {
            Text(
                text = "+ 5 \'",
                fontSize = 25.sp)
        }

        if (displayReminder) {
            Dialog(
                onDismissRequest = { displayReminder = false }
            ) {
                // Wrap the content in a Box to create a frame
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(16.dp)
                        .size(300.dp) // Adjust the size as needed
                ) {Column{
                    Text("Your journey is coming to an end!")
                    Button(onClick = { displayReminder = false }) {
                        Text("X")
                    }
                }
                }
            }
        }

    }
}
