package com.agrebennicov.jetpackdemo.features.random

import androidx.lifecycle.viewModelScope
import com.agrebennicov.jetpackdemo.common.BaseViewModel
import com.agrebennicov.jetpackdemo.common.di.BASE_URL
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import com.agrebennicov.jetpackdemo.common.util.DownloadManager
import com.agrebennicov.jetpackdemo.common.util.ShareUtil
import com.agrebennicov.jetpackdemo.features.random.repository.RandomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val randomRepository: RandomRepository,
    private val downloadManager: DownloadManager,
    private val shareUtil: ShareUtil,
) : BaseViewModel<RandomAction, RandomState>(RandomState(isLoadingFirstJoke = true)) {

    init {
        onAction(RandomAction.LoadFirstJoke)
    }

    override fun onAction(action: RandomAction) {
        when (action) {
            RandomAction.LoadFirstJoke -> {
                updateState(action)
                fetchJoke()
            }
            is RandomAction.LoadNextJoke -> {
                updateState(action)
                fetchJoke()
            }
            is RandomAction.SaveJoke -> {
                updateState(action)
                saveJoke(action.joke)
            }
            is RandomAction.DeleteJoke -> {
                updateState(action)
                deleteJoke(action.joke)
            }
            is RandomAction.DownloadJoke -> {
                updateState(action)
                downloadJoke(action.joke)
            }
            is RandomAction.ShareJoke -> {
                updateState(action)
                shareUtil.share(action.context, action.joke)
            }
            RandomAction.Refresh -> {
                updateState(action)
                refreshJokeIfNeeded()
            }
            is RandomAction.JokeLoaded,
            RandomAction.DownloadAnimationFinished,
            RandomAction.DownloadSuccess,
            RandomAction.ShowError -> updateState(action)
        }
    }

    private fun refreshJokeIfNeeded() {
        viewModelScope.launch {
            state.value.joke?.let {
                val localJoke = randomRepository.getJoke(it.id)
                when {
                    localJoke == it -> return@launch
                    localJoke != null -> onAction(RandomAction.JokeLoaded(it.copy(isSaved = true)))
                    else -> onAction(RandomAction.JokeLoaded(it.copy(isSaved = false)))
                }
            }
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
            RandomAction.LoadFirstJoke -> oldState.copy(isLoadingFirstJoke = true)
            RandomAction.LoadNextJoke -> oldState.copy(isLoadingNextJoke = true)
            is RandomAction.SaveJoke -> oldState.copy(joke = action.joke.copy(isSaved = true))
            is RandomAction.DeleteJoke -> oldState.copy(joke = action.joke.copy(isSaved = false))
            RandomAction.ShowError -> oldState.copy(
                showError = true,
                joke = null,
                isLoadingFirstJoke = false,
                isLoadingNextJoke = false,
                isDownloading = false,
                showDownloadConfirmation = false
            )
            is RandomAction.DownloadJoke -> oldState.copy(isDownloading = true)
            RandomAction.DownloadAnimationFinished -> oldState.copy(showDownloadConfirmation = false)
            RandomAction.DownloadSuccess -> oldState.copy(
                showDownloadConfirmation = true,
                isDownloading = false
            )
            is RandomAction.ShareJoke,
            RandomAction.Refresh -> oldState
        }
    }

    private fun fetchJoke() {
        viewModelScope.launch {
            randomRepository.fetchRandomJoke().collect {
                val joke = it.getOrNull()
                when {
                    it.isSuccess && joke != null -> onAction(RandomAction.JokeLoaded(joke))
                    else -> onAction(RandomAction.ShowError)
                }
            }
        }
    }

    private fun saveJoke(joke: Joke) {
        viewModelScope.launch { randomRepository.addJoke(joke.copy(isSaved = true)) }
    }

    private fun deleteJoke(joke: Joke) {
        viewModelScope.launch { randomRepository.deleteJoke(joke) }
    }

    private fun downloadJoke(joke: Joke) {
        viewModelScope.launch {
            downloadManager.downloadImage(
                "${BASE_URL}j/${joke.id}.png",
                onDownloaded = { onAction(RandomAction.DownloadSuccess) },
                onError = { onAction(RandomAction.ShowError) }
            )
        }
    }
}
