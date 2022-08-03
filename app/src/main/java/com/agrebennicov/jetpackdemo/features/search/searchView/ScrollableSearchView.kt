package com.agrebennicov.jetpackdemo.features.search.searchView

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun ScrollableSearchView(
    modifier: Modifier = Modifier,
    textValue: String,
    onQueryChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onClearButtonClick: () -> Unit,
    scrollUpState: State<Boolean?>,
) {
    val position by animateFloatAsState(if (scrollUpState.value == true) -300f else 0f)

    Surface(modifier = modifier.graphicsLayer { translationY = (position) }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .background(color = MaterialTheme.colors.surface)
        ) {
            SearchView(
                modifier = Modifier.padding(16.dp),
                textValue = textValue,
                onQueryChanged = onQueryChanged,
                onSearchClicked = onSearchClicked,
                onClearButtonClick = onClearButtonClick
            )
        }
    }
}
