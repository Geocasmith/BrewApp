package com.example.seng440assignment2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PrefScreen(onBackButtonPress: () -> Unit) {

    var isDarkMode by remember { mutableStateOf(false) }
    var allowNotification by remember { mutableStateOf(false) }
    var openDayDialog by remember { mutableStateOf(false) }

    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val selectedDays = mutableStateListOf<String>()

    if (openDayDialog) {
        Dialog(onDismissRequest = { openDayDialog = false }) {
            LazyColumn {
                items(items = days, key = {days.indexOf(it)}) { day ->
                    Text(text = day)
                    Checkbox(checked = selectedDays.contains(day), onCheckedChange = { isChecked ->
                        if (isChecked) {
                            selectedDays.remove(day)
                        } else {
                            selectedDays.add(day)
                        }
                    })
                }
            }
        }
    }

    Column(Modifier.fillMaxWidth()) {
        TopAppBar(backgroundColor = Color.Transparent, elevation = 1.dp)
        {
            IconButton(onClick = { onBackButtonPress() }) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = null)
            }
            Text(modifier = Modifier.padding(horizontal = 5.dp), text = stringResource(id = R.string.profile_pref))
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .size(60.dp), contentAlignment = Alignment.CenterStart)
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(modifier = Modifier.padding(horizontal = 2.dp),
                            text = stringResource(id = R.string.pref_darkmode),
                            fontSize = 20.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton( onClick = { isDarkMode = !isDarkMode }) {
                            if (!isDarkMode) {
                                Icon(Icons.Outlined.DarkMode, modifier = Modifier.size(50.dp), contentDescription = null)
                            }
                            else {
                                Icon(Icons.Outlined.LightMode, modifier = Modifier.size(50.dp), contentDescription = null)
                            }
                        }
                    }
                }
                Divider(color = Color.Black)
            }
            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .size(60.dp), contentAlignment = Alignment.CenterStart)
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(modifier = Modifier.padding(horizontal = 2.dp),
                            text = stringResource(id = R.string.pref_allow_notifications),
                            fontSize = 20.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(modifier = Modifier.size(50.dp), checked = allowNotification, onCheckedChange = { allowNotification = !allowNotification })
                    }
                }
                Divider(color = Color.Black)
            }

            if (allowNotification) {
                item {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .size(60.dp)
                        , contentAlignment = Alignment.CenterStart)
                    {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(modifier = Modifier.padding(horizontal = 2.dp),
                                text = stringResource(id = R.string.pref_notification_days),
                                fontSize = 20.sp)
                            Spacer(modifier = Modifier.weight(1f))
                            Button(onClick = { openDayDialog = true }) {
                                Text(text = "Set")
                            }
                        }
                    }
                    Divider(color = Color.Black)
                }
            }
        }
    }
}

@Preview
@Composable
fun Prev() {
    PrefScreen {
        null
    }
}

