package com.agrebennicov.jetpackdemo.features.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(MainState())
    internal val state: State<MainState> = _state

    internal fun onItemSelected(selectedRoute: String) {
        val updatedItems = _state.value.items.map {
            it.copy(isSelected = it.route == selectedRoute)
        }
        _state.value = _state.value.copy(items = updatedItems)
    }
}