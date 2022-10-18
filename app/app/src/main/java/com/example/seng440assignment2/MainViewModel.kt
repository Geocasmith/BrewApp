package com.example.seng440assignment2

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.seng440assignment2.datastore.AppSettings
import com.example.seng440assignment2.datastore.UserData
import org.json.JSONArray
import org.json.JSONObject


class MainViewModelFactory(private val settingsDataStore: DataStore<AppSettings>, private val userDataStore: DataStore<UserData>, private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(settingsDataStore, userDataStore, context) as T
    }
}

class MainViewModel(private var settingsDataStore: DataStore<AppSettings>, private var userDataStore: DataStore<UserData>, context: Context) : ViewModel() {

    private val queue = Volley.newRequestQueue(context)
    private lateinit var userData: UserData

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

    suspend fun setAllowShaking(shaking: Boolean) {
        settingsDataStore.updateData {
            it.copy(allowShaking = shaking)
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

    @Composable
    fun setUserData() {
        userData = userDataStore.data.collectAsState(initial = UserData()).value
    }

    fun getUserId(): Long {
        return userData.id
    }

    suspend fun clearUserData() {
        userDataStore.updateData { UserData() }
    }

    fun addRequestToQueue(request: JsonObjectRequest) {
        queue.add(request)
    }

    fun addRequestToQueue(request: JsonArrayRequest) {
        queue.add(request)
    }

    fun getObjectRequest(context: Context, endpoint: String, responseHandler: Response.Listener<JSONObject>, errorHandler: Response.ErrorListener? = null): JsonObjectRequest {
        return jsonObjectRequest(context, endpoint, Request.Method.GET, responseHandler, errorHandler)
    }

    fun patchObjectRequest(context: Context, endpoint: String, body: JSONObject?, responseHandler: Response.Listener<JSONObject>, errorHandler: Response.ErrorListener? = null): JsonObjectRequest {
        return jsonObjectRequest(context, endpoint, Request.Method.PATCH, responseHandler, errorHandler, body)
    }

    fun getArrayRequest(context: Context, endpoint: String, responseHandler: Response.Listener<JSONArray>, errorHandler: Response.ErrorListener? = null): JsonArrayRequest {
        return jsonArrayRequest(context, endpoint, Request.Method.GET, responseHandler, errorHandler)
    }

    fun postObjectRequest(context: Context, endpoint: String, body: JSONObject?, responseHandler: Response.Listener<JSONObject>, errorHandler: Response.ErrorListener? = null): JsonObjectRequest {
        return jsonObjectRequest(context, endpoint, Request.Method.POST, responseHandler, errorHandler, body)
    }

    private fun jsonObjectRequest(context: Context, endpoint: String, requestType: Int, responseHandler: Response.Listener<JSONObject>, errorHandler: Response.ErrorListener? = null, body: JSONObject? = null): JsonObjectRequest {
        var url = "https://seng440-api-6ieosyuwfq-ts.a.run.app/api"
        if (endpoint.startsWith("https")) {
            url = endpoint
        } else if (endpoint.startsWith("/")) {
            url += endpoint
        } else {
            url += "/$endpoint"
        }
        var eHandler = errorHandler
        if (errorHandler == null) {
            eHandler = Response.ErrorListener { error ->
                var message: CharSequence = context.resources.getText(R.string.error_message)
                when (error.networkResponse?.statusCode ?: 0) {
                    400 -> message = context.resources.getText(R.string.generic_400_error)
                    401 -> message = context.resources.getText(R.string.generic_401_error)
                    404 -> message = context.resources.getText(R.string.generic_404_error)
                    500 -> message = context.resources.getText(R.string.generic_500_error)
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

        val request : JsonObjectRequest = object : JsonObjectRequest(requestType, url, body, responseHandler, eHandler) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["X-Authorization"] = userData.authToken
                return params
            }
        }
        return request
    }

    private fun jsonArrayRequest(context: Context, endpoint: String, requestType: Int, responseHandler: Response.Listener<JSONArray>, errorHandler: Response.ErrorListener? = null) : JsonArrayRequest {
        var url = "https://seng440-api-6ieosyuwfq-ts.a.run.app/api"
        if (endpoint.startsWith("https")) {
            url = endpoint
        } else if (endpoint.startsWith("/")) {
            url += endpoint
        } else {
            url += "/$endpoint"
        }
        var eHandler = errorHandler
        if (errorHandler == null) {
            eHandler = Response.ErrorListener { error ->
                Log.e("ERR", error.message ?: "")
                var message: CharSequence = context.resources.getText(R.string.error_message)
                when (error.networkResponse?.statusCode ?: 0) {
                    400 -> message = context.resources.getText(R.string.generic_400_error)
                    401 -> message = context.resources.getText(R.string.generic_401_error)
                    404 -> message = context.resources.getText(R.string.generic_404_error)
                    500 -> message = context.resources.getText(R.string.generic_500_error)
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

        val request : JsonArrayRequest = object : JsonArrayRequest(requestType, url, null, responseHandler, eHandler) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["X-Authorization"] = userData.authToken
                return params
            }
        }
        return request
    }
}