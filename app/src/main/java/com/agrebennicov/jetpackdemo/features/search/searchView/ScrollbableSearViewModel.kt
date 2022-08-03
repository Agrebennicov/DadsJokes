package com.agrebennicov.jetpackdemo.features.search.searchView

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScrollableSearchViewModel @Inject constructor() : ViewModel() {
    private var lastScrollIndex = 0

    private val _scrollUp = mutableStateOf(false)
    val scrollUpState: State<Boolean>
        get() = _scrollUp

    fun updateScrollPosition(newScrollIndex: Int) {
        if (newScrollIndex == lastScrollIndex) return

        _scrollUp.value = newScrollIndex > lastScrollIndex
        lastScrollIndex = newScrollIndex
    }
}
