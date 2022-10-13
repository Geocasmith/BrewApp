package com.example.seng440assignment2.model

enum class BeerType {
    ALE, LARGER, PORTER, STOUT, IPA, PILSNER, SOUR
}

data class Beer(
    val id: Long,
    val name: String,
    val barcode: Long,
    val type: BeerType,
    val photoURL: String,
    val averageRating: Int
)
