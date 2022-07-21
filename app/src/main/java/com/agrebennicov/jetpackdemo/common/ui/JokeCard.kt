package com.agrebennicov.jetpackdemo.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.theme.JetpackDemoTheme

@Composable
fun JokeCard(
    mainText: String,
    secondaryText: String,
    actionsEnabled: Boolean,
    isSaved: Boolean,
    onShareClicked: (() -> Unit)? = null,
    onSaveClicked: (() -> Unit)? = null
) {
    Card(elevation = 6.dp) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = mainText,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = secondaryText,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1
            )
            if (actionsEnabled) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.align(Alignment.End),
                ) {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onShareClicked?.invoke() },
                        painter = painterResource(R.drawable.ic_share_accent),
                        contentDescription = "Share"
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    val saveIcon =
                        if (isSaved) R.drawable.ic_save_accent_clicked else R.drawable.ic_save_accent
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onSaveClicked?.invoke() },
                        painter = painterResource(saveIcon),
                        contentDescription = "Save"
                    )
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
                mainText = "What do you call a fashionable lawn statue with an excellent sense of rhythmn?",
                secondaryText = "A metro-gnome",
                actionsEnabled = true,
                isSaved = false
            )
        }
    }
}
