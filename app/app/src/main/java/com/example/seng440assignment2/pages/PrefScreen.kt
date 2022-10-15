package com.example.seng440assignment2.pages

import android.app.TimePickerDialog
import android.util.Log
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seng440assignment2.MainViewModel
import com.example.seng440assignment2.R
import com.example.seng440assignment2.notifications.ReminderNotificationService
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun PrefScreen(mainViewModel: MainViewModel, onBackButtonPress: () -> Unit) {

    val appSettings = mainViewModel.getAppSettings()
    val scope = rememberCoroutineScope()

    // Notifications
    val context = LocalContext.current
    val reminderNotificationService = ReminderNotificationService(context)

    // Time Picker Content
    val mCalendar = Calendar.getInstance()
    val hour = mCalendar[Calendar.HOUR_OF_DAY]
    val minute = mCalendar[Calendar.MINUTE]

    val notifyTime = appSettings.notificationTime

    val timePickerDialog = TimePickerDialog(
        LocalContext.current,
        {_, mHour : Int, mMinute: Int ->
            scope.launch {
                var strMin = mMinute.toString()
                var strHour = mHour.toString()

                if (mMinute < 10) {
                    strMin = "0$strMin"
                }

                if (mHour < 10) {
                    strHour = "0$strHour"
                }

                val timeString = "$strHour:$strMin"
                mainViewModel.updateNotificationTime(timeString)
                // Update Reminder Time
                reminderNotificationService.setReminder(timeString)
            }
        }, hour, minute, false
    )

    Column(Modifier.fillMaxWidth()) {
        TopAppBar(backgroundColor = Color.Transparent, elevation = 1.dp)
        {
            IconButton(onClick = { onBackButtonPress() }) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = null)
            }
            Text(modifier = Modifier.padding(horizontal = 5.dp), text = stringResource(id = R.string.profile_pref))
        }
        LazyColumn(Modifier.padding(horizontal = 10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .size(60.dp), contentAlignment = Alignment.CenterStart)
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(horizontal = 2.dp),
                            text = stringResource(id = R.string.pref_darkmode),
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            scope.launch {
                                mainViewModel.setIsDarkMode(!appSettings.isDarkMode)
                            }
                        }) {
                            if (!appSettings.isDarkMode) {
                                Icon(
                                    Icons.Outlined.DarkMode,
                                    modifier = Modifier.size(50.dp),
                                    contentDescription = null
                                )
                            } else {
                                Icon(
                                    Icons.Outlined.LightMode,
                                    modifier = Modifier.size(50.dp),
                                    tint = Color.White,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
                Divider(color = Color.LightGray)
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
                        Switch(modifier = Modifier.size(50.dp), checked = appSettings.allowNotifications,
                            onCheckedChange = {
                                scope.launch {
                                    mainViewModel.setAllowNotifications(it)
                                    if (it) {
                                        reminderNotificationService.setReminder(appSettings.notificationTime)
                                    } else {
                                        reminderNotificationService.removeReminder()
                                    }

                                }
                            }
                        )
                    }
                }
                Divider(color = Color.LightGray)
            }

            if (appSettings.allowNotifications) {
                item {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .size(60.dp)
                        , contentAlignment = Alignment.CenterStart)
                    {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column() {
                                Text(modifier = Modifier.padding(horizontal = 2.dp),
                                    text = stringResource(id = R.string.pref_notification_time),
                                    fontSize = 20.sp)
                                Text(modifier = Modifier.padding(horizontal = 2.dp),
                                    text = stringResource(id = R.string.pref_notification_time_curr) + ": $notifyTime",
                                    fontSize = 15.sp,
                                    color = Color.Gray)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Button(onClick = { timePickerDialog.show() }) {
                                Text(stringResource(id = R.string.pref_notification_time_btn))
                            }
                        }
                    }
                    Divider(color = Color.LightGray)
                }
            }
        }
    }
}

