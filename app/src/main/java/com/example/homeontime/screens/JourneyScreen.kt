package com.example.homeontime.screens
import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.homeontime.NotificationHelper
import com.example.homeontime.sendText

/* JOURNEY SCREEN displaying count down timer
* user can add more time, notify contact that they arrived home or
* send SOS message in case of danger */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JourneyScreen (
    context: Context,
    userName: String,
    userJourneyTimeInMinutes: Int,
    buddyName: String,
    buddyPhoneNumber: String
) {
    val notificationHelper = NotificationHelper(context)

    Column (
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Timer(userJourneyTimeInMinutes)

         Column (
             horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
             verticalArrangement = Arrangement.Center
         ) {
             var sosClickCounter = 0
             val buttonModifier = Modifier
                 .height(150.dp)
                 .padding(10.dp)
                 .width(1000.dp)

             Button(onClick = {
                 // User must triple click to send SOS message
                 sosClickCounter += 1
                 if (sosClickCounter >= 3) {
                     sendText(
                         context,
                         buddyPhoneNumber,
                         //TODO: location
                         "$buddyName I need help!\nTry to contact me or call 999 now."
                     )
                     notificationHelper.showNotification("JOURNEY_SCREEN", buddyPhoneNumber)
                 } },
                 modifier = buttonModifier.alpha(0.9f).background(MaterialTheme.colorScheme.tertiary),
                 shape = RoundedCornerShape(10.dp)
             ) {
                 Column (
                     horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                 ) {
                     Text(text = "SOS", fontSize = 30.sp, color = Color.Red)
                     Text(text = "click 3 times", fontSize = 20.sp, color = Color.Red)
                 }
             }

             Button(onClick = {
                 sendText(
                     context,
                     buddyPhoneNumber,
                     "Hi $buddyName!\nI just got home safely!\nThanks for keeping me safe :)\n$userName",
                 )
                 notificationHelper.showNotification("JOURNEY_SCREEN", buddyPhoneNumber)
             },
                 modifier = buttonModifier,
                 shape = RoundedCornerShape(10.dp)
             ) { Text(text = "I'm home!", fontSize = 30.sp) }
         }
     }
 }


@Composable
// Count Down Timer triggering popup
// reminding user about clicking I'm home or SOS button
fun Timer(remainingTimeInMinutes: Int) {

    val remainingTimeInMilliseconds = remainingTimeInMinutes * 60 * 1000 * 60
    var timeRemaining by remember { mutableIntStateOf(remainingTimeInMilliseconds) }
    var isTimerRunning by remember { mutableStateOf(true) }
    var displayReminder by remember { mutableStateOf(false) }

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

    Column (
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally

    ) {
        val minutes = timeRemaining / 60000 / 60
        val seconds = (timeRemaining / 100000) % 60

        Text(
            text = "Journey time\nremaining:",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 40.sp,
            modifier = Modifier.padding(0.dp, 90.dp, 0.dp, 0.dp)
        )

        // Button adding 5 minutes to remaining time
        Button(
            onClick = { timeRemaining += 15000000},
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .offset(130.dp, 130.dp)
                .alpha(0.6f),
            shape = CircleShape
        ) { Text(text = "+ 5 \'", fontSize = 25.sp) }

        Text(
            text = "$minutes : $seconds",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 130.sp,
            modifier = Modifier.width(400.dp).fillMaxWidth().offset(50.dp, (-120).dp)
            )

        // end of journey popup
        if (displayReminder) {
            Dialog(
                onDismissRequest = { displayReminder = false },
            ) {
                // Wrap the content in a Box to create a frame
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(16.dp)
                        .size(300.dp) // Adjust the size as needed
                ) {Column{
                    Text(text = "Your journey is coming to an end!", fontSize = 30.sp)
                    Button(onClick = { displayReminder = false }) {
                        Text("X")
                    }
                }
                }
            }
        }
    }
}