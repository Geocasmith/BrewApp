package com.example.seng440assignment2.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seng440assignment2.MainViewModel
import com.example.seng440assignment2.R
import com.example.seng440assignment2.model.BeerType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditScreen(mainViewModel: MainViewModel, onBackButtonPress: () -> Unit) {

    val beerTypes = BeerType.values()

    var expanded by remember { mutableStateOf(false) }
    var selectedBeerTypes = remember { mutableStateListOf<BeerType>() }

    Column(Modifier.fillMaxWidth()) {
        TopAppBar(backgroundColor = Color.Transparent, elevation = 1.dp)
        {
            IconButton(onClick = { onBackButtonPress() }) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = null)
            }
            Text(
                modifier = Modifier.padding(horizontal = 5.dp),
                text = stringResource(id = R.string.edit_title)
            )
        }

        LazyColumn(Modifier.padding(horizontal = 10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .size(60.dp), contentAlignment = Alignment.CenterStart
                )
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                    }
                }
                Divider(color = Color.LightGray)
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .size(60.dp), contentAlignment = Alignment.CenterStart
                )
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                            }
                        ) {
                            TextField(
                                readOnly = true,
                                value = selectedBeerTypes.toString(),
                                onValueChange = { },
                                label = { androidx.compose.material3.Text("Sort By") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors()
                            )
                        }
                    }
                    Divider(color = Color.LightGray)
                }
            }
        }
    }
}




