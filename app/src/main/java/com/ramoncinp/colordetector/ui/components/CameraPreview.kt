package com.ramoncinp.colordetector.ui.components

import android.util.Log
import android.view.ScaleGestureDetector
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.ramoncinp.colordetector.ui.ColorImageAnalyzer
import com.ramoncinp.colordetector.ui.TapHandler
import kotlin.math.max
import kotlin.math.min

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    onTap: (Float, Float) -> Unit,
    onColorDetected: (Int) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val analyzer = ColorImageAnalyzer(previewView, onColorDetected)
    var cameraControl by remember { mutableStateOf<CameraControl?>(null) }
    var zoomRatio by remember { mutableFloatStateOf(1f) }
    var maxZoomRatio by remember { mutableFloatStateOf(1f) }
    val tapHandler = remember { TapHandler { x, y -> onTap(x, y) } }

    val scaleGestureDetector =
        ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val newZoomRatio = zoomRatio * detector.scaleFactor
                val clampedZoom = max(1f, min(newZoomRatio, maxZoomRatio))
                cameraControl?.setZoomRatio(clampedZoom)
                zoomRatio = clampedZoom
                return true
            }
        })

    LaunchedEffect(cameraProviderFuture) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

        val imageAnalyzer = ImageAnalysis.Builder()
            .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalyzer.setAnalyzer(
            ContextCompat.getMainExecutor(context),
            analyzer
        )
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        cameraProvider.unbindAll()

        try {
            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalyzer
            )

            cameraControl = camera.cameraControl

            camera.cameraInfo.zoomState.observe(lifecycleOwner) {
                maxZoomRatio = it.maxZoomRatio
            }
        } catch (e: Exception) {
            Log.e("CameraPreview", e.toString())
        }
    }

    AndroidView(
        factory = {
            previewView.also {
                it.setOnTouchListener { v, event ->
                    tapHandler.onMotionEvent(event)
                    scaleGestureDetector.onTouchEvent(event)
                    v.performClick()
                    true
                }
            }
        },
        modifier = modifier
    )
}
