package com.example.seng440assignment2.pages

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScaffoldState
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
import coil.compose.AsyncImage
import com.example.seng440assignment2.MainViewModel
import com.example.seng440assignment2.R
import com.example.seng440assignment2.components.RatingStars
import com.example.seng440assignment2.components.RatingStarsLong
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerPage(mainViewModel: MainViewModel, barcode: String?) {
    val scaffoldState = androidx.compose.material3.rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var beerName by remember { mutableStateOf(context.getString(R.string.generic_loading)) }
    var beerType by remember { mutableStateOf(context.getString(R.string.generic_loading)) }
    var beerRating by remember { mutableStateOf(0L)}
    var beerImageUrl by remember { mutableStateOf(context.getString(R.string.generic_loading)) }


    mainViewModel.getObjectRequest(context, "beer/$barcode", { jsonResponse ->
        beerName = jsonResponse["name"].toString()
        beerType = jsonResponse["type"].toString()
        beerRating = jsonResponse["rating"].toString().toLong()
        beerImageUrl = jsonResponse["photo_path"].toString()
    })


    androidx.compose.material3.Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            //put the filter settings in the drawer
            Review(scaffoldState = scaffoldState,scope=scope,ctx=context)

        },
        content = {
            Column {
                AsyncImage(
                    model = beerImageUrl,
                    contentDescription = null,
                    modifier = Modifier.height(215.dp),
                    //content scale fill width
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.Center
                )
                // Beer Name
                Text(
                    text = beerName,
                    color = Color.Black.copy(alpha = 0.87f),
                    lineHeight = 40.sp,
                    style = MaterialTheme.typography.h4
                )
                // Beer Type
                Text(
                    text = beerType,
                    color = Color.Black.copy(alpha = 0.6f),
                    lineHeight = 28.sp,
                    style = TextStyle(
                        fontSize = 16.sp,
                        letterSpacing = 0.44.sp
                    ),
                    modifier = Modifier
                        .width(width = 328.dp)
                        .height(height = 102.dp)
                )
                //divider line
                Divider(
                    color = Color.Black.copy(alpha = 0.12f),
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 1.dp)
                )
                Row {
                    //filled star for rating and unfilled for remaining out of 5
                    RatingStarsLong(beerRating)
                }
                ReviewButton(scaffoldState = scaffoldState,scope = scope)
            }


        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReviewButton(scaffoldState: ScaffoldState, scope: CoroutineScope) {
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 48.dp),
        onClick = {
            scope.launch { scaffoldState.drawerState.open() }
        },
        shape = RoundedCornerShape(4.dp)
    ) {
        androidx.compose.material3.Text(
            text = "Review",
            color = Color.White,
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
private fun SubmitReviewButton(scaffoldState: ScaffoldState, scope: CoroutineScope, ctx: Context) {
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 48.dp),
        onClick = {
            //TODO: API CALL HERE TO SUBMIT REVIEW
            scope.launch { scaffoldState.drawerState.close()
//                Toast.makeText(ctx, ctx.getString(R.string.submitToast), Toast.LENGTH_SHORT).show()
            }
        },
        shape = RoundedCornerShape(4.dp)
    ) {
        androidx.compose.material3.Text(
            text = "Submit",
            color = Color.White,
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
private fun Review(scaffoldState: ScaffoldState, scope: CoroutineScope, ctx: Context) {
    androidx.compose.material3.Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { androidx.compose.material3.Text("Review") },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = {
                        scope.launch { scaffoldState.drawerState.close() }
                    }) {
                        androidx.compose.material3.Icon(
                            Icons.Outlined.Close,
                            contentDescription = "Close"
                        )
                    }
                },

                backgroundColor = Color.White
            )

        },
        content = {
            // center the items and add padding
            var text by remember { mutableStateOf<String>("") }
            LazyColumn(
                modifier=Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),

                ) {
                item {
                    //review input box
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = {
                            androidx.compose.material3.Text(
                                "Review",
                                color = Color.Black.copy(alpha = 0.6f)
                            )
                        }
                    )
                }
                item {
                    //rating drop down
                    val ratingOptions =
                        listOf("5 Stars", "4 Stars", "3 Stars", "2 Stars", "1 Star")
                    var ratingExpanded by remember { mutableStateOf(false) }
                    var ratingSelectedOptionText by remember { mutableStateOf(ratingOptions[0]) }
                    ExposedDropdownMenuBox(
                        expanded = ratingExpanded,
                        onExpandedChange = {
                            ratingExpanded = !ratingExpanded
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = ratingSelectedOptionText,
                            onValueChange = { },
                            label = { androidx.compose.material3.Text("Rating") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = ratingExpanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = ratingExpanded,
                            onDismissRequest = {
                                ratingExpanded = false
                            }
                        ) {
                            ratingOptions.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = {
                                        ratingSelectedOptionText = selectionOption
                                        ratingExpanded = false
                                    }
                                ) {
                                    androidx.compose.material3.Text(text = selectionOption)
                                }
                            }
                        }
                    }
                }
                item{SubmitReviewButton(scaffoldState = scaffoldState,scope = scope,ctx = ctx)}
            //rating stars
}})}