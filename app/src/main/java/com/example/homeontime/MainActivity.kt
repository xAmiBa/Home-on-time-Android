package com.example.homeontime
import android.content.pm.PackageManager
import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.core.app.ActivityCompat
import com.example.homeontime.ui.theme.HomeOnTimeTheme
import com.example.homeontime.ui.theme.Background

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Passing data from app to notification and then from notification to the app
        // Due to when user exits to send a text, state variables are erased
        val screenToNavigate = intent.getStringExtra("screenToNavigate")
        val buddyNumberStore = intent.getStringExtra("buddyNumberStore") ?: ""
        val timeRemainingStore = intent.getStringExtra("timeRemainingStore")?.toIntOrNull() ?: 0

        // Check if screenToNavigate is passed so user comes back to the same screen
        // Navigate to the JourneyScreen using the retrieved data
        if (screenToNavigate == "JOURNEY_SCREEN") {
            setContent {
                HomeOnTimeTheme {
                    Surface{
                        checkPermissions()
                        Background()
                        App(this, screenToNavigate, buddyNumberStore, timeRemainingStore)
                    }
                }
            }
        } else {
            setContent {
                HomeOnTimeTheme {
                    Surface {
                        checkPermissions()
                        Background()
                        App(this, buddyNumberStore = buddyNumberStore)
                    }
                }
            }
        }
    }

    // User permissions handling: send sms, post notification, vibrate
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
