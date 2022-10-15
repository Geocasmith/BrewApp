package com.example.seng440assignment2

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
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
import com.example.seng440assignment2.components.BeerCard
import com.example.seng440assignment2.model.Review

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(onNavigateToEdit: () -> Unit, onNavigateToPref: () -> Unit)
{
        /* TODO: Add Reviews */
        var reviews = listOf<Review>()
        var options by remember { mutableStateOf(false) }


    LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
            stickyHeader { TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp ){
                Spacer(Modifier.weight(1f))

                // Options
                Box(
                    Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopEnd)) {
                    IconButton(onClick = { options = true }) {
                        Icon(Icons.Outlined.MoreVert, contentDescription = null )
                    }
                    DropdownMenu(expanded = options, onDismissRequest = { options = false }) {
                        DropdownMenuItem(onClick = {
                            options = false
                            onNavigateToEdit()
                        }) {
                            Text(text = stringResource(id = R.string.profile_edit))
                        }
                        DropdownMenuItem(onClick = {
                            options = false
                            onNavigateToPref()
                        }) {
                            Text(text = stringResource(id = R.string.profile_pref))
                        }
                        DropdownMenuItem(onClick = {
                            options = false
                            /* TODO: Logout */
                        }) {
                            Text(text = stringResource(id = R.string.profile_logout))
                        }
                    }
                }

                }
                Column(Modifier.fillMaxWidth()) {
                    ProfileImage(modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp))

                    Box(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Placeholder Name", fontSize = 20.sp)
                            Text(text = "Placeholder Bio with Information", color = Color.LightGray)
                        }
                    }
                }
            }

            if (reviews == null) {
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
                items(reviews, key = { it.id }) {
                    BeerCard(title = it.title, description = it.description, name = it.reviewer.name, rating = it.rating, imageLink = it.beer.photoURL)
                }
            }
            // Add Reviews Here
        }
    )
}


@Composable
fun EditScreen(onBackButtonPress: () -> Unit) {
    Column() {
        TopAppBar(backgroundColor = Color.LightGray, elevation = 0.dp )
        {
            IconButton(onClick = { onBackButtonPress() }) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = null)
            }
            Text(modifier = Modifier.padding(horizontal = 5.dp), text = stringResource(id = R.string.profile_pref))
        }

        Box(modifier = Modifier.fillMaxWidth())
        {
            Row() {
                Text(text = stringResource(id = R.string.pref_darkmode))
                Spacer(modifier = Modifier.weight(1f))
            }
        }

    }
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