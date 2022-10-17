package com.example.seng440assignment2.model

enum class BeerType {
    ALE, LAGER, PORTER, STOUT, IPA, PILSNER, SOUR, DRY
}

data class Beer(
    val id: Long,
    val name: String,
    val barcode: Long,
    val type: BeerType,
    val photoURL: String,
    val averageRating: Int
)

data class BeerListItem(
    val title: String,
    val barcode: String,
    val photoURL: String,
    val averageRating: Long
)