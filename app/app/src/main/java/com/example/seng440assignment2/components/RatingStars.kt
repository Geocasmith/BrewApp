package com.example.seng440assignment2.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun RatingStars(rating: Int) {
    for (i in 1..5) {
        if (i <= rating) {
            androidx.compose.material3.Icon(
                Icons.Outlined.Star,
                contentDescription = null
            )
        } else {
            androidx.compose.material3.Icon(
                Icons.Outlined.Grade,
                contentDescription = null
            )
        }
    }
}

/**
 * Allows for half stars and long numbers
 */
@Composable
fun RatingStarsFloat(rating: Float) {
    var halfStarUsed = false
    for (i in 1..5) {
        if (i <= rating) {
            Icon(
                Icons.Outlined.Star,
                contentDescription = null
            )
            //rating not a full number
        } else if ((rating / 3).toInt().compareTo(rating / 3) == 0 && !halfStarUsed && rating != 0f){
            Icon(
                Icons.Outlined.StarHalf,
                contentDescription = null
            )
            halfStarUsed = true
        }
        else {
            Icon(
                Icons.Outlined.Grade,
                contentDescription = null
            )
        }
    }
}
