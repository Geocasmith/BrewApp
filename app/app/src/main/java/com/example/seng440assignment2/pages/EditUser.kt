package com.example.seng440assignment2.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.seng440assignment2.components.SpacerDP
import com.example.seng440assignment2.model.BeerType
import org.json.JSONObject

class EditUserViewModel : ViewModel() {

    var expanded by mutableStateOf(false)
    var userName by mutableStateOf("")
    var userBio by mutableStateOf("")
    var userBeerTypes = mutableStateListOf<BeerType>()

    fun getValues(mainViewModel: MainViewModel, context: Context) {
        if (userName.isNotEmpty() || userBio.isNotEmpty() || userBeerTypes.isNotEmpty()) return
        val userRequest = mainViewModel.getObjectRequest(
            context,
            "users/" + mainViewModel.getUserId(),
            { response ->
                userName = response["name"].toString()
                userBio = response["bio"].toString()
                userBeerTypes.clear()
                if (response["favourites"].toString() != "null") {
                    val beerList = response["favourites"].toString().split(",")
                    beerList.forEach { beer ->
                        val type: BeerType
                        try {
                            type = BeerType.valueOf(beer.filter { !it.isWhitespace() })
                        } catch (exception: IllegalArgumentException) {
                            return@forEach
                        }
                        userBeerTypes.add(type)
                    }
                }
            })

        mainViewModel.addRequestToQueue(userRequest)
    }

    fun save(mainViewModel: MainViewModel, context: Context, callback: () -> Unit = {}) {
        if (userName.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.no_name), Toast.LENGTH_SHORT).show()
            return
        }

        val requestJson = JSONObject()
        requestJson.put("name", userName)
        requestJson.put("bio", userBio)
        requestJson.put("favourites", userBeerTypes.joinToString())

        val request = mainViewModel.patchObjectRequest(context, "users", requestJson, { _ ->
            Toast.makeText(context, context.getString(R.string.profile_updated), Toast.LENGTH_SHORT)
                .show()
            callback()
        })

        mainViewModel.addRequestToQueue(request)
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun EditScreen(
    editUserViewModel: EditUserViewModel = viewModel(),
    mainViewModel: MainViewModel,
    onBackButtonPress: () -> Unit
) {
    val context = LocalContext.current

    val beerTypes = BeerType.values()

    val selectedBeerTypes = remember { mutableStateListOf<BeerType>() }
    LaunchedEffect(Unit) {
        editUserViewModel.getValues(mainViewModel, context)
    }

    // Updates selectedBeerTypes to match the viewmodel
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


    LazyColumn(
        Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        stickyHeader {
            TopAppBar(backgroundColor = MaterialTheme.colorScheme.background, elevation = 1.dp)
            {
                IconButton(onClick = { onBackButtonPress() }) {
                    Icon(
                        Icons.Outlined.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = stringResource(id = R.string.edit_title),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        item {
            OutlinedTextField(
                value = editUserViewModel.userName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onValueChange = { editUserViewModel.userName = it },
                label = {
                    Text(
                        text = stringResource(id = R.string.edit_username),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.secondary),
            )

        }
        item { SpacerDP(6) }
        item {
            OutlinedTextField(
                value = editUserViewModel.userBio,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onValueChange = { editUserViewModel.userBio = it },
                label = {
                    Text(
                        text = stringResource(id = R.string.edit_bio),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.secondary)
            )
        }
        item { SpacerDP(6) }
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
                        Row {
                            Column {
                                Text(
                                    text = stringResource(id = R.string.edit_fav_beer_types),
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = stringResource(id = R.string.curr_string) + ": " + selectedBeerTypes.joinToString(
                                        ", "
                                    ),
                                    color = MaterialTheme.colorScheme.secondary,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.width(270.dp)
                                )
                                ExposedDropdownMenu(
                                    modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                                    expanded = editUserViewModel.expanded,
                                    onDismissRequest = {
                                        editUserViewModel.expanded = false
                                    }
                                ) {
                                    beerTypes.forEach {
                                        DropdownMenuItem(onClick = {}, enabled = false) {
                                            Text(
                                                text = it.toString(),
                                                color = MaterialTheme.colorScheme.secondary
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            Checkbox(checked = selectedBeerTypes.contains(it),
                                                onCheckedChange = { checked ->
                                                    if (checked) {
                                                        selectedBeerTypes.add(it)
                                                        editUserViewModel.userBeerTypes.add(it)
                                                    } else {
                                                        selectedBeerTypes.remove(it)
                                                        editUserViewModel.userBeerTypes.remove(
                                                            it
                                                        )
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Button(
                                onClick = {
                                    editUserViewModel.expanded = !editUserViewModel.expanded
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.pref_notification_time_btn),
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }
        }
        item {
            Row(Modifier.padding(16.dp)) {
                Button(
                    modifier = Modifier
                        .width(width = 140.dp)
                        .height(height = 48.dp)
                        .padding(horizontal = 16.dp),
                    onClick = {
                        editUserViewModel.save(mainViewModel, context, callback = onBackButtonPress)
                        Toast.makeText(
                            context,
                            context.getString(R.string.saving_profile),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        text = stringResource(id = R.string.edit_save_btn),
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
        }
    }
}



