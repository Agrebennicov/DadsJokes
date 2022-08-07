package com.agrebennicov.jetpackdemo.features.search

import androidx.lifecycle.viewModelScope
import com.agrebennicov.jetpackdemo.common.BaseViewModel
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import com.agrebennicov.jetpackdemo.common.util.ShareUtil
import com.agrebennicov.jetpackdemo.features.search.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val shareUtil: ShareUtil,
) : BaseViewModel<SearchAction, SearchState>(SearchState()) {
    override fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.Search -> {
                mutableState.value = reduce(action, mutableState.value)
                if (action.query.length >= 3) {
                    search(action.query)
                }
            }
            is SearchAction.JokeSaved -> {
                mutableState.value = reduce(action, mutableState.value)
                saveJoke(action.joke)
            }
            is SearchAction.JokeUnSaved -> {
                mutableState.value = reduce(action, mutableState.value)
                deleteJoke(action.joke)
            }
            is SearchAction.ShareJoke -> {
                mutableState.value = reduce(action, mutableState.value)
                shareUtil.share(action.context, action.joke)
            }
            SearchAction.Refresh -> {
                mutableState.value = reduce(action, mutableState.value)
                refreshIfNeeded()
            }
            SearchAction.Error,
            is SearchAction.QueryChanged,
            is SearchAction.JokesLoaded -> mutableState.value = reduce(action, mutableState.value)
        }
    }

    private fun refreshIfNeeded() {
        if (state.value.jokes.isNotEmpty() && state.value.showData) {
            viewModelScope.launch {
                val localJokesIds =
                    searchRepository.getStoredJokesIdsByIds(state.value.jokes.map { it.id })
                val mergedJokes =
                    state.value.jokes.map { it.copy(isSaved = localJokesIds.contains(it.id)) }
                onAction(SearchAction.JokesLoaded(mergedJokes))
            }
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
            SearchAction.Refresh,
            is SearchAction.ShareJoke -> oldState
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
                    onAction(SearchAction.JokesLoaded(jokes = data))
                } else {
                    onAction(SearchAction.Error)
                }
            }
        }
    }

    private fun saveJoke(joke: Joke) {
        viewModelScope.launch { searchRepository.addJoke(joke.copy(isSaved = true)) }
    }

    private fun deleteJoke(joke: Joke) {
        viewModelScope.launch { searchRepository.deleteJoke(joke) }
    }
}
