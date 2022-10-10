package com.example.seng440assignment2.Pages
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color

import androidx.compose.runtime.Composable
import com.example.seng440assignment2.Components.BeerCard

/**
 * This is the reviews page
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Reviews() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {Text("Reviews")},
                navigationIcon = {
                    IconButton(onClick = { /*TODO:OPEN DRAWER*/ }) {
                        Icon(Icons.Outlined.FilterAlt, contentDescription = "Filter")
                    }
                },
                backgroundColor = Color.White)

                 },

//        floatingActionButtonPosition = FabPosition.End,
//        floatingActionButton = { FloatingActionButton(onClick = {}){
//            Text("X")
//        } },
        drawerContent = { Text(text = "drawerContent") },
        content = {
                  //lazy column for both beer cards
                    LazyColumn {
                        item {
                            BeerCard("LOST AND GROUNDED", "A crisp and clean beer that will quench your thirst any time","Mike",4,"https://cdn.shopify.com/s/files/1/0178/4982/products/O_zapftis__Render_Web.png?v=1664829695")
                        }
                        item {BeerCard(title = "HAZY IPA", description = "This hazy was amazing! 5/5", name = "Sally", rating = 5, imageLink = "https://www.newworld.co.nz/-/media/Project/Sitecore/Brands/Brand-New-World/Beer-and-Cider/Beer-and-cider-awards-2022/Top-30-tiles/9500-Beer-Baroness-Sunshine-and-Spaceships.jpg?h=auto&w=300&hash=F2A766F47BCE573A74E90AFD41011D34%27")
                        }
                  }},
    )
}
