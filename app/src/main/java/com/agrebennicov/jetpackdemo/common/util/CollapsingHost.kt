package com.agrebennicov.jetpackdemo.common.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

interface CollapsingHost {
    var lastScrollIndex: Int

    val _scrollUp: MutableState<Boolean>
    val scrollUpState: State<Boolean>
        get() = _scrollUp

    fun updateScrollPosition(newScrollIndex: Int)
}

class DefaultCollapsingHost : CollapsingHost {
    override var lastScrollIndex: Int = 0

    override val _scrollUp: MutableState<Boolean> = mutableStateOf(false)

    override fun updateScrollPosition(newScrollIndex: Int) {
        if (newScrollIndex == lastScrollIndex) return

        _scrollUp.value = newScrollIndex > lastScrollIndex
        lastScrollIndex = newScrollIndex
    }
}