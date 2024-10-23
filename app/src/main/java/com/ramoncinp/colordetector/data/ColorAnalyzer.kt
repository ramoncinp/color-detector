package com.ramoncinp.colordetector.data

import com.ramoncinp.colordetector.domain.ColorData

interface ColorAnalyzer {
    fun categorize(data: ColorData): String
}
