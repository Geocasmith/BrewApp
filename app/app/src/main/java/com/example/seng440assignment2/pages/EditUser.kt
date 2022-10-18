package com.example.seng440assignment2.pages

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import com.example.seng440assignment2.model.BeerType
import com.example.seng440assignment2.components.SpacerDP
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
                    beerList.forEach { beer -> userBeerTypes.add(BeerType.valueOf(beer.filter { !it.isWhitespace() })) }
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditScreen(
    editUserViewModel: EditUserViewModel = viewModel(),
    mainViewModel: MainViewModel,
    onBackButtonPress: () -> Unit
) {
    val context = LocalContext.current

    val beerTypes = BeerType.values()

    var selectedBeerTypes = remember { mutableStateListOf<BeerType>() }
    LaunchedEffect(Unit) {
        editUserViewModel.getValues(mainViewModel, context)
    }

    //Updates selectedBeerTypes to match the viewmodel
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

        LazyColumn(
            Modifier
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
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
            item { SpacerDP(6) }
            item {
                OutlinedTextField(
                    value = editUserViewModel.userBio,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onValueChange = { editUserViewModel.userBio = it },
                    label = {
                        androidx.compose.material3.Text(
                            text = stringResource(id = R.string.edit_bio),
                            color = Color.Black.copy(alpha = 0.6f)
                        )
                    }
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
                            Row() {
                                Column {
                                    Text(
                                        text = stringResource(id = R.string.edit_fav_beer_types),
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = stringResource(id = R.string.curr_string) + ": " + selectedBeerTypes.joinToString(
                                            ", "
                                        ),
                                        color = Color.LightGray,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.width(270.dp)
                                    )
                                    ExposedDropdownMenu(
                                        expanded = editUserViewModel.expanded,
                                        onDismissRequest = {
                                            editUserViewModel.expanded = false
                                        }
                                    ) {
                                        beerTypes.forEach {
                                            DropdownMenuItem(onClick = {}, enabled = false) {
                                                Text(text = it.toString())
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
                                Button(onClick = {
                                    editUserViewModel.expanded = !editUserViewModel.expanded
                                }) {
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
                onClick = {
                    editUserViewModel.save(mainViewModel, context, callback = onBackButtonPress)
                    Toast.makeText(
                        context,
                        context.getString(R.string.saving_profile),
                        Toast.LENGTH_SHORT
                    ).show()
                },
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



