package com.agrebennicov.jetpackdemo.common

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<A, S>(initialState: S) : ViewModel() {
    protected val mutableState = mutableStateOf(initialState)
    internal val state: State<S> = mutableState

    abstract fun onAction(action: A)
    protected abstract fun reduce(action: A, oldState: S): S
}