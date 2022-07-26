package com.agrebennicov.jetpackdemo.features.random

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import com.agrebennicov.jetpackdemo.features.random.repository.RandomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val randomRepository: RandomRepository
) : ViewModel() {
    private val _state = mutableStateOf(RandomState(isLoadingFirstJoke = true))
    internal val state: State<RandomState> = _state

    fun onAction(action: RandomAction) {
        _state.value = when (action) {
            RandomAction.LoadFirstJoke -> {
                fetchJoke()
                reduce(action, _state.value)
            }
            is RandomAction.LoadNextJoke -> {
                fetchJoke()
                reduce(action, _state.value)
            }
            is RandomAction.JokeLoaded,
            RandomAction.ShowError -> reduce(action, _state.value)
            else -> throw IllegalStateException("TODO Soon")
        }
    }

    private fun reduce(action: RandomAction, oldState: RandomState): RandomState {
        return when (action) {
            is RandomAction.JokeLoaded -> oldState.copy(
                joke = action.joke,
                isLoadingFirstJoke = false,
                isLoadingNextJoke = false,
                showError = false
            )
            RandomAction.LoadFirstJoke -> oldState.copy(
                isLoadingFirstJoke = true
            )
            RandomAction.LoadNextJoke -> oldState.copy(isLoadingNextJoke = true)
            RandomAction.ShowError -> oldState.copy(
                showError = true,
                joke = null,
                isLoadingFirstJoke = false,
                isLoadingNextJoke = false,
            )
            else -> throw IllegalStateException("TODO Soon")
        }
    }

    private fun fetchJoke() {
        viewModelScope.launch {
            randomRepository.fetchRandomJoke().collect {
                val jokeResponse = it.getOrNull()
                when {
                    it.isSuccess && jokeResponse != null -> onAction(
                        RandomAction.JokeLoaded(Joke(jokeResponse))
                    )
                    else -> onAction(RandomAction.ShowError)
                }
            }
        }
    }
}
