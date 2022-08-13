package com.agrebennicov.jetpackdemo.features.saved

import android.content.Context
import com.agrebennicov.jetpackdemo.common.pojo.Joke

data class SavedState(
    val jokes: List<Joke> = emptyList(),
    val jokesToUnSave: List<Joke> = emptyList(),
    val isSelectingMode: Boolean = false,
    val showData: Boolean = false
)

sealed class SavedAction {
    data class JokeSelected(val joke: Joke) : SavedAction()
    data class DataLoaded(val jokes: List<Joke>) : SavedAction()
    data class ShareJoke(val context: Context, val jokes: List<Joke>) : SavedAction()
    data class UnSaveJoke(val joke: Joke) : SavedAction()
    data class UnSaveJokeConfirmation(val jokes: List<Joke>) : SavedAction()
    object LoadData : SavedAction()
    object Refresh : SavedAction()
    object UnSaveJokeCancel : SavedAction()
    object SelectionClosed : SavedAction()
    object SelectionOpened : SavedAction()
    object UnSaveSelected : SavedAction()
}