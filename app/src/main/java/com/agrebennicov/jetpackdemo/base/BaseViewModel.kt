package com.agrebennicov.jetpackdemo.base

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

open class BaseViewModel<T>(initialState: T) : ViewModel() {
    internal val _state = mutableStateOf(initialState)
    val state: State<T>
        get() = _state
}
