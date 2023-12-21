package com.example.homeontime
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homeontime.screens.HomeScreen
import com.example.homeontime.screens.JourneyScreen
import com.example.homeontime.screens.NewBuddyScreen
import com.example.homeontime.screens.NewUserScreen

/* Main App function handling routing and state variables of user & buddy. */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App(
    context: Context,
    screenToNavigate: String = "HOME_SCREEN",
    buddyNumberStore: String = "",
    timeRemainingStore: Int = 0
) {
    // state variables: USER
    var userName by remember { mutableStateOf("") }
    var userPhoneNumber by remember { mutableStateOf("") }
    var userJourneyTimeInMinutes by remember { mutableIntStateOf(timeRemainingStore) }

    //state variables: BUDDY
    var buddyName by remember { mutableStateOf("") }
    var buddyPhoneNumber by remember { mutableStateOf(buddyNumberStore) }

    // Routing
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = screenToNavigate, builder = {
            composable("HOME_SCREEN") {
                HomeScreen(
                    letsGoButton = { navController.navigate("NEW_USER_SCREEN") }
                )
            }

            composable("NEW_USER_SCREEN") {
                NewUserScreen(
                    userName,
                    userPhoneNumber,
                    userJourneyTimeInMinutes.toString(), // Convert Int to String when passing to the composable
                    userNameChange = { userName = it },
                    userPhoneNumberChange = { userPhoneNumber = it },
                    userJourneyTimeChange = {
                        // Handle invalid input by replacing it with 0
                        userJourneyTimeInMinutes = try {
                            it.toInt()
                        } catch (e: NumberFormatException) {
                            0
                        }
                    },
                    addBuddyButton = { navController.navigate("NEW_BUDDY_SCREEN") }
                )
            }

            composable("NEW_BUDDY_SCREEN") {
                NewBuddyScreen(
                    context,
                    buddyName,
                    buddyPhoneNumber,
                    buddyNameChange = { buddyName = it },
                    buddyPhoneNumberChange = { buddyPhoneNumber = it },
                    startJourneyButton = { navController.navigate("JOURNEY_SCREEN") },
                    userJourneyTimeInMinutes
                )
            }

            composable("JOURNEY_SCREEN") {
                JourneyScreen(
                    context,
                    userName,
                    timeRemainingStore,
                    buddyName,
                    buddyPhoneNumber
                )
            }
        } )
}