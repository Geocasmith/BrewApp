package com.example.seng440assignment2

import android.app.Application
import android.content.Context
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.seng440assignment2.datastore.AppSettingsSerializer


class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}

class MainViewModel() : ViewModel() {

    val Context.dataStore by dataStore(
        fileName = "app-settings.json",
        serializer = AppSettingsSerializer
    )




}