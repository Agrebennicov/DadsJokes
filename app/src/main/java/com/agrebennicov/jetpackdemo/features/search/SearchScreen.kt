package com.agrebennicov.jetpackdemo.features.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import com.agrebennicov.jetpackdemo.common.ui.EmptyView
import com.agrebennicov.jetpackdemo.common.ui.ErrorPlaceholder
import com.agrebennicov.jetpackdemo.common.ui.JokeCard
import com.agrebennicov.jetpackdemo.common.ui.Loader
import com.agrebennicov.jetpackdemo.features.search.searchView.ScrollableSearchView

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    state: State<SearchState>,
    scrollUpState: State<Boolean>,
    onQueryChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onShareClick: (Joke) -> Unit,
    onJokeSaved: (Joke) -> Unit,
    onJokeUnsaved: (Joke) -> Unit,
    onClearButtonClick: () -> Unit,
    onTryAgainButtonClick: () -> Unit,
    onFirstVisibleItemChanged: (Int) -> Unit
) {
    if (state.value.isError) {
        ShowError(
            modifier = Modifier,
            onTryAgainButtonClick = onTryAgainButtonClick
        )
    } else {
        ShowContent(
            state = state,
            scrollUpState = scrollUpState,
            onQueryChanged = onQueryChanged,
            onSearchClicked = onSearchClicked,
            onJokeSaved = onJokeSaved,
            onJokeUnsaved = onJokeUnsaved,
            onShareClick = onShareClick,
            onClearButtonClick = onClearButtonClick,
            onFirstVisibleItemChanged = onFirstVisibleItemChanged
        )
    }
}

@Composable
private fun ShowContent(
    state: State<SearchState>,
    scrollUpState: State<Boolean>,
    onQueryChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onJokeSaved: (Joke) -> Unit,
    onJokeUnsaved: (Joke) -> Unit,
    onShareClick: (Joke) -> Unit,
    onClearButtonClick: () -> Unit,
    onFirstVisibleItemChanged: (Int) -> Unit
) {

    val scrollState = rememberLazyListState()

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .collect { onFirstVisibleItemChanged(it) }
    }

    val alignment = if (state.value.showData) Alignment.TopCenter else Alignment.Center

    Box {
        LazyColumn(
            modifier = Modifier
                .wrapContentSize()
                .align(alignment),
            state = scrollState,
            contentPadding = PaddingValues(
                top = 56.dp + 32.dp,
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                when {
                    state.value.isLoading -> item { ShowLoading() }
                    state.value.showData -> {
                        if (state.value.jokes.isNotEmpty()) {
                            items(
                                count = state.value.jokes.size,
                                key = { state.value.jokes[it].content },
                                itemContent = {
                                    val joke = state.value.jokes[it]
                                    JokeCard(
                                        joke = joke,
                                        actionsEnabled = true,
                                        isSelectionActive = false,
                                        onShareClicked = { onShareClick(joke) },
                                        onSaveClicked = {
                                            if (state.value.jokes[it].isSaved) {
                                                onJokeUnsaved(joke)
                                            } else {
                                                onJokeSaved(joke)
                                            }
                                        }
                                    )
                                }
                            )
                        } else {
                            item { EmptyView(emptyText = "Sorry, there is nothing on\n\"${state.value.query}\"") }
                        }
                    }
                }
            }
        )

        ScrollableSearchView(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            textValue = state.value.query,
            onQueryChanged = onQueryChanged,
            onSearchClicked = onSearchClicked,
            onClearButtonClick = onClearButtonClick,
            scrollUpState = scrollUpState
        )
    }
}

@Composable
private fun ShowLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Loader()
    }
}

@Composable
private fun ShowError(
    modifier: Modifier = Modifier,
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
