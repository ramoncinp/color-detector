package com.ramoncinp.colordetector.ui

import android.graphics.PixelFormat
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.view.PreviewView
import com.ramoncinp.colordetector.domain.ColorData
import java.nio.ByteBuffer

class ColorImageAnalyzer(
    private val previewView: PreviewView,
    private val onColorDetected: (ColorData) -> Unit
) : ImageAnalysis.Analyzer {

    private var touchX = 0f
    private var touchY = 0f

    fun onCoordinatesChange(x: Float, y: Float) {
        touchX = x
        touchY = y
    }

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        if (touchX != 0.0f && touchY != 0.0f) {
            val imageWidth = image.width
            val imageHeight = image.height

            val previewWidth = previewView.width
            val previewHeight = previewView.height

            val scaledX = (touchX / previewWidth) * imageWidth
            val scaledY = (touchY / previewHeight) * imageHeight

            if (image.format == PixelFormat.RGBA_8888) {
                if (scaledX >= 0 && scaledX < imageWidth && scaledY >= 0 && scaledY < imageHeight) {
                    val color = extractPixelColor(image, scaledX.toInt(), scaledY.toInt())
                    onColorDetected(color)
                }
            } else {
                Log.d("Analyzer", "Not the format: ${image.format}")
            }

            touchX = 0.0f
            touchY = 0.0f
        }

        image.close()
    }

    private fun extractPixelColor(image: ImageProxy, x: Int, y: Int): ColorData {
        val buffer: ByteBuffer = image.planes[0].buffer
        val pixelStride = image.planes[0].pixelStride
        val rowStride = image.planes[0].rowStride
        val pixelIndex = y * rowStride + x * pixelStride
        val r = buffer.get(pixelIndex).toInt() and 0xFF
        val g = buffer.get(pixelIndex + 1).toInt() and 0xFF
        val b = buffer.get(pixelIndex + 2).toInt() and 0xFF
        val a = buffer.get(pixelIndex + 3).toInt() and 0xFF
        return ColorData(r, g, b, a)
    }
}
