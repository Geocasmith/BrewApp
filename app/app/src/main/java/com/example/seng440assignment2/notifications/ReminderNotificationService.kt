package com.example.seng440assignment2.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import com.example.seng440assignment2.MainActivity
import com.example.seng440assignment2.R
import okhttp3.internal.notify
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ReminderNotificationService(private val context: Context): ContextWrapper(context) {

    companion object {
        const val REMINDER_CHANNEL_ID = "reminder_channel"
        const val REMINDER_CHANNEL_NAME = "reminder"
    }

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
    private var pendingReminderIntent: PendingIntent


    init {
        val reminderIntent = Intent(context, NotificationReceiver::class.java)
        pendingReminderIntent = PendingIntent.getBroadcast(context, 0, reminderIntent, PendingIntent.FLAG_IMMUTABLE)

        createNotificationChannel()
    }


    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            REMINDER_CHANNEL_ID,
            REMINDER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Used to remind user to review some craft beers"

        notificationManager.createNotificationChannel(channel)
    }

    fun setReminder(timeStr: String) {
        val calendar = Calendar.getInstance()
        val time = LocalTime.parse(timeStr)
        calendar.add(Calendar.SECOND, 0)
        calendar.add(Calendar.MINUTE, time.minute)
        calendar.add(Calendar.HOUR, time.hour)

        alarmManager.setRepeating(AlarmManager.RTC, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingReminderIntent)
    }

    fun removeReminder() {
        alarmManager.cancel(pendingReminderIntent)
    }

    fun showNotification() {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.beer_icon)
            .setContentTitle(context.getString(R.string.reminder_notification_title))
            .setContentText(context.getString(R.string.reminder_notification_text))
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }
}