package com.aerosense.os.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.accessibility.AccessibilityEvent

class AeroSenseService : AccessibilityService() {

    fun performAirSwipeUp() {
        val path = Path()
        val metrics = resources.displayMetrics
        val midX = metrics.widthPixels / 2f

        path.moveTo(midX, metrics.heightPixels * 0.8f)
        path.lineTo(midX, metrics.heightPixels * 0.2f)

        val stroke = GestureDescription.StrokeDescription(path, 0, 300)
        dispatchGesture(
            GestureDescription.Builder().addStroke(stroke).build(),
            object : GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                    triggerHapticFeedback()
                }
            },
            null
        )
    }

    fun performVirtualClick(x: Float, y: Float) {
        val path = Path().apply { moveTo(x, y) }
        val stroke = GestureDescription.StrokeDescription(path, 0, 50)
        dispatchGesture(GestureDescription.Builder().addStroke(stroke).build(), null, null)
        triggerHapticFeedback()
    }

    fun triggerHapticFeedback() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(50)
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) = Unit

    override fun onInterrupt() = Unit
}
