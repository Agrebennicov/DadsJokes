package com.agrebennicov.jetpackdemo.features.saved

import androidx.lifecycle.viewModelScope
import com.agrebennicov.jetpackdemo.common.BaseViewModel
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import com.agrebennicov.jetpackdemo.common.util.ShareUtil
import com.agrebennicov.jetpackdemo.features.saved.repository.SavedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val savedRepo: SavedRepository,
    private val shareUtil: ShareUtil
) : BaseViewModel<SavedAction, SavedState>(SavedState()) {

    init {
        onAction(SavedAction.LoadData)
    }

    override fun onAction(action: SavedAction) {
        when (action) {
            SavedAction.LoadData -> {
                updateState(action)
                loadData()
            }
            SavedAction.Refresh -> {
                updateState(action)
                refreshIfNeeded()
            }
            is SavedAction.ShareJoke -> {
                updateState(action)
                if (action.jokes.count() == 1) {
                    shareUtil.share(action.context, action.jokes[0])
                } else {
                    shareUtil.share(action.context, action.jokes)
                }
            }
            is SavedAction.UnSaveJokeConfirmation -> {
                updateState(action)
                unSaveJoke(action.jokes)
            }
            is SavedAction.JokeSelected -> {
                updateState(action)
                updateSelection(action.joke)
            }
            SavedAction.SelectionClosed -> {
                updateState(action)
                removeAllSelections()
            }
            is SavedAction.DataLoaded,
            is SavedAction.UnSaveJoke,
            SavedAction.UnSaveJokeCancel,
            SavedAction.SelectionOpened,
            SavedAction.UnSaveSelected -> updateState(action)
        }
    }

    override fun reduce(action: SavedAction, oldState: SavedState): SavedState {
        return when (action) {
            is SavedAction.DataLoaded -> oldState.copy(
                jokes = action.jokes,
                showData = true
            )
            is SavedAction.UnSaveJoke -> oldState.copy(jokesToUnSave = listOf(action.joke))
            SavedAction.UnSaveJokeCancel,
            is SavedAction.UnSaveJokeConfirmation -> oldState.copy(
                jokesToUnSave = emptyList(),
                isSelectingMode = false,
                jokes = oldState.jokes.mapNotNull { if (!it.isSelected) it else null }
            )
            SavedAction.SelectionClosed -> oldState.copy(isSelectingMode = false)
            SavedAction.SelectionOpened -> oldState.copy(isSelectingMode = true)
            SavedAction.UnSaveSelected -> oldState.copy(
                jokesToUnSave = oldState.jokes.filter { it.isSelected }
            )
            is SavedAction.ShareJoke -> oldState.copy(
                isSelectingMode = false,
                jokes = oldState.jokes.map { it.copy(isSelected = false) }
            )
            is SavedAction.JokeSelected,
            SavedAction.LoadData,
            SavedAction.Refresh -> oldState
        }
    }

    private fun unSaveJoke(jokes: List<Joke>) {
        viewModelScope.launch {
            savedRepo.deleteJokes(jokes)
            val currentJokes = state.value.jokes.toMutableList()
            currentJokes.removeAll(jokes)
            onAction(SavedAction.DataLoaded(currentJokes))
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            onAction(SavedAction.DataLoaded(savedRepo.getAllJokes()))
        }
    }

    private fun refreshIfNeeded() {
        val currentJokes = state.value.jokes
        val currentJokesMap = state.value.jokes.associate { it.id to it.isSelected }
        viewModelScope.launch {
            val localJokes = savedRepo.getAllJokes()
            if (localJokes != currentJokes) {
                val updatedJokes = localJokes.map {
                    it.copy(isSelected = currentJokesMap[it.id] == true)
                }
                onAction(SavedAction.DataLoaded(updatedJokes))
            }
        }
    }

    private fun updateSelection(joke: Joke) {
        val jokes = state.value.jokes.map {
            if (it.id == joke.id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
        onAction(SavedAction.DataLoaded(jokes))
    }

    private fun removeAllSelections() {
        val jokes = state.value.jokes
        onAction(SavedAction.DataLoaded(jokes.map { it.copy(isSelected = false) }))
    }
}
