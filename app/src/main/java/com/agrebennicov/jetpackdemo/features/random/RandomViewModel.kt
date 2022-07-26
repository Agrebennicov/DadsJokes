package com.agrebennicov.jetpackdemo.features.random

import androidx.lifecycle.viewModelScope
import com.agrebennicov.jetpackdemo.common.BaseViewModel
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import com.agrebennicov.jetpackdemo.features.random.repository.RandomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val randomRepository: RandomRepository
) : BaseViewModel<RandomAction, RandomState>(RandomState(isLoadingFirstJoke = true)) {
    override fun onAction(action: RandomAction) {
        mutableState.value = when (action) {
            RandomAction.LoadFirstJoke -> {
                fetchJoke()
                reduce(action, mutableState.value)
            }
            is RandomAction.LoadNextJoke -> {
                fetchJoke()
                reduce(action, mutableState.value)
            }
            is RandomAction.JokeLoaded,
            RandomAction.ShowError -> reduce(action, mutableState.value)
            else -> throw IllegalStateException("TODO Soon")
        }
    }

    override fun reduce(action: RandomAction, oldState: RandomState): RandomState {
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
