package com.agrebennicov.jetpackdemo.features.welcome

import androidx.lifecycle.viewModelScope
import com.agrebennicov.jetpackdemo.base.BaseViewModel
import com.agrebennicov.jetpackdemo.features.welcome.repo.WelcomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val welcomeRepository: WelcomeRepository
) : BaseViewModel<WelcomeState>(WelcomeState()) {
    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(2_000)
            }
            _state.value = _state.value.copy(username = welcomeRepository.getUserName())
        }
    }
}
