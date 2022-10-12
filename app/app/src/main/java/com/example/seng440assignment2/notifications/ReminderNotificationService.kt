package com.example.seng440assignment2.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.seng440assignment2.MainActivity
import com.example.seng440assignment2.R

class ReminderNotificationService(private val context: Context): ContextWrapper(context) {

    companion object {
        const val REMINDER_CHANNEL_ID = "reminder_channel"
        const val REMINDER_CHANNEL_NAME = "reminder"
    }

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
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

    fun setReminder() {
        var intent = Intent(context, )
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

        notificationManager.n
    }




    override fun onHandleIntent(p0: Intent?) {
        TODO("Not yet implemented")
    }

}