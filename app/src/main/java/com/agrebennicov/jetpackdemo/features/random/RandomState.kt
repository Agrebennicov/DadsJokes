package com.agrebennicov.jetpackdemo.features.random

import android.content.Context
import com.agrebennicov.jetpackdemo.common.pojo.Joke

data class RandomState(
    val isLoadingFirstJoke: Boolean = false,
    val isLoadingNextJoke: Boolean = false,
    val joke: Joke? = null,
    val showError: Boolean = false,
    val showDownloadConfirmation: Boolean = false,
    val isDownloading: Boolean = false
)

sealed class RandomAction {
    object LoadFirstJoke : RandomAction()
    object LoadNextJoke : RandomAction()
    data class JokeLoaded(val joke: Joke) : RandomAction()
    data class SaveJoke(val joke: Joke) : RandomAction()
    data class DeleteJoke(val joke: Joke) : RandomAction()
    data class ShareJoke(val context: Context, val joke: Joke) : RandomAction()
    object ShowError : RandomAction()
    data class DownloadJoke(val joke: Joke) : RandomAction()
    object DownloadAnimationFinished : RandomAction()
    object DownloadSuccess : RandomAction()
    object Refresh : RandomAction()
}