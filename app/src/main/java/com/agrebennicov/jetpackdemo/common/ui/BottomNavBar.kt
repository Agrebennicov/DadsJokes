package com.agrebennicov.jetpackdemo.common.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.theme.JetpackDemoTheme
import com.agrebennicov.jetpackdemo.navigation.main.NavRoutes

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem>,
    onItemSelected: (NavRoutes) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }

    BottomAppBar(
        modifier = modifier
            .background(color = MaterialTheme.colors.surface)
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    AnimatedContent(
                        modifier = Modifier.fillMaxSize(),
                        targetState = item
                    ) { state ->
                        if (state.isSelected) selectedIndex = index
                        Box(modifier = Modifier.fillMaxSize()) {
                            val icon =
                                if (state.isSelected) state.selectedImage else state.unSelectedImage
                            Icon(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(24.dp)
                                    .align(Alignment.Center),
                                painter = painterResource(id = icon),
                                contentDescription = state.route.route
                            )
                        }
                    }
                },
                selected = item.isSelected,
                onClick = { onItemSelected(item.route) }
            )
        }
    }

    val transition = updateTransition(
        targetState = selectedIndex,
        label = "bottomIndicatorTransition"
    )
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(3.dp)
    ) {

        val indicatorPosition by transition.animateDp(label = "bottomIndicatorTransitionDp") { state ->
            maxWidth.div(3).times(state)
        }

        Box(
            modifier = Modifier
                .width(maxWidth.div(3))
                .height(3.dp)
                .offset(x = indicatorPosition)
        ) {
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight()
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
    }
}

@Preview
@Composable
fun BottomNavBarPreview() {
    val bottomNavItems = listOf(
        BottomNavItem(
            route = NavRoutes.RandomScreen,
            selectedImage = R.drawable.ic_shuffle_white_clicked,
            unSelectedImage = R.drawable.ic_shuffle_white,
            isSelected = true
        ),
        BottomNavItem(
            route = NavRoutes.SearchScreen,
            selectedImage = R.drawable.ic_search_clicked,
            unSelectedImage = R.drawable.ic_search_white,
            isSelected = false
        ),
        BottomNavItem(
            route = NavRoutes.SavedScreen,
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
    val route: NavRoutes,
    @DrawableRes
    val selectedImage: Int,
    @DrawableRes
    val unSelectedImage: Int,
    val isSelected: Boolean
)
