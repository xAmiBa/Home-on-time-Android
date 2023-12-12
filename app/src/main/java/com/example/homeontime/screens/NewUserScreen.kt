package com.example.homeontime.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun NewUserScreen(
    userName: String,
    userPhoneNumber: String,
    userJourneyTimeInMinutes: String,
    userNameChange: (String) -> Unit,
    userPhoneNumberChange: (String) -> Unit,
    userJourneyTimeChange: (String) -> Unit,
    addBuddyButton: () -> Unit
) {
    Column {
        UserHeader(userName)
        UserForm(
            userName,
            userPhoneNumber,
            userJourneyTimeInMinutes,
            userNameChange,
            userPhoneNumberChange,
            userJourneyTimeChange,
            addBuddyButton
        )

    }
}

@Composable
fun UserHeader(userName: String) {
    Text(
        text = if (userName == "") "Start your journey!" else "Start your journey $userName"
    )
}

@Composable
fun UserForm(
    userName: String,
    userPhoneNumber: String,
    userJourneyTimeInMinutes: String,
    userNameChange: (String) -> Unit,
    userPhoneNumberChange: (String) -> Unit,
    userJourneyTimeChange: (String) -> Unit,
    addBuddyButton: () -> Unit
){
    TextField(
        value = userName,
        onValueChange = userNameChange,
        placeholder = {(Text(text = "Your name"))}
    )
    TextField(
        value = userPhoneNumber,
        onValueChange = userPhoneNumberChange,
//        TODO: ADD VALIDATION
        placeholder = {(Text(text = "Your phone number - 9 digits starting with 0"))}
    )
    TextField(
        value = userJourneyTimeInMinutes.toString(),
        onValueChange = userJourneyTimeChange,
        placeholder = {(Text(text = "Your journey time in minutes"))},
        // TODO: does this stop user from entering letters?
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
    Button(
        enabled = (
                (userName.length > 2) &&
                (userPhoneNumber.length == 11) &&
                (userPhoneNumber[0] == '0') &&
                (userJourneyTimeInMinutes.length in 1 ..3)
                ),
        onClick = { addBuddyButton() },
    ) {
        // TODO: replace with next image button!
        Text(text = "Add your buddy")
    }
}
