package com.agrebennicov.jetpackdemo.features.main.toolbar

import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.BaseViewModel
import com.agrebennicov.jetpackdemo.navigation.main.NavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ToolbarViewModel @Inject constructor() : BaseViewModel<ToolbarAction, ToolbarState>(
    ToolbarState()
) {
    override fun onAction(action: ToolbarAction) {
        updateState(action)
    }

    override fun reduce(action: ToolbarAction, oldState: ToolbarState): ToolbarState {
        return when (action) {
            is ToolbarAction.RouteChanged -> updateToolbarTitle(oldState, action.route)
            is ToolbarAction.UpdateSelectingCount -> oldState.copy(selectingCount = action.count)
            ToolbarAction.SelectingModeOff -> oldState.copy(
                isSelecting = false,
                closeSelections = true,
                persistSelectingMode = false
            )
            ToolbarAction.SelectingModeOn -> oldState.copy(isSelecting = true)
            ToolbarAction.ShareSelections -> oldState.copy(shareSelections = true)
            ToolbarAction.UnSaveSelections -> oldState.copy(unSaveSelections = true)
            ToolbarAction.ResetToDefault -> oldState.copy(
                isSelecting = false,
                selectingCount = 0,
                closeSelections = false,
                shareSelections = false,
                unSaveSelections = false
            )
        }
    }

    private fun updateToolbarTitle(oldState: ToolbarState, route: String?): ToolbarState {
        val toolbarTitle = when (route) {
            NavRoutes.RandomScreen.route -> R.string.random_joke
            NavRoutes.SearchScreen.route -> R.string.search
            NavRoutes.SavedScreen.route -> R.string.saved
            else -> null
        }
        return oldState.copy(
            toolbarTitle = toolbarTitle,
            persistSelectingMode = oldState.isSelecting || oldState.persistSelectingMode,
            isSelecting = toolbarTitle == R.string.saved &&
                    (oldState.isSelecting || oldState.persistSelectingMode)
        )
    }
}
