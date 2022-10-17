package com.example.seng440assignment2.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable

@Composable
fun RatingStars(rating: Int) {
    for (i in 1..5) {
        if (i <= rating) {
            androidx.compose.material3.Icon(
                Icons.Outlined.Star,
                contentDescription = "Filled Star"
            )
        } else {
            androidx.compose.material3.Icon(
                Icons.Outlined.Grade,
                contentDescription = "Unfilled Star"
            )
        }
    }
}