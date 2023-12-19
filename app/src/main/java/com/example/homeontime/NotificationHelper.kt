package com.example.homeontime
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class NotificationHelper(private val context: Context) {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "HomeOnTime_channel"
            val channelName = "HomeOnTime"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(screenToNavigate: String, buddyNumberStore: String = "", timeRemainingStore: String = "0") {
        val channelId = "HomeOnTime_channel"

        println("TIME RECIEVED IN NOTIFICATION: $timeRemainingStore")

        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.putExtra("screenToNavigate", screenToNavigate)
        notificationIntent.putExtra("buddyNumberStore", buddyNumberStore)
        notificationIntent.putExtra("timeRemainingStore", timeRemainingStore)


        notificationIntent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = Notification.Builder(context, channelId)
            .setContentTitle("HomeOnTime")
            .setContentText("Come back to HomeOnTime!")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)

        // Delay before canceling the notification (10 seconds in this example)
//        Handler(Looper.getMainLooper()).postDelayed({
//            notificationManager.cancel(1)
//        }, 5000)

        println("NOTIFICATION GOT EXECUTED")

    }
}