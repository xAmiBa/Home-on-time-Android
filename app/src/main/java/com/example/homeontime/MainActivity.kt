package com.example.homeontime

import android.content.pm.PackageManager
import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.ActivityNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homeontime.ui.theme.HomeOnTimeTheme
import com.example.homeontime.screens.HomeScreen
import com.example.homeontime.screens.NewUserScreen
import com.example.homeontime.screens.NewBuddyScreen
import com.example.homeontime.screens.JourneyScreen

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenToNavigate = intent.getStringExtra("screenToNavigate")
        val buddyNumberStore = intent.getStringExtra("buddyNumberStore") ?: ""

        println("PUT EXTRA PRINTS: $buddyNumberStore")
        // check if screenToNavigate is passed so user comes back to the same screen
        if (screenToNavigate == "JOURNEY_SCREEN") {
            setContent {
                HomeOnTimeTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        checkPermissions()
                        App(this, screenToNavigate, buddyNumberStore)

                    }
                }
            }
            // Navigate to the JourneyScreen using the retrieved data
        } else {
            setContent {
                HomeOnTimeTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        checkPermissions()
                        App(this, buddyNumberStore = buddyNumberStore)

                    }
                }
            }

        }
    }
    // ask for permissions
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermissions() {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.SEND_SMS), 101)
        }
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
        }
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.VIBRATE), 101)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
//@Preview(showBackground = true)
fun App(
    context: Context,
    screenToNavigate: String = "HOME_SCREEN",
    buddyNumberStore: String = ""
) {
    // state variables: USER
    var userName by remember {
        mutableStateOf("")
    }
    var userPhoneNumber by remember {
        mutableStateOf("")
    }
    var userJourneyTimeInMinutes by remember {
        mutableStateOf(0)
    }
    //state variables: BUDDY
    var buddyName by remember {
        mutableStateOf("")
    }
    var buddyPhoneNumber by remember {
        mutableStateOf(buddyNumberStore)
    }

    // routing names
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = screenToNavigate, builder = {
        composable("HOME_SCREEN") {
            HomeScreen(letsGoButton = {
                navController.navigate("NEW_USER_SCREEN")
            })
        }

        composable("NEW_USER_SCREEN") {

            NewUserScreen(
                userName,
                userPhoneNumber,
                userJourneyTimeInMinutes.toString(), // Convert Int to String when passing to the composable
                userNameChange = { userName = it },
                userPhoneNumberChange = { userPhoneNumber = it },
                userJourneyTimeChange = {
                    // Use try-catch to handle invalid input (e.g., non-numeric)
                    try {
                        userJourneyTimeInMinutes = it.toInt()
                    } catch (e: NumberFormatException) {
                        // Handle the exception (e.g., show an error message)
                        // You can also set a default value or take other actions
                        userJourneyTimeInMinutes = 0
                    }
                },
                addBuddyButton = {
                    navController.navigate("NEW_BUDDY_SCREEN")
                }
            )
        }

        composable("NEW_BUDDY_SCREEN") {
            NewBuddyScreen(
                context,
                buddyName,
                buddyPhoneNumber,
                buddyNameChange = { buddyName = it },
                buddyPhoneNumberChange = { buddyPhoneNumber = it },
                startJourneyButton = {
                    navController.navigate("JOURNEY_SCREEN")
                },
                userJourneyTimeInMinutes
            )
        }

        composable("JOURNEY_SCREEN") {
            JourneyScreen(
                context,
                userName,
                userPhoneNumber,
                userJourneyTimeInMinutes,
                buddyName,
                buddyPhoneNumber
            )
        }
    } )
}