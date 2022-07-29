package com.agrebennicov.jetpackdemo.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.theme.JetpackDemoTheme

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    emptyText: String
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .wrapContentSize()
                .align(Alignment.Center)
        ) {
            Icon(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .size(75.dp),
                painter = painterResource(id = R.drawable.ic_box),
                contentDescription = "Empty Icon"
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = emptyText,
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun EmptyViewPreview() {
    JetpackDemoTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.surface)
        ) {
            EmptyView(emptyText = "No Info Here")
        }
    }
}
