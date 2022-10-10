package com.example.seng440assignment2.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

/**
 * A card that displays a beer review. Inputs are the beer name, description, reviewer name, rating, and image link.
 */
@Composable
fun BeerCard(title:String, description:String, name:String,rating:Int, imageLink:String,onClick: () -> Unit = {}) {
    val padding=16.dp
    Card(
        elevation = 4.dp, modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            //box with image aligned center horizontally and height of 90 and width of 50 dp
            Box(
                modifier = androidx.compose.ui.Modifier
                    .height(90.dp)
                    .width(90.dp),
                contentAlignment = Alignment.Center
            ) {
                //image
                AsyncImage(
                    model = imageLink,
                    contentDescription = "Beer!",
                    modifier = androidx.compose.ui.Modifier.fillMaxSize()
                )
            }


            Column(
                horizontalAlignment = Alignment.Start,
                modifier = androidx.compose.ui.Modifier.padding(padding)
            ) {
                Text(
                    text = title,
                    color = Color.Black.copy(alpha = 0.87f),
                    lineHeight = 16.sp,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.5.sp
                    )
                )
                Spacer(modifier = androidx.compose.ui.Modifier.height(4.dp))
                Text(
                    text = description,
                    color = Color.Black.copy(alpha = 0.87f),
                    lineHeight = 24.sp,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = androidx.compose.ui.Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
//                    TODO:Make the name clickable to go to the user's profile
                    Text(
                        text = name,
                        color = Color.Black.copy(alpha = 0.6f),
                        lineHeight = 16.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row {
                        //filled star for rating and unfilled for remaining out of 5
                        for (i in 1..5) {
                            if (i <= rating) {
                                Icon(Icons.Outlined.Star, contentDescription = "Filled Star")
                            } else {
                                Icon(Icons.Outlined.Grade, contentDescription = "Unfilled Star")
                            }
                        }
                    }
                }


            }
        }

    }
}