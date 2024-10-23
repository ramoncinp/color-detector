package com.ramoncinp.colordetector.ui

import android.view.MotionEvent
import kotlin.math.abs

private const val TAP_THRESHOLD = 15

class TapHandler(
    val onValidTap: (Float, Float) -> Unit
) {

    private var initialX = 0f
    private var initialY = 0f

    fun onMotionEvent(motionEvent: MotionEvent) {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = motionEvent.x
                initialY = motionEvent.y
            }

            MotionEvent.ACTION_UP -> {
                val finalX = motionEvent.x
                val finalY = motionEvent.y

                if (abs(finalX - initialX) < TAP_THRESHOLD && abs(finalY - initialY) < TAP_THRESHOLD) {
                    onValidTap(finalX, finalY)
                }
            }
        }
    }
}