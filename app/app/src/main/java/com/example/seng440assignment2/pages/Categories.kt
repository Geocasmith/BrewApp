package com.example.seng440assignment2.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.seng440assignment2.components.BeerCard

import com.example.seng440assignment2.model.BeerType
import kotlin.random.Random

/**
 * This is the reviews page
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories(onNavigateToBeerListPage: (String) -> Unit) {
    val beerTypeOptions = BeerType.values().map { it.name }
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {Text("Beer Types")},
                backgroundColor = Color.White)

        },
        content = {
            //lazy column for both beer cards
            LazyColumn {
                //put two categorycards in a row
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerListPage(string: Any?,onNavigateToBeerPage: (String) -> Unit){
    val beerTypeOptions = BeerType.values().map { it.name }
    val scaffoldState = rememberScaffoldState()

    var appBarTitle:String = "Beers"
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
        content = {
            //lazy column for both beer cards
            LazyColumn {
                //put two categorycards in a row
                item{
                    BeerCard(beerName = "HAZY IPA", rating=3.7.toLong(), imageLink = "https://www.newworld.co.nz/-/media/Project/Sitecore/Brands/Brand-New-World/Beer-and-Cider/Beer-and-cider-awards-2022/Top-30-tiles/9500-Beer-Baroness-Sunshine-and-Spaceships.jpg?h=auto&w=300&hash=F2A766F47BCE573A74E90AFD41011D34%27", onNavigateToBeerPage = {onNavigateToBeerPage(it)})
                }
            }},
    )
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
        Text(text = category, modifier = Modifier.align(Alignment.Center))
    }
}