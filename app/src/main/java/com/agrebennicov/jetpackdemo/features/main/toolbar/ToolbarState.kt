package com.agrebennicov.jetpackdemo.features.main.toolbar

data class ToolbarState(
    val isSelecting: Boolean = false,
    val selectingCount: Int = 0,
    val closeSelections: Boolean = false,
    val shareSelections: Boolean = false,
    val unSaveSelections: Boolean = false,
    val toolbarTitle: Int? = null,
    val persistSelectingMode: Boolean = false
)

sealed class ToolbarAction {
    data class RouteChanged(val route: String?) : ToolbarAction()
    data class UpdateSelectingCount(val count: Int) : ToolbarAction()
    object SelectingModeOn : ToolbarAction()
    object SelectingModeOff : ToolbarAction()
    object ShareSelections : ToolbarAction()
    object UnSaveSelections : ToolbarAction()
    object ResetToDefault : ToolbarAction()
}
