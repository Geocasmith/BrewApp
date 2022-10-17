package com.example.seng440assignment2.model

data class Review(
    val id: Long,
    val title: String,
    val description: String,
    val rating: Int,
    val beer: Beer,
    val reviewer: User,
)

data class ReviewCard(
    val id: Long,
    val beerName:String,
    val barcode: Long,
    val title: String,
    val description: String,
    val rating: Int,
    val reviewerName: String,
    val beerPhotoUrl: String
)
