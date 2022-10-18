package com.example.seng440assignment2.datastore
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val isDarkMode: Boolean = false,
    val allowNotifications: Boolean = false,
    val notificationTime: String = "12:00",
    val allowShaking: Boolean = true
)


