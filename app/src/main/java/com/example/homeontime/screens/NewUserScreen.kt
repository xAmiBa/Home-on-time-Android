package com.example.homeontime.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/* NEW USER SCREEN allowing user to add their name, phone number and journey time */
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

    Column (
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
// Heading customized depending on user name input.
fun UserHeader(userName: String) {
    Text(
        text = if (userName == "") "Start your journey!" else "Start your journey \n$userName",
        style = MaterialTheme.typography.titleLarge,
        fontSize = 40.sp,
        modifier = Modifier.padding(0.dp, 90.dp, 0.dp, 0.dp)
        )
}

@Composable
// User name, phone and journey time input handling
fun UserForm(
    userName: String,
    userPhoneNumber: String,
    userJourneyTimeInMinutes: String,
    userNameChange: (String) -> Unit,
    userPhoneNumberChange: (String) -> Unit,
    userJourneyTimeChange: (String) -> Unit,
    addBuddyButton: () -> Unit
){
    val textFieldModifier = Modifier
        .width(390.dp)
        .height(110.dp)
        .fillMaxWidth()
        .padding(20.dp)

    Column {

        TextField(
            value = userName,
            onValueChange = userNameChange,
            placeholder = {(Text(text = "Anna"))},
            modifier = textFieldModifier,
            label ={ Text(text = "Your name") }
        )

        TextField(
            value = userPhoneNumber,
            onValueChange = userPhoneNumberChange,
            placeholder = {(Text(text = "0123456789"))},
            modifier = textFieldModifier,
            label ={ Text(text = "Your phone number") }
        )

        TextField(
            value = userJourneyTimeInMinutes,
            onValueChange = userJourneyTimeChange,
            // Keyboard setup accepting only numeric values
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = textFieldModifier,
            label ={ Text(text = "Your journey time in minutes") }
        )
        Button(
            // Button is enabled only after positive validation of input
            enabled = (
                    (userName.length > 2) &&
                    (userPhoneNumber.length == 11) &&
                    (userPhoneNumber[0] == '0') &&
                    (userJourneyTimeInMinutes.length in 1 ..3)
                    ),
            onClick = { addBuddyButton() },
            modifier = Modifier
                .width(300.dp)
                .height(70.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp)
        ) {Text(text = ">", fontSize = 40.sp) }
        }
}
