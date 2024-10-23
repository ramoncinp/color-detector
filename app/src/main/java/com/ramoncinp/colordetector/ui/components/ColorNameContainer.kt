package com.ramoncinp.colordetector.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColorNameContainer(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(Color(0, 0, 0, 80))
    ) {
        Text(text = name, style = TextStyle(color = Color.White, fontSize = 20.sp), modifier = Modifier.padding(8.dp))
    }
}
