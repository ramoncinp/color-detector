package com.ramoncinp.colordetector.domain

fun ColorData.toHex(): String {
    return String.format("#%02X%02X%02X", r, g, b)
}
