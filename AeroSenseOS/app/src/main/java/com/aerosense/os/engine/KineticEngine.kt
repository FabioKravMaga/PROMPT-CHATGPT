package com.aerosense.os.engine

import android.accessibilityservice.AccessibilityService
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.aerosense.os.service.AeroSenseService
import kotlin.math.abs

class KineticEngine(private val accessibilityService: AeroSenseService) : SensorEventListener {

    private val flickThreshold = 15.0f
    private var lastFlickTime: Long = 0

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            val yAccel = event.values[1]
            val currentTime = System.currentTimeMillis()

            if (abs(yAccel) > flickThreshold && (currentTime - lastFlickTime > 1000)) {
                lastFlickTime = currentTime

                if (yAccel > 0) {
                    accessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS)
                } else {
                    accessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
                }

                accessibilityService.triggerHapticFeedback()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}
