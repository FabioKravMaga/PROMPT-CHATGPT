package com.aerosense.os.engine

import com.aerosense.os.service.AeroSenseService
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import kotlin.math.hypot

class VisionEngine(private val accessibilityService: AeroSenseService) {

    private val pinchThreshold = 0.05f
    private var isPinching = false
    private var lastActionTime = 0L

    fun processHandLandmarks(result: HandLandmarkerResult) {
        if (result.landmarks().isEmpty()) return

        val hand = result.landmarks()[0]
        val thumbTip = hand[4]
        val indexTip = hand[8]

        val distance = hypot(
            (thumbTip.x() - indexTip.x()).toDouble(),
            (thumbTip.y() - indexTip.y()).toDouble()
        ).toFloat()

        val currentTime = System.currentTimeMillis()

        if (distance < pinchThreshold && !isPinching && (currentTime - lastActionTime > 1000)) {
            isPinching = true
            lastActionTime = currentTime

            val metrics = accessibilityService.resources.displayMetrics
            val mappedX = indexTip.x() * metrics.widthPixels
            val mappedY = indexTip.y() * metrics.heightPixels

            accessibilityService.performVirtualClick(mappedX, mappedY)
        } else if (distance > pinchThreshold + 0.02f) {
            isPinching = false
        }
    }
}
