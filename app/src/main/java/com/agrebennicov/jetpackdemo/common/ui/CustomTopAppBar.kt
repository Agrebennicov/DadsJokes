package com.agrebennicov.jetpackdemo.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.features.main.toolbar.ToolbarState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTopAppBar(
    state: State<ToolbarState>,
    onCloseSelectionClick: () -> Unit,
    onShareClick: () -> Unit,
    onUnSaveJokes: () -> Unit
) {
    TopAppBar(
        contentPadding = PaddingValues(start = 16.dp),
        elevation = 16.dp
    ) {
        if (state.value.isSelecting) {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable { onCloseSelectionClick() },
                painter = painterResource(R.drawable.ic_close),
                contentDescription = "Share"
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                style = MaterialTheme.typography.h1,
                text = pluralStringResource(
                    R.plurals.jokesSelected,
                    state.value.selectingCount,
                    state.value.selectingCount
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable { onShareClick() },
                painter = painterResource(R.drawable.ic_share),
                contentDescription = "Share"
            )

            Spacer(modifier = Modifier.size(24.dp))

            Image(
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable { onUnSaveJokes() },
                painter = painterResource(R.drawable.ic_unsave),
                contentDescription = "UnSave"
            )

            Spacer(modifier = Modifier.size(16.dp))
        } else {
            state.value.toolbarTitle?.let {
                Text(
                    style = MaterialTheme.typography.h1,
                    text = stringResource(id = it)
                )
            }
        }
    }
}