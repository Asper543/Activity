package com.example.activityandfragments.user_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.activityandfragments.NameColor
import com.example.activityandfrugments.R

const val NOTIFICATION = 1
const val CHANNEL_ID = "ChangeColorNotification "


@RequiresApi(Build.VERSION_CODES.O)
val NOTIFICATION_CHANNEL =
    NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT)

class MyNotification(private val context: Context) {

    private val manager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(nameColor: NameColor) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Color")
            .setColor(context.getColor(nameColor.color))
            .setContentText("CHANGED ${nameColor.name}")
            .build()
        manager.createNotificationChannel(NOTIFICATION_CHANNEL)
        manager.notify(NOTIFICATION, notification)
    }
}