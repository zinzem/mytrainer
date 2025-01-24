package com.mytrainer.kmp.ui.util

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>(defaultState: T) : ViewModel() {

    private var started: Boolean = false

    private val _state: MutableStateFlow<T> = MutableStateFlow(defaultState)
    val stateValue: T
        get() = _state.value
    val state: StateFlow<T>
        get() = _state

    private val _events = MutableSharedFlow<Event>()
    val events: SharedFlow<Event>
        get() = _events

    fun start(args: List<Any?> = emptyList()) {
        //if (!started.getAndSet(true)) {
            onStart(args.toList())
        //}
    }

    fun forceStart() = start()

    abstract fun onStart(args: List<Any?>)

    protected fun setState(
        buildNewState: T.() -> T
    ) = buildNewState(_state.value).also { newState ->
        _state.update { newState }
    }

    @VisibleForTesting
    fun setStateForTesting(
        buildNewState: T.() -> T
    ) = buildNewState(_state.value).also { newState ->
        _state.value =  newState
    }

    protected fun sendEvent(event: Event) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }
}

interface Event