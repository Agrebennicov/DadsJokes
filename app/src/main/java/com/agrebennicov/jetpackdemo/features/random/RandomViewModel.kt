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

    private var savedJokes: List<Joke> = emptyList()

    init {
        viewModelScope.launch {
            randomRepository.getSavedJokes().collect { result ->
                result.onSuccess { savedJokes = it }
            }
        }
    }

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
            is RandomAction.SaveJoke -> {
                mutableState.value.joke?.let { saveJoke(it) }
                reduce(action, mutableState.value)
            }
            is RandomAction.DeleteJoke -> {
                mutableState.value.joke?.let { deleteJoke(it) }
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
                isSavingJoke = false,
                isDeletingJoke = false,
                showError = false
            )
            RandomAction.LoadFirstJoke -> oldState.copy(
                isLoadingFirstJoke = true
            )
            RandomAction.LoadNextJoke -> oldState.copy(isLoadingNextJoke = true)
            RandomAction.SaveJoke -> oldState.copy(isSavingJoke = true)
            RandomAction.DeleteJoke -> oldState.copy(isDeletingJoke = true)
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
                        val isJokeSaved = savedJokes.map {item -> item.id }.contains(jokeResponse.id)
                        onAction(
                            RandomAction.JokeLoaded(Joke(jokeResponse, isJokeSaved))
                        )
                    }
                    else -> onAction(RandomAction.ShowError)
                }
            }
        }
    }

    private fun saveJoke(joke: Joke) {
        viewModelScope.launch {
            randomRepository.addJoke(joke).collect { response ->
                response.onSuccess {
                    onAction(
                        RandomAction.JokeLoaded(joke.copy(isSaved = true))
                    )
                }.onFailure {
                    onAction(
                        RandomAction.JokeLoaded(joke)
                    )
                }
            }
        }
    }

    private fun deleteJoke(joke: Joke) {
        viewModelScope.launch {
            randomRepository.deleteJoke(joke).collect { response ->
                response.onSuccess {
                    onAction(
                        RandomAction.JokeLoaded(joke.copy(isSaved = false))
                    )
                }.onFailure {
                    onAction(
                        RandomAction.JokeLoaded(joke)
                    )
                }
            }
        }
    }
}
