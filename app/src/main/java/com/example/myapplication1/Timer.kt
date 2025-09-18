package com.example.myapplication1

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Timer {

    private var totalSeconds: Long = 0
    private val _timeState = MutableStateFlow("00:00:00")
    val timeState: StateFlow<String> = _timeState

    private val scope = CoroutineScope(Dispatchers.Default)

    private var isRunningInternal = false

    fun start() {
        if (isRunningInternal) return
        isRunningInternal = true
        scope.launch {
            while (isRunningInternal) {
                delay(1000)
                if (!isRunningInternal) break
                totalSeconds++
                val hours = totalSeconds / 3600
                val minutes = (totalSeconds % 3600) / 60
                val seconds = totalSeconds % 60
                _timeState.value = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }
        }
    }

    fun pause() {
        isRunningInternal = false
    }

    fun finish() {
        pause()
        totalSeconds = 0
        _timeState.value = "00:00:00"
    }
}
