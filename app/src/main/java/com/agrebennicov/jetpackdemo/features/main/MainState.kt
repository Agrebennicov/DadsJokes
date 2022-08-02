package com.agrebennicov.jetpackdemo.features.main

import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.ui.BottomNavItem
import com.agrebennicov.jetpackdemo.navigation.main.NavRoutes

data class MainState(
    val route: NavRoutes = NavRoutes.RandomScreen,
    val items: List<BottomNavItem> = listOf(
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
            route = NavRoutes.RandomImageScreen,
            selectedImage = R.drawable.ic_image_clicked,
            unSelectedImage = R.drawable.ic_image,
            isSelected = false
        ),
        BottomNavItem(
            route = NavRoutes.SavedScreen,
            selectedImage = R.drawable.ic_save_white_clicked,
            unSelectedImage = R.drawable.ic_save_white,
            isSelected = false
        )
    )
)

sealed class MainAction {
    data class TabChanged(val newRoute: NavRoutes) : MainAction()
}
