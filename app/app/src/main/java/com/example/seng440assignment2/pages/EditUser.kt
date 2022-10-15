package com.example.seng440assignment2.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
    var userName by remember { mutableStateOf("") }
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
                        Text(text = stringResource(id = R.string.edit_username) + ": ", fontSize = 20.sp)
                        OutlinedTextField(value = userName, onValueChange = { userName = it }, singleLine = true)
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
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {}
                        ) {
                            Row() {
                                Column {
                                    Text(text = stringResource(id = R.string.edit_fav_beer_types),
                                        fontSize = 20.sp
                                    )
                                    Text(text = stringResource(id = R.string.curr_string) + ": " + selectedBeerTypes.joinToString(", "),
                                        color = Color.LightGray,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.width(270.dp))
                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = {
                                            expanded = false
                                        }
                                    ) {
                                        beerTypes.forEach {
                                            DropdownMenuItem(onClick = {}, enabled = false ) {
                                                Text(text = it.toString())
                                                Spacer(modifier = Modifier.weight(1f))
                                                Checkbox(checked = selectedBeerTypes.contains(it),
                                                    onCheckedChange = { checked ->
                                                        if (checked) {
                                                            selectedBeerTypes.add(it)
                                                        } else {
                                                            selectedBeerTypes.remove(it)
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Button(onClick = { expanded = !expanded }) {
                                    Text(text = stringResource(id = R.string.pref_notification_time_btn))
                                }
                            }
                        }
                    }
                }
                Divider(color = Color.LightGray)
            }
        }
        Row(Modifier.padding(16.dp)) {
            Button(onClick = { /*TODO: Edit User*/ }) {
                Text(text = stringResource(id = R.string.edit_save_btn))
            }
        }
    }
}




