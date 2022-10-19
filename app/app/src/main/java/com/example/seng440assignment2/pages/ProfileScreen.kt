package com.example.seng440assignment2

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.seng440assignment2.components.BeerReviewCard
import com.example.seng440assignment2.model.ReviewCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(mainViewModel: MainViewModel, onNavigateToEdit: () -> Unit, onNavigateToPref: () -> Unit, onLogout: () -> Unit)
{
    val context = LocalContext.current
    val reviews = remember { mutableStateListOf<ReviewCard>()}
    var options by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf(context.resources.getText(R.string.loading))}
    var bio by remember { mutableStateOf(context.resources.getText(R.string.loading))}

    val userRequest = mainViewModel.getObjectRequest(LocalContext.current, "users/" + mainViewModel.getUserId(), { response ->
        name = response["name"].toString()
        bio = response["bio"].toString()
    })

    val reviewRequest = mainViewModel.getArrayRequest(LocalContext.current, "review/mine", { response ->
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
    mainViewModel.addRequestToQueue(userRequest)
    mainViewModel.addRequestToQueue(reviewRequest)




    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            stickyHeader {
                TopAppBar(backgroundColor = MaterialTheme.colorScheme.background, elevation = 0.dp ) {
                    Spacer(Modifier.weight(1f))
                    // Options
                    Box(
                        Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.TopEnd)
                    ) {
                        IconButton(onClick = { options = true }) {
                            Icon(
                                Icons.Outlined.MoreVert,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        DropdownMenu(
                            expanded = options,
                            onDismissRequest = { options = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.background)
                        ) {
                            DropdownMenuItem(onClick = {
                                options = false
                                onNavigateToEdit()
                            }) {
                                Text(
                                    text = stringResource(id = R.string.profile_edit),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            DropdownMenuItem(onClick = {
                                options = false
                                onNavigateToPref()
                            }) {
                                Text(
                                    text = stringResource(id = R.string.profile_pref),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            DropdownMenuItem(onClick = {
                                options = false
                                scope.launch {
                                    mainViewModel.clearUserData()
                                    onLogout()
                                }
                            }) {
                                Text(
                                    text = stringResource(id = R.string.profile_logout),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
            item {
                Column(Modifier.fillMaxWidth()) {
                    ProfileImage(modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp))

                    Box(modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 10.dp))
                    {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = name.toString().trim(), fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)

                            if (!(bio.isBlank() || bio.toString() == "null")) {
                                Text(text = bio.toString().trim(), color = MaterialTheme.colorScheme.secondary)
                            }
                        }
                    }
                }
            }
            if (reviews.isEmpty()) {
                item {
                    Box(modifier = Modifier.padding(10.dp))
                    {
                        Text(
                            text = stringResource(R.string.no_review_text),
                            fontSize = 20.sp,
                            color = Color.LightGray
                        )
                    }
                }
            } else {
                items (reviews, key = { it.id }) {
                    BeerReviewCard(beerName = it.beerName, reviewContent = it.title, reviewerName = it.reviewerName, rating = it.rating, imageLink = it.beerPhotoUrl)
                }
            }
            // Add Reviews Here
        }
    )
}



@Composable
fun ProfileImage(modifier: Modifier = Modifier, userId: Int? = null)
{
    Card(modifier = modifier, elevation = 5.dp, shape = CircleShape) {
        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(if (userId != null) "" else "https://thiscatdoesnotexist.com/")  /* TODO: Get User Image */
            .crossfade(true)
            .build(),
            placeholder = painterResource(id = R.drawable.placeholder_image),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(CircleShape)
                .size(125.dp),
            contentDescription = null)
    }
}