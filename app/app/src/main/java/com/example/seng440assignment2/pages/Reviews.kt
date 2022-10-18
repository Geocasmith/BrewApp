package com.example.seng440assignment2.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seng440assignment2.MainViewModel
import com.example.seng440assignment2.R
import com.example.seng440assignment2.components.BeerReviewCard
import com.example.seng440assignment2.model.ReviewCard

/**
 * This is the reviews page
 */
@Composable
fun Reviews(
    mainViewModel: MainViewModel
) {
    val reviews = remember { mutableStateListOf<ReviewCard>() }
    val scaffoldState = rememberScaffoldState()

    val reviewRequest = mainViewModel.getArrayRequest(LocalContext.current, "review", { response ->
        reviews.clear()
        for (i in 0 until response.length()) {
            val item = response.getJSONObject(i)
            val review = ReviewCard(
                item["id"].toString().toLong(),
                item["name"].toString(),
                item["barcode"].toString().toLong(),
                item["title"].toString(),
                item["description"].toString(),
                item["rating"].toString().toInt(),
                item["reviewerName"].toString(),
                item["photo_path"].toString()
            )
            reviews.add(review)
        }
    })

    mainViewModel.addRequestToQueue(reviewRequest)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.review_title), color = MaterialTheme.colorScheme.primary) },
                backgroundColor = MaterialTheme.colorScheme.background
            )

        },
        backgroundColor = MaterialTheme.colorScheme.background,
        content = {
            //lazy column for both beer cards
            LazyColumn (modifier = Modifier.padding(it)) {
                if (reviews.isEmpty()) {
                    item {
                        Box(modifier = Modifier.padding(10.dp))
                        {
                            androidx.compose.material.Text(
                                text = LocalContext.current.getString(R.string.no_review_text),
                                fontSize = 20.sp,
                                color = Color.LightGray
                            )
                        }
                    }
                } else {
                    items(reviews, key = { it.id }) {
                        BeerReviewCard(
                            beerName = it.beerName,
                            reviewContent = it.title,
                            reviewerName = it.reviewerName,
                            rating = it.rating,
                            imageLink = it.beerPhotoUrl
                        )
                    }
                }
            }
        },
    )
}

