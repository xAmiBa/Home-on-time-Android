package com.example.homeontime.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    Column (
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text="Set up your\nHomeOnTime\nbuddy!",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 40.sp,
            modifier = Modifier.padding(0.dp, 90.dp, 0.dp, 0.dp))
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

        val textFieldModifier = Modifier
            .width(390.dp)
            .height(110.dp)
            .fillMaxWidth()
            .padding(20.dp)

        TextField(
            value = buddyName,
            onValueChange = buddyNameChange,
            placeholder = { Text(text = "Veronica")},
            modifier = textFieldModifier,
            label ={ Text(text = "Your buddy's name") }

        )

        TextField(
            value = buddyPhoneNumber,
            onValueChange = buddyPhoneNumberChange,
            placeholder = { Text(text = "0123456789")},
            modifier = textFieldModifier,
            label ={ Text(text = "Your buddy's phone number") }

        )

        Button(
            onClick = {
                sendText(
                    context,
                    buddyPhoneNumber,
                    "Hi $buddyName!\nThanks for being my HomeOnTime buddy.\nI just started my journey!\nI will be home within $userJourneyTimeString minutes. If I won't let you know I'm home, please get in touch."
                )
                notificationHelper.showNotification("JOURNEY_SCREEN", buddyPhoneNumber, userJourneyTime.toString())
                startJourneyButton()
                      },
            enabled = (
                    (buddyName.length > 2) &&
                    (buddyPhoneNumber.length == 11) &&
                    (buddyPhoneNumber[0] == '0')
                    ),
            modifier = Modifier
                .width(300.dp)
                .height(70.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Start your journey!",
                fontSize = 30.sp)
        }
    }
}