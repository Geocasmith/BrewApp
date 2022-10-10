package com.example.seng440assignment2

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val isDarkMode: Boolean = false,
    val allowNotifications: Boolean = false,
    val notificationDays: PersistentList<Days> = persistentListOf()
)

@Serializable
enum class Days(val shorthand: String) {
    MONDAY("Mon"), TUESDAY("Tue"), WEDNESDAY("Wed"), THURSDAY("Thu"), FRIDAY("Fri"), SATURDAY("Sat"), SUNDAY("Sun")
}


