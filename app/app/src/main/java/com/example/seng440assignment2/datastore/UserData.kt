package com.example.seng440assignment2.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val id: Long = -1,
    val authToken: String = ""
)
