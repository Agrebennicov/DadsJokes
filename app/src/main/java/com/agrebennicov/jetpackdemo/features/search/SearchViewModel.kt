package com.agrebennicov.jetpackdemo.features.search

import androidx.lifecycle.viewModelScope
import com.agrebennicov.jetpackdemo.common.BaseViewModel
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import com.agrebennicov.jetpackdemo.features.search.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : BaseViewModel<SearchAction, SearchState>(SearchState()) {
    override fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.Search -> {
                if (action.query.length >= 3) {
                    search(action.query)
                }
                mutableState.value = reduce(action, mutableState.value)
            }
            is SearchAction.JokeSaved -> {
                saveJoke(action.joke)
                mutableState.value = reduce(action, mutableState.value)
            }
            is SearchAction.JokeUnSaved -> {
                deleteJoke(action.joke)
                mutableState.value = reduce(action, mutableState.value)
            }
            SearchAction.Error,
            is SearchAction.QueryChanged,
            is SearchAction.JokesLoaded -> mutableState.value = reduce(action, mutableState.value)
        }
    }

    override fun reduce(action: SearchAction, oldState: SearchState): SearchState {
        return when (action) {
            SearchAction.Error -> oldState.copy(isError = true)
            is SearchAction.QueryChanged -> oldState.copy(
                query = action.query,
                showData = false,
                isError = false,
                isLoading = false
            )
            is SearchAction.Search -> {
                return when {
                    action.query.length >= 3 -> oldState.copy(
                        query = action.query,
                        isLoading = true,
                        showData = false
                    )
                    else -> oldState.copy(query = action.query)
                }
            }
            is SearchAction.JokesLoaded -> oldState.copy(
                jokes = action.jokes,
                isLoading = false,
                showData = true
            )
            is SearchAction.JokeSaved -> updateSavedState(action.joke, oldState)
            is SearchAction.JokeUnSaved -> updateSavedState(action.joke, oldState)
        }
    }

    private fun updateSavedState(joke: Joke, state: SearchState) = state.copy(
        jokes = state.jokes.map {
            if (joke.id == it.id) it.copy(isSaved = !it.isSaved) else it
        }
    )

    private fun search(query: String) {
        viewModelScope.launch {
            searchRepository.searchJokes(query).collect {
                val data = it.getOrNull()
                if (it.isSuccess && data != null) {
                    onAction(
                        SearchAction.JokesLoaded(
                            jokes = data.jokes.map { jokeResponse -> Joke(jokeResponse) },
                            currentPage = data.currentPage,
                            nextPage = data.nextPage,
                            totalPages = data.totalPages
                        )
                    )
                } else {
                    onAction(SearchAction.Error)
                }
            }
        }
    }

    private fun saveJoke(joke: Joke) {
        viewModelScope.launch { searchRepository.addJoke(joke) }
    }

    private fun deleteJoke(joke: Joke) {
        viewModelScope.launch { searchRepository.deleteJoke(joke) }
    }
}
