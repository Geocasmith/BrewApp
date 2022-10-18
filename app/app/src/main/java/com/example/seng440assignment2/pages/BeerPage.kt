package com.example.seng440assignment2.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.seng440assignment2.MainViewModel
import com.example.seng440assignment2.R
import com.example.seng440assignment2.components.BeerPageReviewCard
import com.example.seng440assignment2.components.RatingStarsFloat
import com.example.seng440assignment2.model.BeerPageReviewCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject


class BeerPageViewModel : ViewModel() {

    var beerId by mutableStateOf(-1L)
    var beerName by mutableStateOf("")
    var beerType by mutableStateOf("")
    var beerRating by mutableStateOf(0f)
    var beerImageUrl by mutableStateOf("")

    var newReview by mutableStateOf("")
    var newRating by mutableStateOf(1)

    fun canSave(): Boolean {
        return newReview != ""
    }

    fun clearReview() {
        newReview = ""
        newRating = 1
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerPage(
    beerViewModel: BeerPageViewModel = viewModel(),
    mainViewModel: MainViewModel,
    barcode: String?
) {
    val reviews = remember { mutableStateListOf<BeerPageReviewCard>() }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    //    Get the beer info from the API
    beerViewModel.beerName = stringResource(id = R.string.generic_loading)
    beerViewModel.beerType = stringResource(id = R.string.generic_loading)
    beerViewModel.beerImageUrl = stringResource(id = R.string.generic_loading)

    val request = mainViewModel.getObjectRequest(context, "beer/$barcode", { jsonResponse ->
        beerViewModel.beerId = jsonResponse["id"].toString().toLong()
        beerViewModel.beerName = jsonResponse["name"].toString()
        beerViewModel.beerType = jsonResponse["type"].toString()

        if (jsonResponse["rating"].toString() != "null") {
            beerViewModel.beerRating = jsonResponse["rating"].toString().toFloat()
        }
        beerViewModel.beerImageUrl = jsonResponse["photo_path"].toString()
    })
    mainViewModel.addRequestToQueue(request)

    //Get the review data from the API

    val reviewRequest = mainViewModel.getArrayRequest(
        LocalContext.current,
        "review/${beerViewModel.beerId}",
        { response ->
            if (response.length() != 0) {
                reviews.clear()
                for (i in 0 until response.length()) {
                    val item = response.getJSONObject(i)
                    val review = BeerPageReviewCard(
                        item["id"].toString().toLong(),
                        item["rating"].toString().toInt(),
                        item["title"].toString(),
                        item["reviewerName"].toString(),
                    )
                    reviews.add(review)
                }
            }
        })
    mainViewModel.addRequestToQueue(reviewRequest)

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            //put the filter settings in the drawer
            Review(
                beerViewModel,
                mainViewModel,
                scaffoldState = scaffoldState,
                scope = scope,
                ctx = context
            )

        },
        content = {
            Column {
                Box(modifier = Modifier
                    .padding(10.dp)
                    .background(Color.White)
                    .height(215.dp)) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(LocalContext.current).data(beerViewModel.beerImageUrl).error(R.drawable.beer_icon).build(),
                        contentDescription = null,
                        //content scale fill width
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = beerViewModel.beerName,
                            color = MaterialTheme.colorScheme.primary,
                            lineHeight = 40.sp,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            //                    TODO:Make the name clickable to go to the user's profile
                            Text(
                                text = beerViewModel.beerType,
                                color = MaterialTheme.colorScheme.secondary,
                                lineHeight = 16.sp,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Row {
                                //filled star for rating and unfilled for remaining out of 5
                                RatingStarsFloat(beerViewModel.beerRating)
                            }
                        }

                    }
                }
                //                    RatingStarsFloat(beerViewModel.beerRating)

                ReviewButton(scaffoldState = scaffoldState, scope = scope)

                //divider line
                Divider(
                    color = Color.Black.copy(alpha = 0.12f),
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 1.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))


                LazyColumn {

                    items(reviews, key = { it.id }) {
                        BeerPageReviewCard(
                            reviewContent = it.title,
                            reviewerName = it.reviewerName,
                            rating = it.rating
                        )
                    }
                }
            }


        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReviewButton(scaffoldState: ScaffoldState, scope: CoroutineScope) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(height = 48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        onClick = {
            scope.launch { scaffoldState.drawerState.open() }
        },
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.review_button_title),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp
            ),
            modifier = Modifier
                .width(width = 96.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubmitReviewButton(
    beerViewModel: BeerPageViewModel,
    mainViewModel: MainViewModel,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    context: Context
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        onClick = {
            if (beerViewModel.canSave()) {
                val jsonRequest = JSONObject()
                jsonRequest.put("title", beerViewModel.newReview)
                jsonRequest.put("rating", beerViewModel.newRating)

                val request = mainViewModel.postObjectRequest(
                    context,
                    "review/${beerViewModel.beerId}",
                    jsonRequest,
                    {
                        Toast.makeText(
                            context,
                            context.getString(R.string.submitToast),
                            Toast.LENGTH_SHORT
                        ).show()
                        beerViewModel.clearReview()
                        scope.launch { scaffoldState.drawerState.close() }
                    })
                mainViewModel.addRequestToQueue(request)
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.new_cant_save),
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.submit_button_title),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp
            ),
            modifier = Modifier
                .width(width = 96.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun Review(
    beerViewModel: BeerPageViewModel,
    mainViewModel: MainViewModel,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    ctx: Context
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.review_drawer_title), color = MaterialTheme.colorScheme.primary) },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { scaffoldState.drawerState.close() }
                    }) {
                        Icon(
                            Icons.Outlined.Close,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.background
            )

        },
        content = {
            // center the items and add padding
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),

                ) {
                item {
                    //review input box
                    OutlinedTextField(
                        value = beerViewModel.newReview,
                        onValueChange = { beerViewModel.newReview = it },
                        colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.secondary),
                        label = {
                            Text(
                                stringResource(id = R.string.review_drawer_input),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                }
                item {
                    //rating drop down
                    val ratingOptions = (1..5).toList()
                    var ratingExpanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = ratingExpanded,
                        onExpandedChange = {
                            ratingExpanded = !ratingExpanded
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = "${beerViewModel.newRating} ${stringResource(id = R.string.review_drawer_stars)}",
                            onValueChange = { },
                            label = { Text(stringResource(id = R.string.review_drawer_rating_title), color = MaterialTheme.colorScheme.primary) },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = ratingExpanded
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = MaterialTheme.colorScheme.secondary,
                                trailingIconColor = MaterialTheme.colorScheme.primary)
                        )
                        ExposedDropdownMenu(
                            expanded = ratingExpanded,
                            onDismissRequest = {
                                ratingExpanded = false
                            },
                            modifier = Modifier.background(MaterialTheme.colorScheme.background)
                        ) {
                            ratingOptions.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = {
                                        beerViewModel.newRating = selectionOption
                                        ratingExpanded = false
                                    }
                                ) {
                                    Text(
                                        text = "${selectionOption} ${stringResource(id = R.string.review_drawer_stars)}",
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    SubmitReviewButton(
                        beerViewModel,
                        mainViewModel,
                        scaffoldState = scaffoldState,
                        scope = scope,
                        context = ctx
                    )
                }
            }
        })
}