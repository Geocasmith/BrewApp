package com.example.seng440assignment2

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.seng440assignment2.datastore.AppSettings
import com.example.seng440assignment2.datastore.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject


class AuthViewModelFactory(private val userDataStore: DataStore<UserData>, private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(userDataStore, context) as T
    }
}

class AuthViewModel(private var userDataStore: DataStore<UserData>, context: Context): ViewModel() {

    var userName by mutableStateOf("")
    var userPassword by mutableStateOf("")
    private var requestQueue = Volley.newRequestQueue(context)

    fun login(context: Context, scope: CoroutineScope, onSuccess: () -> Unit) {
        val url = "https://seng440-api-6ieosyuwfq-ts.a.run.app/api/users/login"

        val jsonRequest = JSONObject()
        jsonRequest.put("username", userName)
        jsonRequest.put("password", userPassword)

        val loginRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonRequest,
            { responseJSON ->
                scope.launch {
                    userDataStore.updateData {
                        it.copy(
                            id = responseJSON["userId"].toString().toLong(),
                            authToken = responseJSON["token"].toString()
                        )
                    }
                    onSuccess()
                }
            },
            { error ->
                when (error.networkResponse.statusCode) {
                    400 -> Toast.makeText(context, context.resources.getText(R.string.login_400_error), Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(context, context.resources.getText(R.string.generic_500_error), Toast.LENGTH_SHORT).show()
                }
            })
        requestQueue.add(loginRequest)
    }

    fun register() {
        /* TODO */
    }

    @Composable
    fun getUserData(): UserData {
        return userDataStore.data.collectAsState(initial = UserData()).value
    }

}