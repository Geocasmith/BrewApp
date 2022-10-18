package com.example.seng440assignment2.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.seng440assignment2.MainViewModel
import com.example.seng440assignment2.R
import com.example.seng440assignment2.components.BeerCard
import com.example.seng440assignment2.model.BeerListItem

import com.example.seng440assignment2.model.BeerType
import com.example.seng440assignment2.model.ReviewCard
import kotlin.random.Random

/**
 * This is the reviews page
 */
@Composable
fun Categories(onNavigateToBeerListPage: (String) -> Unit) {
    val beerTypeOptions = BeerType.values().map { it.name }
    val scaffoldState = rememberScaffoldState()


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {Text(stringResource(id= R.string.categories_title), color = MaterialTheme.colorScheme.primary)},
                backgroundColor = MaterialTheme.colorScheme.background)

        },
        backgroundColor = MaterialTheme.colorScheme.background,
        content = {
            //lazy column for both beer cards
            LazyColumn(modifier = Modifier.padding(it)) {
                //put two category cards in a row
                items(beerTypeOptions.size / 2) { index ->
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        //first beer card
                        CategoryCard(
                            category = beerTypeOptions[index * 2],
                            onNavigateToBeerListPage = { onNavigateToBeerListPage(it) }
                        )
                        //second beer card
                        CategoryCard(
                            category = beerTypeOptions[index * 2 + 1],
                            onNavigateToBeerListPage = { onNavigateToBeerListPage(it) }
                        )
                    }
                }
            }},
    )
}

@Composable
fun BeerListPage(mainViewModel: MainViewModel, string: Any?, onNavigateToBeerPage: (String) -> Unit){
    val scaffoldState = rememberScaffoldState()
    //convert string to all uppercase
    val beerType = string.toString().uppercase()

    val beerList = remember { mutableStateListOf<BeerListItem>()}
    val reviewRequest = mainViewModel.getArrayRequest(LocalContext.current,
        "beer/type/$beerType", { response ->
        beerList.clear()
        for (i in 0 until response.length()) {
            val item = response.getJSONObject(i)
            var beerRating = 0f
            if(item["rating"].toString() != "null") {
                beerRating = item["rating"].toString().toFloat()
            }
            val review = BeerListItem(
                item["name"].toString(),
                item["barcode"].toString(),
                item["photo_path"].toString(),
                beerRating,
            )
            beerList.add(review)
        }
    })
    mainViewModel.addRequestToQueue(reviewRequest)

    var appBarTitle = stringResource(id=R.string.categories_alternative_title)
    if(string is String){
        appBarTitle = string
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {Text(appBarTitle)},
                backgroundColor = Color.White)

        },
    ) {
        //lazy column for both beer cards
        LazyColumn(modifier = Modifier.padding(it)) {
            beerList.map { beerListItem ->
                item {
                    BeerCard(
                        beerName = beerListItem.title,
                        imageLink = beerListItem.photoURL,
                        rating = beerListItem.averageRating,
                        onNavigateToBeerPage = { onNavigateToBeerPage(beerListItem.barcode) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryCard(category:String,onNavigateToBeerListPage: (String) -> Unit){

    //colored square with text inside
    //generate random color
    val randomColor = remember { mutableStateOf(Color(Random.nextInt(0, 255), Random.nextInt(0, 255), Random.nextInt(0, 255))) }
    Box(modifier = Modifier){

        //colored square
        Box(modifier = Modifier
            .padding(10.dp)
            .size(170.dp)
            .background(color = randomColor.value)
            .clickable(onClick = { onNavigateToBeerListPage(category) })
        )
        //text on inside of square
        Text(text = category, modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.contentColorFor(randomColor.value))
    }
}