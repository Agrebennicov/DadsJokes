package com.agrebennicov.jetpackdemo.common.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import com.agrebennicov.jetpackdemo.common.theme.JetpackDemoTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JokeCard(
    modifier: Modifier = Modifier,
    joke: Joke,
    actionsEnabled: Boolean,
    isSelectionActive: Boolean,
    onShareClicked: (() -> Unit)? = null,
    onSaveClicked: (() -> Unit)? = null,
    onSelect: ((Boolean) -> Unit)? = null,
) {
    key(joke.content) {
        Card(
            elevation = 16.dp,
            modifier = modifier
                .combinedClickable(
                    onClick = { if (isSelectionActive) onSelect?.invoke(false) },
                    onLongClick = { if (!isSelectionActive) onSelect?.invoke(true) }
                ),
            backgroundColor = if (joke.isSelected) MaterialTheme.colors.surface else
                MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = joke.content.trim(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1
                )
                if (actionsEnabled) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Crossfade(
                            targetState = joke.isSelected,
                            animationSpec = tween(durationMillis = 100)
                        ) { isSelected ->
                            if (isSelectionActive)
                                if (isSelected)
                                    Image(
                                        modifier = Modifier.size(24.dp),
                                        painter = painterResource(R.drawable.ic_checked_circle),
                                        contentDescription = "Checked"
                                    )
                                else
                                    Image(
                                        modifier = Modifier.size(24.dp),
                                        painter = painterResource(R.drawable.ic_unchecked_circle),
                                        contentDescription = "Unchecked"
                                    )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { onShareClicked?.invoke() },
                                painter = painterResource(R.drawable.ic_share_accent),
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.primaryVariant),
                                contentDescription = "Share"
                            )
                            Spacer(modifier = Modifier.width(24.dp))
                            val saveIcon =
                                if (joke.isSaved) R.drawable.ic_save_accent_clicked else R.drawable.ic_save_accent
                            Image(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { onSaveClicked?.invoke() },
                                painter = painterResource(saveIcon),
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.primaryVariant),
                                contentDescription = "Save"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun JokeCardPreview() {
    JetpackDemoTheme {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            JokeCard(
                joke = Joke(
                    id = "0",
                    content = "What do you call a fashionable lawn statue with an excellent sense of rhythmn?\nA metro-gnome",
                    isSaved = true,
                    isSelected = false
                ),
                actionsEnabled = true,
                isSelectionActive = false
            )
        }
    }
}

@Preview
@Composable
fun JokeListPreview() {
    JetpackDemoTheme {
        var jokeList by remember {
            mutableStateOf(
                listOf(
                    Joke(
                        id = "0",
                        content = "Wha-gnome",
                        isSaved = true,
                        isSelected = false
                    ),
                    Joke(
                        id = "0",
                        content = "What do you call a fashionable lawn statue with an excellent sense of rhythmn?\nA metro-gnome",
                        isSaved = true,
                        isSelected = false
                    ),
                    Joke(
                        id = "0",
                        content = "What do you call a fashionable lawn statue with an excellent sense of rhythmn?\nA metro-gnome",
                        isSaved = true,
                        isSelected = false
                    )
                )
            )
        }
        var isSelectionActive by remember { mutableStateOf(false) }
        BackHandler {
            jokeList = jokeList.map { item ->
                item.copy(isSelected = false)
            }
            isSelectionActive = false
        }
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(jokeList.size) { i ->
                    JokeCard(
                        joke = jokeList[i],
                        actionsEnabled = true,
                        isSelectionActive = isSelectionActive,
                        onSelect = { selectionActive ->
                            if (selectionActive) {
                                isSelectionActive = true
                            }
                            jokeList = jokeList.mapIndexed { index, item ->
                                if (i == index) {
                                    item.copy(isSelected = !item.isSelected)
                                } else {
                                    item
                                }
                            }
                        })
                }
            }
        }
    }
}
