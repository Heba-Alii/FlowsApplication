package com.example.flowsapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _liveData = MutableLiveData<String>()
    public val liveData: MutableLiveData<String> = _liveData

    private val _stateFlow = MutableStateFlow("Hello")
    public val stateFlow: MutableStateFlow<String> = _stateFlow

    private val _sharedFlow = MutableSharedFlow<String>()
    public val sharedFlow: MutableSharedFlow<String> = _sharedFlow

    fun triggerLiveData() {
        liveData.value = "Live Data"
    }

    fun triggerStateFlow() {
        stateFlow.value = "State Flow"
    }

    fun triggerSharedFlow() {
        viewModelScope.launch {
            sharedFlow.emit("SharedFlow")
        }
    }

    fun triggerFlow(): Flow<Int> {
        return flow {
            repeat(5) {
                emit(it)
                delay(1000)
            }
        }
    }
}