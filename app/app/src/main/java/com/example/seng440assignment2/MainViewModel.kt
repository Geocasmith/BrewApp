package com.example.seng440assignment2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.seng440assignment2.datastore.AppSettings


class MainViewModelFactory(private val settingsDataStore: DataStore<AppSettings>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(settingsDataStore) as T
    }
}

class MainViewModel(private var settingsDataStore: DataStore<AppSettings>) : ViewModel() {

    suspend fun setIsDarkMode(darkMode: Boolean) {
        settingsDataStore.updateData {
            it.copy(isDarkMode = darkMode)
        }
    }

    suspend fun setAllowNotifications(allow: Boolean) {
        settingsDataStore.updateData {
            it.copy(allowNotifications = allow)
        }
    }

    suspend fun updateNotificationTime(time: String) {
        settingsDataStore.updateData {
            it.copy(notificationTime = time)
        }
    }

    @Composable
    fun getAppSettings(): AppSettings {
        return settingsDataStore.data.collectAsState(initial = AppSettings()).value
    }


}