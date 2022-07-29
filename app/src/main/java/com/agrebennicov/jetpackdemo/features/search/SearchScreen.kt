package com.agrebennicov.jetpackdemo.features.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import com.agrebennicov.jetpackdemo.common.ui.EmptyView
import com.agrebennicov.jetpackdemo.common.ui.ErrorPlaceholder
import com.agrebennicov.jetpackdemo.common.ui.JokeCard
import com.agrebennicov.jetpackdemo.common.ui.Loader

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    state: State<SearchState>,
    onQueryChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onJokeSaved: (Joke) -> Unit,
    onJokeUnsaved: (Joke) -> Unit,
    onClearButtonClick: () -> Unit,
    onTryAgainButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                contentPadding = PaddingValues(start = 16.dp),
                elevation = 16.dp
            ) {
                Text(
                    style = MaterialTheme.typography.h1,
                    text = "Search"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            if (state.value.isError) {
                ShowError(
                    modifier = Modifier,
                    onTryAgainButtonClick = onTryAgainButtonClick
                )
            } else {
                ShowContent(
                    state,
                    onQueryChanged,
                    onSearchClicked,
                    onJokeSaved,
                    onJokeUnsaved,
                    onClearButtonClick,
                )
            }
        }
    }
}

@Composable
private fun ShowContent(
    state: State<SearchState>,
    onQueryChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onJokeSaved: (Joke) -> Unit,
    onJokeUnsaved: (Joke) -> Unit,
    onClearButtonClick: () -> Unit
) {
    SearchView(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        textValue = state.value.query,
        onQueryChanged = onQueryChanged,
        onSearchClicked = onSearchClicked,
        onClearButtonClick = onClearButtonClick
    )

    when {
        state.value.isLoading -> ShowLoading()
        state.value.showData -> {
            if (state.value.jokes.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    content = {
                        items(
                            count = state.value.jokes.size,
                            key = { state.value.jokes[it].content },
                            itemContent = {
                                JokeCard(
                                    joke = state.value.jokes[it],
                                    actionsEnabled = true,
                                    isSelectionActive = false,
                                    onShareClicked = {},
                                    onSaveClicked = {
                                        val joke = state.value.jokes[it]
                                        if (state.value.jokes[it].isSaved) {
                                            onJokeUnsaved(joke)
                                        } else {
                                            onJokeSaved(joke)
                                        }
                                    }
                                )
                            }
                        )
                    }
                )
            } else {
                EmptyView(emptyText = "Sorry, there is nothing on\n\"${state.value.query}\"")
            }
        }
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
