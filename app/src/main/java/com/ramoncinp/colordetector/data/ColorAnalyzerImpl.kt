package com.ramoncinp.colordetector.data

import com.ramoncinp.colordetector.domain.ColorData
import com.ramoncinp.colordetector.domain.toRgb
import kotlin.math.sqrt

class ColorAnalyzerImpl : ColorAnalyzer {

    override fun categorize(data: ColorData): String {
        var closestColorName = "Desconocido"
        var smallestDistance = Double.MAX_VALUE

        materialTable.forEach { (colorInt, name) ->
            val (rTable, gTable, bTable) = colorInt.toRgb()
            val distance = colorDistance(
                data.r, data.g, data.b,
                rTable, gTable, bTable
            )

            if (distance < smallestDistance) {
                smallestDistance = distance
                closestColorName = name
            }
        }

        return closestColorName
    }

    private fun colorDistance(r1: Int, g1: Int, b1: Int, r2: Int, g2: Int, b2: Int): Double {
        return sqrt(((r1 - r2) * (r1 - r2) + (g1 - g2) * (g1 - g2) + (b1 - b2) * (b1 - b2)).toDouble())
    }

    companion object {
        private val colorTable = mapOf(
            0xFF0000 to "Rojo",      // Red
            0x00FF00 to "Verde",     // Green
            0x0000FF to "Azul",      // Blue
            0xFFFF00 to "Amarillo",  // Yellow
            0xFF00FF to "Magenta",   // Magenta
            0x00FFFF to "Cian",      // Cyan
            0x800080 to "Púrpura",   // Purple
            0xFFFFFF to "Blanco",    // White
            0x000000 to "Negro"      // Black
        )

        private val materialTable = mapOf(
            0xFF0000 to "Rojo",
            0xFFEBEE to "Rojo",
            0xFFCDD2 to "Rojo",
            0xEF9A9A to "Rojo",
            0xE57373 to "Rojo",
            0xEF5350 to "Rojo",
            0xF44336 to "Rojo",
            0xE53935 to "Rojo",
            0xD32F2F to "Rojo",
            0xC62828 to "Rojo",
            0xB71C1C to "Rojo",
            0xFF8A80 to "Rojo",
            0xFF5252 to "Rojo",
            0xFF1744 to "Rojo",
            0xD50000 to "Rojo",
            0xFCE4EC to "Rosa",
            0xF8BBD0 to "Rosa",
            0xF48FB1 to "Rosa",
            0xF06292 to "Rosa",
            0xEC407A to "Rosa",
            0xE91E63 to "Rosa",
            0xD81B60 to "Rosa",
            0xC2185B to "Rosa",
            0xAD1457 to "Rosa",
            0x880E4F to "Rosa",
            0xFF4081 to "Rosa",
            0xF50057 to "Rosa",
            0xC51162 to "Rosa",
            0xF3E5F5 to "Morado",
            0xE1BEE7 to "Morado",
            0xCE93D8 to "Morado",
            0xBA68C8 to "Morado",
            0xAB47BC to "Morado",
            0x9C27B0 to "Morado",
            0x8E24AA to "Morado",
            0x7B1FA2 to "Morado",
            0x6A1B9A to "Morado",
            0x4A148C to "Morado",
            0xEA80FC to "Morado",
            0xE040FB to "Morado",
            0xD500F9 to "Morado",
            0xAA00FF to "Morado",
            0xEDE7F6 to "Morado",
            0xD1C4E9 to "Morado",
            0xB39DDB to "Morado",
            0xB39DDB to "Morado",
            0x9575CD to "Morado",
            0x7E57C2 to "Morado",
            0x673AB7 to "Morado",
            0x5E35B1 to "Morado",
            0x512DA8 to "Morado",
            0x4527A0 to "Morado",
            0x311B92 to "Morado",
            0xB388FF to "Morado",
            0x7C4DFF to "Morado",
            0x651FFF to "Morado",
            0x6200EA to "Morado",
            0xE3F2FD to "Azul",
            0xBBDEFB to "Azul",
            0x90CAF9 to "Azul",
            0x64B5F6 to "Azul",
            0x42A5F5 to "Azul",
            0x2196F3 to "Azul",
            0x1E88E5 to "Azul",
            0x1976D2 to "Azul",
            0x1565C0 to "Azul",
            0x0D47A1 to "Azul",
            0x82B1FF to "Azul",
            0x448AFF to "Azul",
            0x2979FF to "Azul",
            0xE1F5FE to "Azul",
            0x81D4FA to "Azul",
            0x29B6F6 to "Azul",
            0x039BE5 to "Azul",
            0x0277BD to "Azul",
            0x01579B to "Azul",
            0x80D8FF to "Azul",
            0x40C4FF to "Azul",
            0x00B0FF to "Azul",
            0x0091EA to "Azul",
            0xE8F5E9 to "Verde",
            0xC8E6C9 to "Verde",
            0xA5D6A7 to "Verde",
            0x81C784 to "Verde",
            0x66BB6A to "Verde",
            0x4CAF50 to "Verde",
            0x43A047 to "Verde",
            0x388E3C to "Verde",
            0x1B5E20 to "Verde",
            0xF1F8E9 to "Verde",
            0xC5E1A5 to "Verde",
            0x9CCC65 to "Verde",
            0x7CB342 to "Verde",
            0x558B2F to "Verde",
            0x33691E to "Verde",
            0xB2FF59 to "Verde",
            0x64DD17 to "Verde",
            0xFFFDE7 to "Amarillo",
            0xFFF59D to "Amarillo",
            0xFFEE58 to "Amarillo",
            0xFDD835 to "Amarillo",
            0xF9A825 to "Amarillo",
            0xFFFF8D to "Amarillo",
            0xFFEA00 to "Amarillo",
            0xFFF3E0 to "Naranja",
            0xFFCC80 to "Naranja",
            0xFFA726 to "Naranja",
            0xFB8C00 to "Naranja",
            0xEF6C00 to "Naranja",
            0xFFD180 to "Naranja",
            0xFF9100 to "Naranja",
            0xFF6D00 to "Naranja",
            0x8D6E63 to "Café",
            0x795548 to "Café",
            0x6D4C41 to "Café",
            0x4E342E to "Café",
            0x3E2723 to "Café",
            0xF5F5F5 to "Blanco",
            0xFAFAFA to "Blanco",
            0xEEEEEE to "Blanco",
            0xE0E0E0 to "Gris",
            0xBDBDBD to "Gris",
            0x9E9E9E to "Gris",
            0x757575 to "Gris",
            0x616161 to "Gris",
            0x424242 to "Gris",
            0x212121 to "Negro",
            0x121212 to "Negro",
            0x000000 to "Negro",
            0xFFFFFF to "Blanco",
            0x00FF00 to "Verde",
            0x0000FF to "Azul",
            0xFFFF00 to "Amarillo",
            0xFF00FF to "Magenta",
            0x00FFFF to "Cian",
            0x800080 to "Morado",
        )
    }
}
