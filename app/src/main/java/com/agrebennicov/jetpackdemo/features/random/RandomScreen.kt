package com.agrebennicov.jetpackdemo.features.random

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import com.agrebennicov.jetpackdemo.common.ui.CommonButton
import com.agrebennicov.jetpackdemo.common.ui.ErrorPlaceholder
import com.agrebennicov.jetpackdemo.common.ui.JokeCard
import com.agrebennicov.jetpackdemo.common.ui.Loader
import com.agrebennicov.jetpackdemo.common.util.getAppDefaultAnimation
import com.agrebennicov.jetpackdemo.common.util.getNonAnimatedContentTransform
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RandomScreen(
    modifier: Modifier = Modifier,
    state: State<RandomState>,
    onSaveClick: (Joke) -> Unit,
    onDeleteClick: (Joke) -> Unit,
    onShareClick: (Joke) -> Unit,
    onNextJokeClick: () -> Unit,
    onTryAgainButtonClick: () -> Unit,
    onDownloadClicked: (Joke) -> Unit,
    onDownloadAnimationFinished: () -> Unit
) {
    val statusBarColor = MaterialTheme.colors.primaryVariant
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
    }

    AnimatedVisibility(
        visible = state.value.showDownloadConfirmation,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        DownloadConfirmation(onAnimationFinished = onDownloadAnimationFinished)
    }

    AnimatedContent(
        targetState = state.value,
        transitionSpec = {
            val loadedNextJokeSuccessfully = initialState.isLoadingNextJoke &&
                    !targetState.showError
            val isLoadingNextJoke = targetState.isLoadingNextJoke
            val saved = initialState.joke != null &&
                    !initialState.joke!!.isSaved &&
                    targetState.joke != null &&
                    targetState.joke!!.isSaved
            val deleted = initialState.joke != null &&
                    initialState.joke!!.isSaved &&
                    targetState.joke != null &&
                    !targetState.joke!!.isSaved
            val isDownloading = initialState.isDownloading || targetState.isDownloading
            val showHideDownloadConfirmation = targetState.showDownloadConfirmation ||
                    (initialState.showDownloadConfirmation && !targetState.showDownloadConfirmation)

            when {
                loadedNextJokeSuccessfully ||
                        isLoadingNextJoke ||
                        saved ||
                        deleted ||
                        isDownloading ||
                        showHideDownloadConfirmation -> getNonAnimatedContentTransform()
                else -> getAppDefaultAnimation()
            }
        }
    ) { targetState ->
        when {
            targetState.isLoadingFirstJoke -> ShowLoading(modifier = modifier)
            targetState.joke != null -> {
                ShowContent(
                    modifier = modifier,
                    joke = targetState.joke,
                    isNextJokeLoading = targetState.isLoadingNextJoke,
                    onSaveClick = onSaveClick,
                    onDeleteClick = onDeleteClick,
                    onShareClick = onShareClick,
                    onNextJokeClick = onNextJokeClick,
                    onDownloadClicked = onDownloadClicked,
                    isShowingDownload = targetState.showDownloadConfirmation,
                    isDownloading = targetState.isDownloading
                )
            }
            targetState.showError -> ShowError(
                modifier = modifier,
                onTryAgainButtonClick = onTryAgainButtonClick
            )
        }
    }
}

@Composable
private fun ShowContent(
    modifier: Modifier,
    joke: Joke,
    isNextJokeLoading: Boolean,
    onSaveClick: (Joke) -> Unit,
    onDeleteClick: (Joke) -> Unit,
    onShareClick: (Joke) -> Unit,
    onNextJokeClick: () -> Unit,
    onDownloadClicked: (Joke) -> Unit,
    isShowingDownload: Boolean,
    isDownloading: Boolean
) {
    val scrollState = rememberScrollState()
    val areButtonEnabled = !isNextJokeLoading && !isShowingDownload && !isDownloading

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        JokeCard(
            Modifier.padding(top = 16.dp),
            joke = joke,
            actionsEnabled = false,
            isSelectionActive = false
        )

        Spacer(modifier = Modifier.weight(1f))

        CommonButton(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = stringResource(id = R.string.download),
            backGroundColor = MaterialTheme.colors.primaryVariant,
            icon = R.drawable.ic_download,
            onButtonClicked = { onDownloadClicked(joke) },
            isLoading = isDownloading,
            enabled = areButtonEnabled
        )

        Spacer(modifier = Modifier.size(16.dp))

        Row {
            CommonButton(
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentHeight(),
                text = stringResource(if (joke.isSaved) R.string.delete else R.string.save),
                backGroundColor = MaterialTheme.colors.primaryVariant,
                icon = if (joke.isSaved) R.drawable.ic_unsave else R.drawable.ic_save,
                onButtonClicked = if (joke.isSaved) {
                    { onDeleteClick(joke) }
                } else {
                    { onSaveClick(joke) }
                },
                enabled = areButtonEnabled
            )

            Spacer(modifier = Modifier.size(16.dp))

            CommonButton(
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentHeight(),
                text = stringResource(id = R.string.share),
                backGroundColor = MaterialTheme.colors.primaryVariant,
                icon = R.drawable.ic_share,
                onButtonClicked = { onShareClick(joke) },
                enabled = areButtonEnabled
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        CommonButton(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = stringResource(id = R.string.next_random_joke),
            backGroundColor = MaterialTheme.colors.primaryVariant,
            icon = R.drawable.ic_next,
            onButtonClicked = onNextJokeClick,
            enabled = areButtonEnabled,
            isLoading = isNextJokeLoading
        )

        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun ShowError(
    modifier: Modifier,
    onTryAgainButtonClick: () -> Unit
) {
    Box(modifier = modifier) {
        ErrorPlaceholder(
            topImage = R.drawable.ic_error,
            centerImage = R.drawable.ic_dad,
            text = stringResource(id = R.string.common_error),
            onTryAgainButtonClick = onTryAgainButtonClick
        )
    }
}

@Composable
private fun ShowLoading(modifier: Modifier) {
    Box(modifier = modifier) {
        Loader()
    }
}

