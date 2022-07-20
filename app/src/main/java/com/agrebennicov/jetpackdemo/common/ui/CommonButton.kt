package com.agrebennicov.jetpackdemo.common.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommonButton(
    modifier: Modifier,
    text: String,
    backGroundColor: Color,
    @DrawableRes icon: Int? = null,
    onButtonClicked: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = CardShape.medium,
        elevation = 5.dp,
        onClick = onButtonClicked
    ) {
        Box(
            modifier = Modifier
                .background(backGroundColor)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
                style = MaterialTheme.typography.button,
                text = text
            )
            if (icon != null) {
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterEnd),
                    painter = painterResource(icon),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "Action Icon",
                )
            }
        }
    }
}

@Preview
@Composable
fun CommonButtonPreview() {
    JetpackDemoTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Background)
        ) {
            CommonButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                text = "Button Preview",
                backGroundColor = ButtonBackgroundMain,
                onButtonClicked = {}
            )

            CommonButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                text = "Button Preview",
                backGroundColor = ButtonBackgroundSecondary,
                icon = R.drawable.ic_next,
                onButtonClicked = {}
            )
        }
    }
}
