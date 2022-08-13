package com.agrebennicov.jetpackdemo.features.saved

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import com.agrebennicov.jetpackdemo.common.ui.EmptyView
import com.agrebennicov.jetpackdemo.common.ui.JokeCard
import com.agrebennicov.jetpackdemo.common.ui.UnSavePrompt

@Composable
fun SavedScreen(
    state: State<SavedState>,
    onShareClick: (Joke) -> Unit,
    onUnSaveClick: (Joke) -> Unit,
    onUnSaveConfirmClick: (List<Joke>) -> Unit,
    onUnSaveCancelClick: () -> Unit,
    onSelectJoke: (Joke) -> Unit,
    onExitSelectionMode: () -> Unit,
    onEnterSelectionMode: () -> Unit,
) {
    ShowContent(
        state = state,
        onShareClick = onShareClick,
        onUnSaveClick = onUnSaveClick,
        onUnSaveConfirmClick = onUnSaveConfirmClick,
        onUnSaveCancelClick = onUnSaveCancelClick,
        onSelectJoke = onSelectJoke,
        onEnterSelectionMode = onEnterSelectionMode,
        onExitSelectionMode = onExitSelectionMode
    )
}

@Composable
private fun ShowContent(
    state: State<SavedState>,
    onShareClick: (Joke) -> Unit,
    onUnSaveClick: (Joke) -> Unit,
    onUnSaveConfirmClick: (List<Joke>) -> Unit,
    onUnSaveCancelClick: () -> Unit,
    onSelectJoke: (Joke) -> Unit,
    onEnterSelectionMode: () -> Unit,
    onExitSelectionMode: () -> Unit
) {
    val jokesToUnSave = state.value.jokesToUnSave
    val contentAlignment = when {
        state.value.showData && state.value.jokes.isNotEmpty() -> Alignment.TopCenter
        else -> Alignment.Center
    }

    if (jokesToUnSave.isNotEmpty()) {
        UnSavePrompt(
            onConfirm = { onUnSaveConfirmClick(jokesToUnSave) },
            onDismiss = onUnSaveCancelClick,
            jokesCount = jokesToUnSave.count()
        )
    }

    if (state.value.isSelectingMode) {
        BackHandler {
            onExitSelectionMode()
        }
    }

    Box {
        LazyColumn(
            modifier = Modifier
                .wrapContentSize()
                .align(contentAlignment),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                when {
                    state.value.showData && state.value.jokes.isNotEmpty() -> {
                        items(
                            count = state.value.jokes.size,
                            key = { state.value.jokes[it].content },
                            itemContent = {
                                val joke = state.value.jokes[it]
                                JokeCard(
                                    joke = joke,
                                    actionsEnabled = true,
                                    isSelectionActive = state.value.isSelectingMode,
                                    onShareClicked = { onShareClick(joke) },
                                    onSaveClicked = { onUnSaveClick(joke) },
                                    onSelect = { selectionActive ->
                                        if (selectionActive) {
                                            onEnterSelectionMode()
                                        }
                                        onSelectJoke(joke)
                                    }
                                )
                            }
                        )
                    }
                    state.value.showData && state.value.jokes.isEmpty() -> {
                        item { EmptyView(emptyText = stringResource(id = R.string.no_jokes_error_message)) }
                    }
                }
            }
        )
    }
}
