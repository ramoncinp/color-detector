package com.ramoncinp.colordetector.ui.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val SIZE = 150.dp

@Composable
fun ColoredSquare(hexColor: String, modifier: Modifier = Modifier) {
    Log.d("HexColor", "Color: $hexColor")
    val color = try {
        Color(android.graphics.Color.parseColor(hexColor))
    } catch (e: IllegalArgumentException) {
        Color.Black
    }

    Canvas(modifier = modifier.size(SIZE)) {
        drawRect(
            color = color,
            size = Size(size.width, size.height)
        )
    }
}
