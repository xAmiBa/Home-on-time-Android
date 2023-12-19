package com.example.homeontime.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import com.example.homeontime.NotificationHelper
import com.example.homeontime.sendText

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewBuddyScreen (
    context: Context,
    buddyName: String,
    buddyPhoneNumber: String,
    buddyNameChange: (String) -> Unit,
    buddyPhoneNumberChange: (String) -> Unit,
    startJourneyButton: () -> Unit,
    userJourneyTime: Int
) {
    Column {
        Text(text="Set up your\nHomeOnTime\nbuddy!")
        BuddyForm(
            context,
            buddyName,
            buddyPhoneNumber,
            buddyNameChange,
            buddyPhoneNumberChange,
            startJourneyButton,
            userJourneyTime
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BuddyForm(
    context: Context,
    buddyName: String,
    buddyPhoneNumber: String,
    buddyNameChange: (String) -> Unit,
    buddyPhoneNumberChange: (String) -> Unit,
    startJourneyButton: () -> Unit,
    userJourneyTime: Int

) {
    val notificationHelper = NotificationHelper(context)
    var userJourneyTimeString = userJourneyTime.toString()

    Column {
        TextField(
            value = buddyName,
            onValueChange = buddyNameChange,
            placeholder = { Text(text = "Your buddy's name")}
            )

        TextField(
            value = buddyPhoneNumber,
            onValueChange = buddyPhoneNumberChange,
            placeholder = { Text(text = "Your buddy's phone number - 9 digits starting with 0")},
        )

        Button(
            onClick = {
                sendText(
                    context,
                    buddyPhoneNumber,
                    "Hi $buddyName!\nThanks for being my HomeOnTime buddy.\nI just started my journey!\nI will be home within $userJourneyTimeString minutes. If I won't let you know I'm home, please get in touch."
                )
                notificationHelper.showNotification("JOURNEY_SCREEN", buddyPhoneNumber, userJourneyTime.toString())
                println("USER JOURNEY TIME SENT IN NOTIFCATION: $userJourneyTime")
                startJourneyButton()
                      },
            enabled = (
                    (buddyName.length > 2) &&
                    (buddyPhoneNumber.length == 11) &&
                    (buddyPhoneNumber[0] == '0')
                    )
        ) {
            Text(text = "Start your journey!")
        }
    }
}