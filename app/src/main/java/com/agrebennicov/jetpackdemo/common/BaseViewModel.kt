package com.agrebennicov.jetpackdemo.common

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<A, S>(initialState: S) : ViewModel() {
    private val mutableState = mutableStateOf(initialState)
    internal val state: State<S> = mutableState

    abstract fun onAction(action: A)
    protected abstract fun reduce(action: A, oldState: S): S

    protected fun updateState(action: A) {
        mutableState.value = reduce(action, state.value)
    }
}