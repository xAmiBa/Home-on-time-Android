package com.example.homeontime.screens

import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    println("BUDDY PHONE: $buddyPhoneNumber")
    println("TIMER TIME FROM STATE: $userJourneyTimeInMinutes")


    Column {
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
        }) {
            Text(text = "Swipe for help")
        }

        // I'm home button
        Button(onClick = {
            println("IM HOME BUTTON CLICK")
            sendText(
                context,
                buddyPhoneNumber,
                "Hi $buddyName!\nI just got home safely!\nThanks for keeping me safe :)\n$userName"
            )
            //TODO: final screen? Or popup?
            notificationHelper.showNotification("JOURNEY_SCREEN", buddyPhoneNumber)
        }) {
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

        Text(text = "Journey time remaining:")
        Text(text = "$minutes : $seconds")

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
                    Text("Time is up! Are you home yet?")
                    Button(onClick = { displayReminder = false }) {
                        Text("Cancel")
                    }
                }
                }
            }
        }
        // Example of a button to stop the timer
        Button(onClick = { isTimerRunning = false }) {
            Text(text = "Stop Timer")
        }
        Button(onClick = { timeRemaining += 15000000}) {
            Text(text = "+ 5 minutes")
        }
    }
}




@Preview
@Composable
fun PreviewTimer() {
    Timer(60)
}
