package com.agrebennicov.jetpackdemo.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.agrebennicov.jetpackdemo.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UnSavePrompt(
    jokesCount: Int,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .size(100.dp),
                painter = painterResource(R.drawable.ic_dad2),
                contentDescription = "Dad"
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                text = pluralStringResource(id = R.plurals.unSavePromptTitle, count = jokesCount),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption
            )

            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onConfirm() },
                    text = stringResource(id = R.string.delete_prompt_positive_message),
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onDismiss() },
                    text = stringResource(id = R.string.delete_prompt_negative_message),
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
