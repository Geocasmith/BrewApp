package com.example.seng440assignment2.pages
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.seng440assignment2.MainViewModel

import com.example.seng440assignment2.components.BeerCard
import kotlinx.coroutines.launch

/**
 * This is the reviews page
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Reviews(
    onNavigateToBeerPage: (String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val sortBy = remember { mutableStateOf("Default") } //default,top rated ASC, top rated DESC, most recent ASC, most recent DESC

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {Text("Reviews")},
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { scaffoldState.drawerState.open() }}) {
                        Icon(Icons.Outlined.FilterAlt, contentDescription = "Filter")
                    }
                },
                backgroundColor = Color.White)

                 },
        drawerContent = {
                        //put the filter settings in the drawer
            Filter(scaffoldState)

                        },
        content = {
                  //lazy column for both beer cards
                    LazyColumn {
                        item {
                            BeerCard("LOST AND GROUNDED", "A crisp and clean beer that will quench your thirst any time","Mike",4,"https://cdn.shopify.com/s/files/1/0178/4982/products/O_zapftis__Render_Web.png?v=1664829695",
                                //pass in onnavigatetobeerpage
                           onNavigateToBeerPage = { onNavigateToBeerPage(it) }
                            )
                        }

//                        item {BeerCard(title = "HAZY IPA", description = "This hazy was amazing! 5/5", name = "Sally", rating = 5, imageLink = "https://www.newworld.co.nz/-/media/Project/Sitecore/Brands/Brand-New-World/Beer-and-Cider/Beer-and-cider-awards-2022/Top-30-tiles/9500-Beer-Baroness-Sunshine-and-Spaceships.jpg?h=auto&w=300&hash=F2A766F47BCE573A74E90AFD41011D34%27")
//                        }
                  }},
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun Filter(scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Filter") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { scaffoldState.drawerState.close() }}) {
                        Icon(Icons.Outlined.Close, contentDescription = "Close")
                    }
                },

                backgroundColor = Color.White
            )

        },
        content = {
            //center the items and add padding
            LazyColumn(
                modifier=Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),

            ) {
                item {
//            default drop down from the compose documentation, credit: https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#DropdownMenu(kotlin.Boolean,kotlin.Function0,androidx.compose.ui.Modifier,androidx.compose.ui.unit.DpOffset,androidx.compose.ui.window.PopupProperties,kotlin.Function1)
                    //sort by drop down
                    val sortByOptions = listOf(
                        "Default",
                        "Most Recent",
                        "Oldest",
                        "Highest Rating",
                        "Lowest Rating"
                    )
                    var expanded by remember { mutableStateOf(false) }
                    var selectedOptionText by remember { mutableStateOf(sortByOptions[0]) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = selectedOptionText,
                            onValueChange = { },
                            label = { Text("Sort By") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            }
                        ) {
                            sortByOptions.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedOptionText = selectionOption
                                        expanded = false
                                    }
                                ) {
                                    Text(text = selectionOption)
                                }
                            }
                        }
                    }
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
                            label = { Text("Rating") },
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
                                    Text(text = selectionOption)
                                }
                            }
                        }
                    }
                }
            }

        },
    )
}
