package com.example.seng440assignment2.model

data class User (
    val id: Long,
    val name: String,
    val photoURL: String,
    val favouriteBeerTypes: List<BeerType>,
    val bio: String
)
