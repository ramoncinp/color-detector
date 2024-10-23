package com.ramoncinp.colordetector.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun TapEffect(
    position: Offset?,
    onAnimationEnd: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animationRadius = remember { Animatable(0f) }

    if (position != null) {
        LaunchedEffect(position) {
            animationRadius.snapTo(100f)
            animationRadius.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
            )
            onAnimationEnd()
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        position?.let {
            drawCircle(
                color = Color.White,
                radius = animationRadius.value,
                center = position
            )
        }
    }
}
