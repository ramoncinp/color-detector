package com.ramoncinp.colordetector.domain

fun ColorData.toHex(): String {
    return String.format("#%02X%02X%02X", r, g, b)
}

fun ColorData.toInt(): Int {
    return (r shl 16) or (g shl 8) or b
}

fun Int.toRgb(): Triple<Int, Int, Int> {
    val r = (this shr 16) and 0xFF
    val g = (this shr 8) and 0xFF
    val b = this and 0xFF
    return Triple(r, g, b)
}
