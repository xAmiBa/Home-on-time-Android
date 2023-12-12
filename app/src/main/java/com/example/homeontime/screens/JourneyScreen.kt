package com.example.homeontime.screens

import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.homeontime.NotificationHelper
import com.example.homeontime.sendText
import kotlin.math.ceil


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
fun Timer(remainingTimeInSeconds: Int) {
//    var timeRemaining by remember { mutableIntStateOf(remainingTimeInSeconds) }
//    var isTimerRunning by remember { mutableStateOf(true) }
//
//    DisposableEffect(timeRemaining) {
//        val countDownTimer = object : CountDownTimer(timeRemaining * 1000L, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                timeRemaining = (millisUntilFinished / 1000).toInt()
//            }
//
//            override fun onFinish() {
//                isTimerRunning = false
//            }
//        }
//
//        if (isTimerRunning) {
//            countDownTimer.start()
//        }
//
//        onDispose {
//            countDownTimer.cancel()
//        }
//    }
//
//    Column {
//        Text(text = "Time remaining: $timeRemaining seconds")
//
//        // Example of a button to stop the timer
//        Button(onClick = { isTimerRunning = false }) {
//            Text(text = "Stop Timer")
//        }
//    }
}
