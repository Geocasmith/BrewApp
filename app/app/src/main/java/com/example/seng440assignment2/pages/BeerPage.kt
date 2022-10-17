package com.example.seng440assignment2.pages

import android.content.Context
import android.widget.Toast
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.seng440assignment2.MainViewModel
import com.example.seng440assignment2.R
import com.example.seng440assignment2.components.RatingStarsFloat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject


class BeerPageViewModel: ViewModel() {

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
fun BeerPage(beerViewModel: BeerPageViewModel = viewModel(), mainViewModel: MainViewModel, barcode: String?) {
    val scaffoldState = androidx.compose.material3.rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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

    androidx.compose.material3.Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            //put the filter settings in the drawer
            Review(beerViewModel, mainViewModel, scaffoldState = scaffoldState,scope=scope,ctx=context)

        },
        content = {
            Column {
                AsyncImage(
                    model = beerViewModel.beerImageUrl,
                    contentDescription = null,
                    modifier = Modifier.height(215.dp),
                    //content scale fill width
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.Center
                )
                // Beer Name
                Text(
                    text = beerViewModel.beerName,
                    color = Color.Black.copy(alpha = 0.87f),
                    lineHeight = 40.sp,
                    style = MaterialTheme.typography.h4
                )
                // Beer Type
                Text(
                    text = beerViewModel.beerType,
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
                    RatingStarsFloat(beerViewModel.beerRating)
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
private fun SubmitReviewButton(beerViewModel: BeerPageViewModel, mainViewModel: MainViewModel, scaffoldState: ScaffoldState, scope: CoroutineScope, context: Context) {
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 48.dp),
        onClick = {
            if (beerViewModel.canSave()) {
                val jsonRequest = JSONObject()
                jsonRequest.put("title", beerViewModel.newReview)
                jsonRequest.put("rating", beerViewModel.newRating)

                val request = mainViewModel.postObjectRequest(context, "review/${beerViewModel.beerId}", jsonRequest, {
                    Toast.makeText(context, context.getString(R.string.submitToast), Toast.LENGTH_SHORT).show()
                    beerViewModel.clearReview()
                    scope.launch { scaffoldState.drawerState.close() }
                })
                mainViewModel.addRequestToQueue(request)
            } else {
                Toast.makeText(context, context.getString(R.string.new_cant_save), Toast.LENGTH_SHORT).show()
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
private fun Review(beerViewModel: BeerPageViewModel, mainViewModel: MainViewModel,scaffoldState: ScaffoldState, scope: CoroutineScope, ctx: Context) {
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
            LazyColumn(
                modifier=Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),

                ) {
                item {
                    //review input box
                    OutlinedTextField(
                        value = beerViewModel.newReview,
                        onValueChange = { beerViewModel.newReview = it },
                        label = {
                            androidx.compose.material3.Text(
                                "Review*",
                                color = Color.Black.copy(alpha = 0.6f)
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
                            value = "${beerViewModel.newRating} stars",
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
                                        beerViewModel.newRating = selectionOption
                                        ratingExpanded = false
                                    }
                                ) {
                                    androidx.compose.material3.Text(text = "$selectionOption stars")
                                }
                            }
                        }
                    }
                }
                item{SubmitReviewButton(beerViewModel, mainViewModel, scaffoldState = scaffoldState,scope = scope, context = ctx)}
}})}