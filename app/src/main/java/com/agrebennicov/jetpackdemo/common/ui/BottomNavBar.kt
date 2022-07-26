package com.agrebennicov.jetpackdemo.common.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.theme.JetpackDemoTheme
import com.agrebennicov.jetpackdemo.common.theme.Surface

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem>,
    onItemSelected: (String) -> Unit
) {
    BottomAppBar(
        modifier = modifier
            .background(color = MaterialTheme.colors.surface)
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (item.isSelected) {
                            Box(
                                modifier = Modifier
                                    .height(4.dp)
                                    .fillMaxWidth(0.6f)
                                    .padding(top = 1.dp)
                                    .align(Alignment.TopCenter)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(
                                            bottomEnd = 15.dp,
                                            bottomStart = 15.dp
                                        )
                                    )
                            )
                        }
                        val icon = if (item.isSelected) item.selectedImage else item.unSelectedImage
                        Icon(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(24.dp)
                                .align(Alignment.Center),
                            painter = painterResource(id = icon),
                            contentDescription = item.route
                        )
                    }
                },
                selected = item.isSelected,
                onClick = { onItemSelected(item.route) }
            )
        }
    }
}

@Preview
@Composable
fun BottomNavBarPreview() {
    val bottomNavItems = listOf(
        BottomNavItem(
            route = "Random",
            selectedImage = R.drawable.ic_shuffle_white_clicked,
            unSelectedImage = R.drawable.ic_shuffle_white,
            isSelected = true
        ),
        BottomNavItem(
            route = "Search",
            selectedImage = R.drawable.ic_search_clicked,
            unSelectedImage = R.drawable.ic_search_white,
            isSelected = false
        ),
        BottomNavItem(
            route = "Image",
            selectedImage = R.drawable.ic_image_clicked,
            unSelectedImage = R.drawable.ic_image,
            isSelected = false
        ),
        BottomNavItem(
            route = "Saved",
            selectedImage = R.drawable.ic_save_white_clicked,
            unSelectedImage = R.drawable.ic_save_white,
            isSelected = false
        )
    )

    JetpackDemoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.surface),
        ) {
            BottomNavBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                items = bottomNavItems,
                onItemSelected = { }
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    @DrawableRes
    val selectedImage: Int,
    @DrawableRes
    val unSelectedImage: Int,
    val isSelected: Boolean
)
