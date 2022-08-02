package com.agrebennicov.jetpackdemo.features.random

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RandomScreen(
    modifier: Modifier = Modifier,
    state: State<RandomState>,
    onSaveClick: (Joke) -> Unit,
    onDeleteClick: (Joke) -> Unit,
    onShareClick: () -> Unit,
    onNextJokeClick: () -> Unit,
    onTryAgainButtonClick: () -> Unit
) {
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

            when {
                loadedNextJokeSuccessfully || isLoadingNextJoke || saved || deleted -> {
                    getNonAnimatedContentTransform()
                }
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
                    onNextJokeClick = onNextJokeClick
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
    onShareClick: () -> Unit,
    onNextJokeClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.fillMaxSize(0.1f))
        JokeCard(
            joke = joke,
            actionsEnabled = false,
            isSelectionActive = false
        )

        Spacer(modifier = Modifier.weight(1f))

        Row {
            CommonButton(
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentHeight(),
                text = stringResource(if (joke.isSaved) R.string.delete else R.string.save),
                backGroundColor = MaterialTheme.colors.primaryVariant,
                icon = if (joke.isSaved) R.drawable.ic_save_white_clicked else R.drawable.ic_save_white,
                onButtonClicked = if (joke.isSaved) {
                    { onDeleteClick(joke) }
                } else {
                    { onSaveClick(joke) }
                },
                enabled = !isNextJokeLoading
            )

            Spacer(modifier = Modifier.size(16.dp))

            CommonButton(
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentHeight(),
                text = "Share",
                backGroundColor = MaterialTheme.colors.primaryVariant,
                icon = R.drawable.ic_share_white,
                onButtonClicked = onShareClick,
                enabled = !isNextJokeLoading
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        CommonButton(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = "Next Random Joke",
            backGroundColor = MaterialTheme.colors.primaryVariant,
            icon = R.drawable.ic_next,
            onButtonClicked = onNextJokeClick,
            enabled = !isNextJokeLoading,
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

