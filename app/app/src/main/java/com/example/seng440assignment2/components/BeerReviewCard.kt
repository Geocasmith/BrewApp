package com.example.seng440assignment2.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.seng440assignment2.R

/**
 * A card that displays a beer review. Inputs are the beer name, description, reviewer name, rating, and image link.
 */
@Composable
fun BeerReviewCard(beerName:String, reviewContent:String, reviewerName:String, rating:Int, imageLink:String, onClick: () -> Unit = {}, onNavigateToBeerPage: (String) -> Unit = {}) {
    val padding=16.dp
    Card(
        backgroundColor = MaterialTheme.colorScheme.background,
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable(onClick = { onNavigateToBeerPage(beerName) })
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            //box with image aligned center horizontally and height of 90 and width of 50 dp
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .height(90.dp)
                    .width(90.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                //image if its a show image
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(imageLink).error(R.drawable.beer_icon).build(),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }


            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(padding)
            ) {
                Text(
                    text = beerName,
                    color = MaterialTheme.colorScheme.primary,
                    lineHeight = 16.sp,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.5.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = reviewContent,
                    color = MaterialTheme.colorScheme.primary,
                    lineHeight = 24.sp,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
//                    TODO:Make the name clickable to go to the user's profile
                    Text(
                        text = reviewerName,
                        color = MaterialTheme.colorScheme.secondary,
                        lineHeight = 16.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row {
                        //filled star for rating and unfilled for remaining out of 5
                        RatingStars(rating)
                    }
                }


            }
        }

    }
}

@Composable
fun BeerCard(beerName:String, imageLink:String, rating:Float, onNavigateToBeerPage: () -> Unit) {
    val padding=4.dp
    Card(
        backgroundColor = MaterialTheme.colorScheme.background,
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onNavigateToBeerPage)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            //box with image aligned center horizontally and height of 90 and width of 50 dp
            Box(modifier = Modifier
                    .padding(5.dp)
                    .height(90.dp)
                    .width(90.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                //image if its a show image
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(imageLink).error(R.drawable.beer_icon).build(),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(padding)
            ) {
                Text(
                    text = beerName,
                    color = MaterialTheme.colorScheme.primary,
                    lineHeight = 16.sp,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.5.sp
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "",
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 16.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row {
                        //filled star for rating and unfilled for remaining out of 5
                        RatingStarsFloat(rating)
                    }
                }


            }
        }

    }
}

@Composable
fun BeerPageReviewCard(reviewContent:String, reviewerName:String, rating:Int) {
    val padding = 16.dp
    Card(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(padding)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = reviewContent,
                color = MaterialTheme.colorScheme.primary,
                lineHeight = 24.sp,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
//                    TODO:Make the name clickable to go to the user's profile
                Text(
                    text = reviewerName,
                    color = MaterialTheme.colorScheme.secondary,
                    lineHeight = 16.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row {
                    //filled star for rating and unfilled for remaining out of 5
                    RatingStars(rating)
                }
            }


        }
    }
}