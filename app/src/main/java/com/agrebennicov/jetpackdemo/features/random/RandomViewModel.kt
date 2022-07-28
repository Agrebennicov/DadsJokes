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

    init {
        onAction(RandomAction.LoadFirstJoke)
    }

    override fun onAction(action: RandomAction) {
        when (action) {
            RandomAction.LoadFirstJoke -> {
                mutableState.value = reduce(action, mutableState.value)
                fetchJoke()
            }
            is RandomAction.LoadNextJoke -> {
                mutableState.value = reduce(action, mutableState.value)
                fetchJoke()
            }
            is RandomAction.SaveJoke -> {
                mutableState.value = reduce(action, mutableState.value)
                saveJoke(action.joke)
            }
            is RandomAction.DeleteJoke -> {
                mutableState.value = reduce(action, mutableState.value)
                deleteJoke(action.joke)
            }
            is RandomAction.JokeLoaded,
            RandomAction.ShowError -> mutableState.value = reduce(action, mutableState.value)
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
            is RandomAction.SaveJoke -> oldState.copy(joke = action.joke.copy(isSaved = true))
            is RandomAction.DeleteJoke -> oldState.copy(joke = action.joke.copy(isSaved = false))
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
                    it.isSuccess && jokeResponse != null -> {
                        onAction(RandomAction.JokeLoaded(Joke(jokeResponse)))
                    }
                    else -> onAction(RandomAction.ShowError)
                }
            }
        }
    }

    private fun saveJoke(joke: Joke) {
        viewModelScope.launch {
            randomRepository.addJoke(joke).collect {}
        }
    }

    private fun deleteJoke(joke: Joke) {
        viewModelScope.launch {
            randomRepository.deleteJoke(joke).collect {}
        }
    }
}
