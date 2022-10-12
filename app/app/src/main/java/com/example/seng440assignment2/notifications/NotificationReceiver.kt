package com.example.seng440assignment2.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val reminderNotificationService = ReminderNotificationService(context)
        val notificationBuilder = reminderNotificationService.setReminder()
    }

}