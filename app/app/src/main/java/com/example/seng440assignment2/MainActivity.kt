package com.example.seng440assignment2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.seng440assignment2.datastore.AppSettingsSerializer
import com.example.seng440assignment2.navigation.AnimatedNav
import com.example.seng440assignment2.navigation.AnimatedNavBar
import com.example.seng440assignment2.sensors.OnShakeListener
import com.example.seng440assignment2.sensors.ShakeDetector
import com.example.seng440assignment2.ui.theme.SENG440Assignment2Theme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import java.nio.charset.Charset


val Context.settingsDataStore by dataStore(
    fileName = "app_settings.json",
    serializer = AppSettingsSerializer
)

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private lateinit var mShakeDetector: ShakeDetector


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSensorManager = applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeDetector()

        setContent {
            val context = LocalContext.current

            // View Model
            val owner = LocalViewModelStoreOwner.current
            val viewModel: MainViewModel = owner?.let {
                viewModel(
                    it,
                    "MainViewModel",
                    MainViewModelFactory(settingsDataStore, context)
                )
            }!!

            // Shake Detection
            mShakeDetector.setOnShakeListener(object : OnShakeListener {
                override fun onShake(count: Int) {
                    val randomBeerRequest: JsonObjectRequest = viewModel.getRequest(context, "beer/random", { response ->
                        // TODO: Route to beer page and display the returned beer
                        val toastText = "Responded with beer '${response["name"]}' (${response["barcode"]})"
                        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
                    })
                    viewModel.addRequestToQueue(randomBeerRequest);
                }
            })

            SENG440Assignment2Theme(darkTheme = viewModel.getAppSettings().isDarkMode) {

                val navController = rememberAnimatedNavController()

                Scaffold(
                    bottomBar = { AnimatedNavBar(navController = navController) }
                ) {
                        paddingValues ->
                    AnimatedNav(navController = navController, mainViewModel = viewModel, padding = paddingValues)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        mSensorManager.unregisterListener(mShakeDetector)
        super.onPause()
    }




}
