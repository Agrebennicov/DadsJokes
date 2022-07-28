package com.agrebennicov.jetpackdemo.features.random

import com.agrebennicov.jetpackdemo.common.pojo.Joke

data class RandomState(
    val isLoadingFirstJoke: Boolean = false,
    val isLoadingNextJoke: Boolean = false,
    val joke: Joke? = null,
    val showError: Boolean = false
)

sealed class RandomAction {
    object LoadFirstJoke : RandomAction()
    object LoadNextJoke : RandomAction()
    data class JokeLoaded(val joke: Joke) : RandomAction()
    data class SaveJoke(val joke: Joke) : RandomAction()
    data class DeleteJoke(val joke: Joke) : RandomAction()
    object ShareJoke : RandomAction()
    object ShowError : RandomAction()
}