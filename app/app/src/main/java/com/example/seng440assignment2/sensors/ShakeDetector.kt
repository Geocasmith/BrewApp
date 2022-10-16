package com.example.seng440assignment2.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class ShakeDetector: SensorEventListener  {

    companion object {
        val SHAKE_THRESHHOLD_GRAVITY = 2.7F
        val SHAKE_SLOP_TIME_MS = 500
        val SHAKE_COUNT_RESET_TIME_MS = 3000
    }

    private lateinit var mListener: OnShakeListener
    private var mShakeTimestamp: Long = 0
    private var mShakeCount = 0

    fun setOnShakeListener(listener: OnShakeListener) {
        mListener = listener
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (::mListener.isInitialized) {
            val gX = event.values[0].div(SensorManager.GRAVITY_EARTH)
            val gY = event.values[1].div(SensorManager.GRAVITY_EARTH)
            val gZ = event.values[2].div(SensorManager.GRAVITY_EARTH)

            val gForce = sqrt(gX * gX + gY * gY + gZ * gZ)

            if (gForce > SHAKE_THRESHHOLD_GRAVITY) {
                val now = System.currentTimeMillis()
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) { return }

                if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    mShakeCount = 0;
                }

                mShakeTimestamp = now
                mShakeCount++

                mListener.onShake(mShakeCount)
            }



        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { null }
}