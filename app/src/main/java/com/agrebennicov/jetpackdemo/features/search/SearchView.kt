package com.agrebennicov.jetpackdemo.features.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.theme.JetpackDemoTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    textValue: String,
    onQueryChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onClearButtonClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier.background(
            color = MaterialTheme.colors.background,
            shape = RoundedCornerShape(15.dp)
        )
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart),
            value = textValue,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onSearchClicked(textValue)
                }
            ),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                cursorColor = MaterialTheme.colors.primaryVariant,
                focusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                unfocusedIndicatorColor = MaterialTheme.colors.primaryVariant,
            ),
            onValueChange = { onQueryChanged(it) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            placeholder = {
                Text(
                    text = "Type in at least 3 chars",
                    style = MaterialTheme.typography.body2
                )
            },
            leadingIcon = {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.ic_search_accent),
                    contentDescription = "Search Icon",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primaryVariant)
                )
            },
            trailingIcon = {
                if (textValue.isNotBlank()) {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onClearButtonClick() },
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = "Search Icon"
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun SearchViewPreview() {
    JetpackDemoTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.surface)
        ) {
            SearchView(modifier = Modifier.padding(16.dp),
                textValue = "",
                onQueryChanged = {},
                onSearchClicked = {},
                onClearButtonClick = {}
            )

            Spacer(Modifier.size(24.dp))

            SearchView(
                modifier = Modifier.padding(16.dp),
                textValue = "Show me Jokes",
                onQueryChanged = {},
                onSearchClicked = {},
                onClearButtonClick = {}
            )
        }
    }
}
