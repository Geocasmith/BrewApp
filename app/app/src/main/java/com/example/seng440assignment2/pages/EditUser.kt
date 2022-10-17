package com.example.seng440assignment2.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seng440assignment2.MainViewModel
import com.example.seng440assignment2.R
import com.example.seng440assignment2.model.BeerType
import com.example.seng440assignment2.components.SpacerDP

class EditUserViewModel: ViewModel() {

    val beerTypes = BeerType.values()

    var expanded:Boolean by mutableStateOf(false)
    var userName:String by mutableStateOf("")
    var userBeerTypes:MutableList<BeerType> by mutableStateOf(mutableListOf())
    


    fun save() {
        /* TODO: call database */
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditScreen(editUserViewModel: EditUserViewModel = viewModel(), mainViewModel: MainViewModel, onBackButtonPress: () -> Unit) {

    val beerTypes = BeerType.values()

    var selectedBeerTypes = remember { mutableStateListOf<BeerType>() }

    //cycle through editUserViewModel.userBeerTypes
    //if the beer type is in the list, add it to selectedBeerTypes
    //if not, remove it from selectedBeerTypes
    for (beerType in beerTypes) {
        if (beerType in editUserViewModel.userBeerTypes) {
            if (beerType !in selectedBeerTypes) {
                selectedBeerTypes.add(beerType)
            }
        } else {
            if (beerType in selectedBeerTypes) {
                selectedBeerTypes.remove(beerType)
            }
        }
    }
    //    selectedBeerTypes.add(beer)

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
                OutlinedTextField(
                    value = editUserViewModel.userName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onValueChange = { editUserViewModel.userName = it },
                    label = {
                        androidx.compose.material3.Text(
                            text = stringResource(id = R.string.edit_username),
                            color = Color.Black.copy(alpha = 0.6f)
                        )
                    }
                )

            }
            item {SpacerDP(6)}
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .size(60.dp), contentAlignment = Alignment.CenterStart
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ExposedDropdownMenuBox(
                            expanded = editUserViewModel.expanded,
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
                                        expanded = editUserViewModel.expanded,
                                        onDismissRequest = {
                                            editUserViewModel.expanded = false
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
                                                            editUserViewModel.userBeerTypes.add(it)
                                                        } else {
                                                            selectedBeerTypes.remove(it)
                                                            editUserViewModel.userBeerTypes.remove(it)
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Button(onClick = { editUserViewModel.expanded = !editUserViewModel.expanded }) {
                                    Text(text = stringResource(id = R.string.pref_notification_time_btn))
                                }
                            }
                        }
                    }
                }
            }
        }
        Row(Modifier.padding(16.dp)) {
            androidx.compose.material3.Button(
                modifier = Modifier
                    .width(width = 140.dp)
                    .height(height = 48.dp)
                    .padding(horizontal = 16.dp),
                onClick = { /*TODO: Do something! */ },
                shape = RoundedCornerShape(4.dp)
            ) {
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.edit_save_btn),
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
    }
}



