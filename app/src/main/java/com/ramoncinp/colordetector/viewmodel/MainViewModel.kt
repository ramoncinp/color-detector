package com.ramoncinp.colordetector.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ramoncinp.colordetector.data.ColorAnalyzer
import com.ramoncinp.colordetector.data.ColorAnalyzerImpl
import com.ramoncinp.colordetector.domain.ColorData
import com.ramoncinp.colordetector.domain.toHex
import com.ramoncinp.colordetector.ui.model.ColorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val colorAnalyzer: ColorAnalyzer = ColorAnalyzerImpl()
) : ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    private val _state = MutableStateFlow(ColorState())
    val state = _state.asStateFlow()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(0, permission)
        }
    }

    fun categorizeColor(colorData: ColorData) {
        val colorName = colorAnalyzer.categorize(colorData)
        _state.update {
            it.copy(
                colorHex = colorData.toHex(),
                colorName = colorName
            )
        }
    }
}
