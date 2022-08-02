package com.agrebennicov.jetpackdemo.features.main

import com.agrebennicov.jetpackdemo.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainAction, MainState>(MainState()) {
    override fun onAction(action: MainAction) {
        mutableState.value = when (action) {
            is MainAction.TabChanged -> reduce(action, state.value)
        }
    }

    override fun reduce(action: MainAction, oldState: MainState): MainState {
        return when (action) {
            is MainAction.TabChanged -> {
                val updatedItems = mutableState.value.items.map {
                    it.copy(isSelected = it.route == action.newRoute)
                }
                mutableState.value.copy(
                    route = action.newRoute,
                    items = updatedItems
                )
            }
        }
    }
}
