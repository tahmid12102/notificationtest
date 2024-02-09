package com.example.notificationtest

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import com.example.notificationtest.ui.theme.NotificationtestTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationtestTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainContent(this)
                }
            }
        }
    }
}

@Composable
fun MainContent(activity: Activity) {
    val context = LocalContext.current
    val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    createNotificationChannel(notificationManager)
    Column {
        Button(onClick = {
            sendNotification(context, activity)
        }) {
            Text("Push for notification")
        }
    }
}

private fun sendNotification(context: Context, mainActivity: Activity) {
    val channelId = "notification_channel"
    val notificationId = 101
    val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Create the notification channel
    createNotificationChannel(notificationManager)
    createNotificationChannel(notificationManager)
    val intent = Intent(context, MainActivity::class.java)
//    val intent = Intent("android.intent.action.MAIN")
    val pendingIntentFlags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, pendingIntentFlags)
    // Create the notification
    val notification : Notification = Notification.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(context.getString(R.string.notification_title))
        .setContentText(context.getString(R.string.notification_message))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    // Show the notification
//    notificationManager.notify(notificationId, notification)
    notificationManager.notify(notificationId, notification)
}

private fun createNotificationChannel(notificationManager: NotificationManager) {
    Log.i("test", "create channel")
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channelName ="Notification Channel"
    val channel = NotificationChannel("channel_id", channelName, importance)
    notificationManager.createNotificationChannel(channel)

}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    NotificationtestTheme {
//        MainContent()
//    }
//}