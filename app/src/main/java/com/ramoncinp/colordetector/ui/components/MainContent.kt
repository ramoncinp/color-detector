package com.ramoncinp.colordetector.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.ramoncinp.colordetector.domain.ColorData
import com.ramoncinp.colordetector.ui.model.ColorState

@Composable
fun MainContent(
    state: ColorState,
    hasRequiredPermissions: Boolean,
    onColorDetected: (ColorData) -> Unit
) {
    var tapPosition by remember { mutableStateOf<Offset?>(null) }

    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (hasRequiredPermissions) {
                    CameraPreview(
                        onColorDetected = { color ->
                            onColorDetected(color)
                        },
                        onTap = { x, y ->
                            tapPosition = Offset(x, y)
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = "Permiso no concedido"
                    )
                }
            }

            TapEffect(
                position = tapPosition,
                onAnimationEnd = { tapPosition = null }
            )

            if (state.colorHex != null) {
                ColoredSquare(
                    hexColor = state.colorHex,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(24.dp)
                )
            }

            if (state.colorName != null) {
                ColorNameContainer(
                    name = state.colorName,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(24.dp)
                )
            }
        }
    }
}
