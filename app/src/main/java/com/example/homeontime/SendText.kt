package com.example.homeontime
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
fun sendText(
    context: Context, //main activity fun
    buddyNumber: String?,
    messageText: String
    ) {

    val smsIntent = Intent(Intent.ACTION_SENDTO)
    smsIntent.data = Uri.parse("sms:$buddyNumber")
    smsIntent.putExtra("sms_body", messageText)
    context.startActivity(smsIntent)

}