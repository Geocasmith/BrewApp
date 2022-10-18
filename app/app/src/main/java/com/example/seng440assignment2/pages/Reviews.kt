package com.example.seng440assignment2.pages
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.example.seng440assignment2.components.BeerReviewCard
import kotlinx.coroutines.launch

/**
 * This is the reviews page
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Reviews(
    mainViewModel: MainViewModel,
    onNavigateToBeerPage: (String) -> Unit
) {
    val reviews = remember { mutableStateListOf<ReviewCard>()}
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
                title = {Text(stringResource(id = R.string.review_title))},
                backgroundColor = Color.White)

                 },

        content = {
                  //lazy column for both beer cards
                    LazyColumn {
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
                                BeerReviewCard(beerName = it.beerName, reviewContent = it.title, reviewerName = it.reviewerName, rating = it.rating, imageLink = it.beerPhotoUrl)
                            }
                        }
                  }},
    )
}

