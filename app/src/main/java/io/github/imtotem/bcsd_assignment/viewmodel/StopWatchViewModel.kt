package io.github.imtotem.bcsd_assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.imtotem.bcsd_assignment.enums.State
import io.github.imtotem.bcsd_assignment.ext.format
import kotlinx.coroutines.*

class StopWatchViewModel : ViewModel() {
    private var job: Job? = null

    private var startTime: Long = 0L

    private var elapsedTime: Long = 0L
    val time: Long get() = elapsedTime

    private val _elapse = MutableLiveData("00:00.00")
    val elapse: MutableLiveData<String> get() = _elapse

    private var _state: State = State.RESET
    val state: State get() = _state

    private var _interval: Long = 0L
    val interval: Long
        get() {
            val temp = elapsedTime - _interval
            _interval = elapsedTime
            return temp
        }

    fun start() {
        _state = State.RUN

        job = viewModelScope.launch(Dispatchers.Default) {
            startTime = System.currentTimeMillis()
            while (isActive) {
                val currentTime = System.currentTimeMillis()
                elapsedTime += currentTime - startTime
                startTime = currentTime

                withContext(Dispatchers.Main) {
                    _elapse.value = elapsedTime.format()
                }

                delay(10L)
            }
        }
    }

    fun stop() {
        job?.cancel()
        _state = State.STOP
    }

    fun reset() {
        _interval = 0L
        elapsedTime = 0L
        _elapse.value = "00:00.00"
        job?.cancel()
        _state = State.RESET
    }
}