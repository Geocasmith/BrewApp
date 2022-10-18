package com.example.seng440assignment2

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.volley.toolbox.JsonObjectRequest
import com.example.seng440assignment2.datastore.AppSettingsSerializer
import com.example.seng440assignment2.navigation.AnimatedNav
import com.example.seng440assignment2.navigation.AnimatedNavBar
import com.example.seng440assignment2.sensors.OnShakeListener
import com.example.seng440assignment2.sensors.ShakeDetector
import com.example.seng440assignment2.ui.theme.SENG440Assignment2Theme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


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

        val authIntent = Intent(applicationContext, AuthActivity::class.java)
        val onLogout = {
            startActivity(authIntent)
            finish()
        }


        setContent {
            val context = LocalContext.current

            // View Model
            val owner = LocalViewModelStoreOwner.current
            val viewModel: MainViewModel = owner?.let {
                viewModel(
                    it,
                    "MainViewModel",
                    MainViewModelFactory(settingsDataStore, userDataStore, context)
                )
            }!!
            viewModel.setUserData()

            val navController = rememberAnimatedNavController()

            val allowShaking = viewModel.getAppSettings().allowShaking

            // Shake Detection
            mShakeDetector.setOnShakeListener(object : OnShakeListener {
                override fun onShake() {
                    if (allowShaking) {
                        val randomBeerRequest: JsonObjectRequest = viewModel.getObjectRequest(context, "beer/random", { response ->
                            navController.navigate("beer/${response["barcode"]}")
                        })
                        viewModel.addRequestToQueue(randomBeerRequest)
                    }
                }
            })

            SENG440Assignment2Theme(darkTheme = viewModel.getAppSettings().isDarkMode) {
                Scaffold(
                    bottomBar = { AnimatedNavBar(navController = navController) }
                ) {
                        paddingValues ->
                    AnimatedNav(navController = navController, mainViewModel = viewModel, padding = paddingValues, onLogout = onLogout)
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
